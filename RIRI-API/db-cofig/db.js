const admin = require('firebase-admin');
const credentials = require('Service account path for firebase');

admin.initializeApp({
  credential: admin.credential.cert(credentials),
});

const db = admin.firestore();

module.exports = db;
