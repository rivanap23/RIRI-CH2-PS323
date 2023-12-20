import math
import numpy as np
import scipy
import pandas as pd
import sklearn
import stopwords
import tensorflow as tf
import tensorflow_recommenders as tfrs
from typing import Dict, Text
from matplotlib import pyplot as plt
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.preprocessing import LabelEncoder
from tensorflow import keras
from sklearn.model_selection import train_test_split

content_df = pd.read_csv('../data/recommender_new/discussion.csv')
interact_df = pd.read_csv('../data/recommender_new/user_interact.csv')

interact_df = interact_df.drop(interact_df[interact_df['interaction'] == 'FOLLOW'].index)
interact_df = interact_df.drop(interact_df[interact_df['interaction'] == 'BOOKMARK'].index)

event_type = {
    'VIEW': 1.0,
    'LIKE': 2.0,
    'COMMENT CREATED': 4.0,
}

interact_df['eventStrength'] = interact_df['interaction'].apply(lambda x: event_type[x])


def smooth_preference(x):
    return math.log(1 + x, 2)


print(interact_df.head(0))
print(content_df.head(0))

interact_df['person_id'] = interact_df['person_id'].astype('str')
interact_df = pd.merge(interact_df, content_df, on='content_id', how='left')

tfid_vectorizer = TfidfVectorizer()
content_feature = tfid_vectorizer.fit_transform(content_df['title']).toarray()

user_id = interact_df['person_id'].unique()
content_id = interact_df['content_id'].unique()

user_index = {user_id: index for index, user_id in enumerate(user_id)}
content_index = {content_id: index for index, content_id in enumerate(content_id)}

interact_df['user_index'] = interact_df['person_id'].map(user_index)
interact_df['content_index'] = interact_df['content_id'].map(content_index)

train, test = train_test_split(interact_df,
                               test_size=0.2,
                               random_state=42)


class ContentBasedModel(tf.keras.Model):
    def __init__(self, num_users, num_content, embedding_dim=30, dropout_rate=0.3, reg_rate=1e-4, name=None):
        super(ContentBasedModel, self).__init__()
        self.user_embedding = tf.keras.layers.Embedding(num_users, embedding_dim, input_length=1,
                                                        embeddings_regularizer=keras.regularizers.l2(reg_rate))
        self.content_embedding = tf.keras.layers.Embedding(num_content, embedding_dim, input_length=1,
                                                           embeddings_regularizer=keras.regularizers.l2(reg_rate))
        self.flatten = tf.keras.layers.Flatten()
        self.dropout = tf.keras.layers.Dropout(dropout_rate)
        self.batch_norm = tf.keras.layers.BatchNormalization()  # Add Batch Normalization
        self.dot_product = tf.keras.layers.Dot(axes=1)

    def call(self, inputs):
        user_embedding = self.flatten(self.user_embedding(inputs['user']))
        content_embedding = self.flatten(self.content_embedding(inputs['content']))
        user_embedding = self.dropout(self.batch_norm(user_embedding))  # Apply Batch Normalization
        content_embedding = self.dropout(self.batch_norm(content_embedding))  # Apply Batch Normalization
        dot_product = self.dot_product([user_embedding, content_embedding])
        return dot_product


num_users = len(user_id)
num_content = len(content_id)


def get_model():
    return ContentBasedModel(num_users, num_content, name='recommender_system')


model = get_model()
model.compile(optimizer='rmsprop',
              loss='mean_squared_error',
              metrics='accuracy')

train_inputs = {
    'user': train['user_index'].values,
    'content': train['content_index'].values
}

test_inputs = {
    'user': test['user_index'].values,
    'content': test['content_index'].values
}

history = model.fit(train_inputs,
          train['eventStrength'].values,
          validation_data=(test_inputs, test['eventStrength'].values),
          epochs=30,
          batch_size=64)


def plot_graphs(history, metric):
    plt.plot(history.history[metric])
    plt.plot(history.history[f'val_{metric}'])
    plt.xlabel("Epochs")
    plt.ylabel(metric)
    plt.legend([metric, f'val_{metric}'])
    plt.show()


plot_graphs(history, "accuracy")
plot_graphs(history, "loss")


def recommend_content_for_user(user_id, num_recommendations=15):
    user_index_new = user_index.get(user_id, None)

    if user_index_new is not None:
        all_content_indices = np.arange(len(content_id))

        user_interactions = interact_df[interact_df['user_index'] == user_index_new]['content_index'].values
        candidate_content_indices = np.setdiff1d(all_content_indices, user_interactions)

        user_inputs = {'user': np.full(len(candidate_content_indices), user_index_new), 'content': candidate_content_indices}
        predicted_strengths = model.predict(user_inputs)

        top_indices = np.argsort(predicted_strengths[:, 0])[::-1][:num_recommendations]

        top_content_titles = [content_df.loc[content_df['content_id'] == content_id[idx], 'title'].values[0]
                              for idx in candidate_content_indices[top_indices]]

        return top_content_titles

    else:
        return None


list_usr = list(user_index.keys())
example_user_id = list_usr[0]
num_recommendations = 15

recommended_content = recommend_content_for_user(example_user_id, num_recommendations)

if recommended_content is not None:
    print(f'Top {num_recommendations} recommended content for user {example_user_id}: {recommended_content}')
else:
    print(f'User ID not found in the mapping.')

model.save_weights('content_based_model_weights.h5')
loaded_model = get_model()
loaded_model.load_weights('content_based_model_weights.h5')

print('Model loaded!')
