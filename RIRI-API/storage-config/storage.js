const {Storage} = require('@google-cloud/storage');
const storage = new Storage({keyFilename: 'Service Account for Storage'});

module.exports = storage;
