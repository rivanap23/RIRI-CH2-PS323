from flask import Flask, request, jsonify
import tensorflow as tf
from keras.models import load_model
import re
import json
import firebase_admin
from firebase_admin import credentials, firestore
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences

cred = credentials.Certificate('../json_things/firestoreKey.json')
firebase_admin.initialize_app(cred)

# Access the Firestore database
db = firestore.client()

app = Flask(__name__)

@app.route('/predict-fake', methods=['POST'])
def predict_fake():
    max_len = 1745
    trunc_type = 'post'
    padding_type = 'post'

    model = load_model('../model_h5/fake_detection.h5')

    with open('../json_things/tokenizer_dict.json') as file:
        data = json.load(file)

    tokenizer = Tokenizer(num_words=max_len, oov_token='')
    tokenizer.word_index = data

    data_input = request.get_json(force=True)
    input_text = data_input['text']

    sequences = tokenizer.texts_to_sequences([input_text])
    padded = pad_sequences(sequences, maxlen=16, padding=padding_type, truncating=trunc_type)

    predictions = model.predict(padded)
    binary_predictions = (predictions > 0.5).astype(int)

    if binary_predictions == 1:
        status = 'Laporan terdeteksi palsu'
    else:
        status = 'Laporan diterima'

    response = {
        'prediction': int(binary_predictions[0]),
        'status': str(status)
    }

    return jsonify(response)


if __name__ == '__main__':
    app.run(debug=True)

