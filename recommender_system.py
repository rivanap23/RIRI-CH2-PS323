import math
import numpy as np
import scipy
import pandas as pd
import sklearn
import stopwords
import tensorflow as tf
import tensorflow_recommenders as tfrs
from typing import Dict, Text

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.preprocessing import LabelEncoder
from tensorflow import keras
from sklearn.model_selection import train_test_split

df = pd.read_csv('../data/shared_articles.csv/shared_articles.csv').drop(['timestamp',
                                                                          'authorSessionId',
                                                                          'authorUserAgent',
                                                                          'authorRegion',
                                                                          'authorCountry',
                                                                          'url'], axis=1)
interact_df = pd.read_csv('../data/users_interactions.csv/users_interactions.csv').drop(['timestamp',
                                                                                         'sessionId',
                                                                                         'userAgent',
                                                                                         'userRegion'], axis=1)
print(df.head(0))

# interact_df.dropna(subset=['userCountry'], inplace=True)
interact_df = interact_df.drop(interact_df[interact_df['eventType'] == 'FOLLOW'].index)
interact_df = interact_df.drop(interact_df[interact_df['eventType'] == 'BOOKMARK'].index)

df['contentId'] = df['contentId']

event_type = {
    'VIEW': 1.0,
    'LIKE': 2.0,
    'COMMENT CREATED': 4.0,
}

interact_df['eventStrength'] = interact_df['eventType'].apply(lambda x: event_type[x])


def smooth_preference(x):
    return math.log(1 + x, 2)

content_df = df[['contentId', 'title']]
print(content_df.head(0))
print(interact_df.head(0))

interact_df['personId'] = interact_df['personId'].astype(str)
interactions_df = pd.merge(interact_df, df[['title', 'contentType', 'contentId']], on='contentId', how='left')

tfidf_vectorizer = TfidfVectorizer(stop_words='english')
content_features = tfidf_vectorizer.fit_transform(content_df['title']).toarray()

user_ids = interact_df['personId'].unique()
content_ids = interact_df['contentId'].unique()

user_id_to_index = {user_id: index for index, user_id in enumerate(user_ids)}
content_id_to_index = {content_id: index for index, content_id in enumerate(content_ids)}

interact_df['userIndex'] = interact_df['personId'].map(user_id_to_index)
interact_df['contentIndex'] = interact_df['contentId'].map(content_id_to_index)

train_df, test_df = train_test_split(interact_df,
                                     test_size=0.2,
                                     random_state=42)

class ContentBasedModel(tf.keras.Model):
    def __init__(self, num_users, num_content, embedding_dim=50):
        super(ContentBasedModel, self).__init__()
        self.user_embedding = tf.keras.layers.Embedding(num_users, embedding_dim, input_length=1)
        self.content_embedding = tf.keras.layers.Embedding(num_content, embedding_dim, input_length=1)
        self.flatten = tf.keras.layers.Flatten()
        self.dot_product = tf.keras.layers.Dot(axes=1)

    def call(self, inputs):
        user_embedding = self.flatten(self.user_embedding(inputs['user']))
        content_embedding = self.flatten(self.content_embedding(inputs['content']))
        dot_product = self.dot_product([user_embedding, content_embedding])
        return dot_product


num_users = len(user_ids)
num_content = len(content_ids)
model = ContentBasedModel(num_users, num_content)
model.compile(optimizer='adam',
              loss='mean_squared_error',
              metrics='accuracy')

train_inputs = {
    'user': train_df['userIndex'].values,
    'content': train_df['contentIndex'].values
}

test_inputs = {
    'user': test_df['userIndex'].values,
    'content': test_df['contentIndex'].values
}

model.fit(train_inputs,
          train_df['eventStrength'].values,
          validation_data=(test_inputs, test_df['eventStrength'].values),
          epochs=5,
          batch_size=32)

loss = model.evaluate(test_inputs,
                      test_df['eventStrength'].values)

print(f'Test Loss: {loss}')

def recommend_content_for_user(user_id, num_recommendations=5):
    user_index = user_id_to_index.get(user_id, None)

    if user_index is not None:
        all_content_indices = np.arange(len(content_ids))

        user_interactions = interact_df[interact_df['userIndex'] == user_index]['contentIndex'].values
        candidate_content_indices = np.setdiff1d(all_content_indices, user_interactions)

        user_inputs = {'user': np.full(len(candidate_content_indices), user_index), 'content': candidate_content_indices}
        predicted_strengths = model.predict(user_inputs)

        top_indices = np.argsort(predicted_strengths[:, 0])[::-1][:num_recommendations]

        top_content_titles = [content_df.loc[content_df['contentId'] == content_ids[idx], 'title'].values[0]
                              for idx in candidate_content_indices[top_indices]]

        return top_content_titles

    else:
        return None


list_usr = list(user_id_to_index.keys())
example_user_id = list_usr[0]
num_recommendations = 5

recommended_content = recommend_content_for_user(example_user_id, num_recommendations)

if recommended_content is not None:
    print(f'Top {num_recommendations} recommended content for user {example_user_id}: {recommended_content}')
else:
    print(f'User ID not found in the mapping.')

