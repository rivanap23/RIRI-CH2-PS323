from flask import Flask, request, jsonify
import numpy as np
import tensorflow as tf
from keras.preprocessing.sequence import pad_sequences
from keras.preprocessing.text import Tokenizer

app = Flask(__name__)

 
model = tf.keras.models.load_model('PATH MODEEELZ.h5')  # PATH MODEL
tokenizer = Tokenizer()

 

@app.route('/predict', methods=['POST'])
def predict():
    try:
         
        input_data = request.json
        new_sentence = input_data['sentence']

         
        new_seq = tokenizer.texts_to_sequences([new_sentence])
        new_pad = pad_sequences(new_seq, maxlen=32, padding='post', truncating='post')

        
        prediction = model.predict(new_pad)

         
        binary_prediction = int(prediction[0][0] > 0.5)

        response = {
            'prediction': binary_prediction
        }

        return jsonify(response)

    except Exception as e:
        return jsonify({'error': str(e)})

if __name__ == '__main__':
    app.run(port=5000, debug=True)
