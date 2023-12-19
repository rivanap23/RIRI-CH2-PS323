const admin = require('firebase-admin');
const credentials = require('Service Account for firebase path');

admin.initializeApp({
  credential: admin.credential.cert(credentials),
});

const db = admin.firestore();

module.exports = db;
