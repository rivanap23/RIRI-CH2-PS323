from flask import Flask, request, jsonify
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
import json
import os
import tensorflow as tf

app = Flask(__name__)

@app.route('/predict-fake', methods=['POST'])
def predict_fake():
    max_len = 3213
    trunc_type = 'post'
    padding_type = 'post'
    oov_tok = '<OOV>'

    model_path = os.path.join(os.path.dirname(__file__), 'fake_detection_v2.h5')
    model = load_model(model_path)

    tokenizer_path = os.path.join(os.path.dirname(__file__), 'tokenizer_dict_v2.json')
    with open(tokenizer_path) as file:
        data = json.load(file)

    tokenizer = Tokenizer(num_words=max_len, oov_token=oov_tok)
    tokenizer.word_index = data

    data_input = request.get_json(force=True)
    input_text = data_input['text']

    sequences = tokenizer.texts_to_sequences([input_text])
    padded = pad_sequences(sequences, maxlen=16, padding=padding_type, truncating=trunc_type)

    predictions = model.predict(padded)
    binary_predictions = (predictions > 0.5).astype(int)

    if binary_predictions[0] == 1:
        status = 'Laporan terdeteksi palsu'
    else:
        status = 'Laporan diterima'

    response = {
        'prediction': int(binary_predictions[0]),
        'status': str(status)
    }

    return jsonify(response)

if __name__ == '__main__':
    app.run(debug=True, host="0.0.0.0", port=int(os.environ.get("PORT", 8080)))
