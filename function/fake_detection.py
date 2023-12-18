import tensorflow as tf
import json
import requests
from google.cloud import storage
from keras.models import load_model
from keras.preprocessing.text import Tokenizer
from keras.preprocessing.sequence import pad_sequences

storage_client = storage.Client()
bucket = storage_client.get_bucket('BUCKET')

blob = bucket.blob('fake_detection.h5')
blob.download_to_filename('/tmp/fake_detection.h5')
model = load_model('/tmp/fake_detection.h5')

blob_data = bucket.blob('tokenizer_dict.json')
blob_data.download_to_filename('/tmp/tokenizer_dict.json')

with open('/tmp/tokenizer_dict.json') as file:
    data = json.load(file)

def predict_fake(request):
    if request.method == 'GET':
        return "Please send POST Request"

    elif request.method == 'POST':
        maxlen = 1745
        trunc_type = 'post'
        padding_type = 'post'

        tokenizer = Tokenizer(num_words=maxlen, oov_token='')
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

        return str(response)

