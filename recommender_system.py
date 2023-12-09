import math

import numpy as np
import scipy
import pandas as pd
import sklearn
import stopwords
import tensorflow as tf
from sklearn.preprocessing import LabelEncoder
from tensorflow import keras
from sklearn.model_selection import train_test_split

df = pd.read_csv('../data/shared_articles.csv/shared_articles.csv')
df = df[df['eventType'] == 'CONTENT SHARED']

interact_df = pd.read_csv('../data/users_interactions.csv/users_interactions.csv')

event_type = {
    'VIEW': 1.0,
    'LIKE': 2.0,
    'COMMENT CREATED': 4.0,
    'BOOKMARK': 2.5,
    'FOLLOW': 3.0,
}

interact_df['eventStrength'] = interact_df['eventType'].apply(lambda x: event_type[x])

user_interact_count_df = interact_df.groupby(['personId', 'contentId']).size().groupby('personId').size()
print('users: ', len(user_interact_count_df))

users_req_interact_df = user_interact_count_df[user_interact_count_df >= 4].reset_index()[['personId']]
print('users with at least 4 interactions: ', len(users_req_interact_df))

print('interactions: ', len(interact_df))

interact_selected_users_df = interact_df.merge(users_req_interact_df, how='right',
                                               left_on='personId',
                                               right_on='personId')

print('interactions from users with at least 4 interactions: ', len(interact_selected_users_df))


def smooth_preference(x):
    return math.log(1 + x, 2)


interact_full_df = (interact_selected_users_df.
                    groupby(['personId', 'contentId'])['eventStrength']
                    .sum().apply(smooth_preference).reset_index())
print('unique user/item interactions: ', len(interact_full_df))
print(interact_full_df.head(10))

content_encoder = LabelEncoder()
person_encoder = LabelEncoder()

interact_full_df['encoded_contentId'] = content_encoder.fit_transform(interact_full_df['contentId'])
interact_full_df['encoded_personId'] = person_encoder.fit_transform(interact_full_df['personId'])

interact_train_df, interact_test_df = train_test_split(interact_full_df,
                                                       stratify=interact_full_df['personId'],
                                                       test_size=0.2,
                                                       random_state=33)
print('Train dataset: ', len(interact_train_df))
print('Test dataset: ', len(interact_test_df))

def build_content_model():
    model = tf.keras.models.Sequential([
        tf.keras.layers.Input(shape=(1,), name='content_input'),
        tf.keras.layers.Embedding(input_dim=len(content_encoder.classes_), output_dim=100, input_length=1),
        tf.keras.layers.Flatten(),  # Flatten layer to connect with Dense layer
        tf.keras.layers.Dense(128, activation='relu'),
        tf.keras.layers.Dropout(0.5),  # Dropout for regularization
        tf.keras.layers.Dense(1, activation='linear')
    ])

    lr_schedule = tf.keras.optimizers.schedules.ExponentialDecay(initial_learning_rate=0.1, decay_steps=10000,
                                   decay_rate=0.9)
    optimizer = tf.keras.optimizers.Adam(learning_rate=lr_schedule)

    model.compile(optimizer=keras.optimizers.Adam(learning_rate=0.001),
                  loss='mse',
                  metrics=['accuracy'])

    return model


content_model = build_content_model()

history = content_model.fit(
    x=interact_train_df['encoded_contentId'].values,
    y=interact_train_df['eventStrength'].values,
    batch_size=64,
    epochs=20,
    validation_data=(
        interact_test_df['encoded_contentId'].values,
        interact_test_df['eventStrength'].values
    ))


test_loss = content_model.evaluate(
    x=interact_test_df['encoded_contentId'].values,
    y=interact_test_df['eventStrength'].values
)

print(f'Test Loss: {test_loss}')


def recommend_content_for_user(user_id, model, interact_full_df, content_encoder):
    user_content = interact_full_df[interact_full_df['encoded_personId'] == user_id]['encoded_contentId'].unique()
    all_content = np.arange(len(content_encoder.classes_))

    new_content = np.setdiff1d(all_content, user_content)

    predictions = model.predict(new_content)

    predicted_content_ids = content_encoder.inverse_transform(new_content)

    recommendations_df = pd.DataFrame({
        'contentId': predicted_content_ids,
        'predicted_eventStrength': predictions.flatten()
    })

    # Sort the recommendations by predicted eventStrength
    recommendations_df = recommendations_df.sort_values(by='predicted_eventStrength', ascending=False)

    return recommendations_df


user_id_to_recommend = interact_test_df['encoded_personId'].values[0]
recommendations = recommend_content_for_user(user_id_to_recommend, content_model, interact_full_df,
                                             content_encoder)

print('Recommendations for user:', person_encoder.inverse_transform([user_id_to_recommend])[0])
print(recommendations.head(10))

