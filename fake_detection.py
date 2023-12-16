import random
import numpy as np
import pandas as pd
import tensorflow as tf
from matplotlib import pyplot as plt
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences

data = pd.read_csv('../data/dataset_fake_3.csv')
data = data.sample(frac=1)

sentences = data['Text']
labels = data['Label']

max_examples = 1995
max_length = 16
embedding_dim = 100
trunc_type = 'post'
padding_type = 'post'
oov_token = '<OOV>'
trainig_split = 0.9

sentences_and_labels = list(zip(sentences, labels))

random.seed(42)
sentences_and_labels = random.sample(sentences_and_labels, max_examples)

sentences, labels = zip(*sentences_and_labels)
print(f"There are {len(sentences)} sentences and {len(labels)} labels after random sampling\n")

training_size = int(len(sentences)*trainig_split)

training_sentences = sentences[0:training_size]
testing_sentences = sentences[training_size:]

training_labels = labels[0:training_size]
testing_labels = labels[training_size:]

print(f"There are {len(training_sentences)} sentences for training.")
print(f"There are {len(training_labels)} labels for training.")
print(f"There are {len(testing_sentences)} sentences for validation.")
print(f"There are {len(testing_labels)} labels for validation.")

tokenizer = Tokenizer(num_words=len(training_sentences), oov_token="")
tokenizer.fit_on_texts(training_sentences)

word_index = tokenizer.word_index
vocab_size = len(word_index)

training_seq = tokenizer.texts_to_sequences(training_sentences)
training_pad = pad_sequences(training_seq, maxlen=max_length, padding=padding_type, truncating=trunc_type)
testing_seq = tokenizer.texts_to_sequences(testing_sentences)
testing_pad = pad_sequences(testing_seq, maxlen=max_length, padding=padding_type, truncating=trunc_type)

print(f"Padded and truncated training sequences have shape: {training_pad.shape}")
print(f"Padded and truncated validation sequences have shape: {testing_pad.shape}")

training_labels = np.array(training_labels)
testing_labels = np.array(testing_labels)

model = tf.keras.models.Sequential([
    tf.keras.layers.Embedding(vocab_size, embedding_dim, input_length=max_length),
    tf.keras.layers.Dropout(0.5),
    tf.keras.layers.Conv1D(32, 5, activation='relu'),
    tf.keras.layers.MaxPooling1D(pool_size=4),
    tf.keras.layers.Dropout(0.5),
    tf.keras.layers.LSTM(32),
    tf.keras.layers.Dropout(0.5),
    tf.keras.layers.Dense(32, activation='relu', kernel_regularizer=tf.keras.regularizers.l2(0.01)),
    # Add L2 regularization
    tf.keras.layers.Dropout(0.5),
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

new_sentence = ["Wahyu pramana berjalan di atas air"]

new_seq = tokenizer.texts_to_sequences(new_sentence)
new_pad = pad_sequences(new_seq, maxlen=max_length, padding=padding_type, truncating=trunc_type)

predictions = model.predict(new_pad)

print(f'Predictions: {predictions}')

# Convert predictions to binary labels (0 or 1)
binary_predictions = (predictions > 0.5).astype(int)
print(f'Binary Predictions: {binary_predictions}')


def plot_graphs(history, metric):
    plt.plot(history.history[metric])
    plt.plot(history.history[f'val_{metric}'])
    plt.xlabel("Epochs")
    plt.ylabel(metric)
    plt.legend([metric, f'val_{metric}'])
    plt.show()


plot_graphs(history, "accuracy")
plot_graphs(history, "loss")

