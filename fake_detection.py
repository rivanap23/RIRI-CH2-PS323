import math
import numpy as np
import scipy
import pandas as pd
import sklearn
import stopwords
import tensorflow as tf
from typing import Dict, Text
from sklearn.preprocessing import LabelEncoder
from tensorflow import keras
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences

data = pd.read_csv('../data/dataset_fake_2.csv')
data = data.sample(frac=1)

# sentences = []
# labels = []

sentences = data['Text']
labels = data['Label']

trainig_size = 360
vocab_size = 10000
max_length = 32
embedding_dim = 16
trunc_type = 'post'
padding_type = 'post'
oov_token = '<OOV>'

training_sentences = sentences[0:trainig_size]
testing_sentences = sentences[trainig_size:]

training_labels = labels[0:trainig_size]
testing_labels = labels[trainig_size:]

tokenizer = Tokenizer(num_words=vocab_size, oov_token=oov_token)

tokenizer.fit_on_texts(training_sentences)
word_index = tokenizer.word_index

training_seq = tokenizer.texts_to_sequences(training_sentences)
training_pad = pad_sequences(training_seq, maxlen=max_length, padding=padding_type, truncating=trunc_type)

testing_seq = tokenizer.texts_to_sequences(testing_sentences)
testing_pad = pad_sequences(testing_seq, maxlen=max_length, padding=padding_type, truncating=trunc_type)

training_labels = np.array(training_labels)
testing_labels = np.array(testing_labels)

gap1d_layer = tf.keras.layers.GlobalAveragePooling1D()

sample_array = np.array([[[10, 2], [1, 3], [1, 1]]])

print(f'sample of array = {sample_array.shape}')
print(f'sample array: {sample_array}')

output = gap1d_layer(sample_array)

print(f'output shape of gap1d_layer: {output.shape}')
print(f'output array of gap1d_layer: {output.numpy()}')

model = tf.keras.models.Sequential([
    tf.keras.layers.Embedding(vocab_size, embedding_dim, input_length=max_length),
    tf.keras.layers.GlobalAveragePooling1D(),
    tf.keras.layers.Dense(24, activation='relu'),
    tf.keras.layers.Dense(1, activation='sigmoid')
])

model.summary()

model.compile(loss='binary_crossentropy',
              optimizer='adam',
              metrics=['accuracy'])

history = model.fit(training_pad,
                    training_labels,
                    epochs=30,
                    validation_data=(testing_pad, testing_labels),
                    callbacks=[],
                    verbose=2)

new_sentence = ["alsdjfielakjsdifekljsadf"]

new_seq = tokenizer.texts_to_sequences(new_sentence)
new_pad = pad_sequences(new_seq, maxlen=max_length, padding=padding_type, truncating=trunc_type)

predictions = model.predict(new_pad)

print(f'Predictions: {predictions}')

# Convert predictions to binary labels (0 or 1)
binary_predictions = (predictions > 0.5).astype(int)
print(f'Binary Predictions: {binary_predictions}')


