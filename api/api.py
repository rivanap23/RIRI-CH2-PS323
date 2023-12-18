import json
from flask import Flask, request, jsonify, render_template
import tensorflow as tf
from keras.preprocessing.sequence import pad_sequences
from keras.preprocessing.text import Tokenizer
import pandas as pd
import numpy as np

app = Flask(__name__)

model = tf.keras.models.load_model('model_h5/fake_detection.h5')

with open('json_things/tokenizer_dict.json') as file:
    data = json.load(file)
    print(data)

tokenizer = Tokenizer(num_words=1795, oov_token='')
tokenizer.word_index = data


@app.route('/')
def home():
    return render_template('index.html')


@app.route('/predict', methods=['POST'])
def predict():
    if request.method == 'POST':
        text = request.form['text']
        sequence = tokenizer.texts_to_sequences([text])
        padded_sequence = pad_sequences(sequence, maxlen=16, padding='post', truncating='post')
        prediction = model.predict(padded_sequence)

        # Assuming a binary classification problem
        result = "Fake" if prediction[0][0] >= 0.5 else "Real"

        return render_template('result.html', prediction=result)

if __name__ == '__main__':
    app.run(debug=True)
