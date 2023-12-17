const {Storage} = require('@google-cloud/storage');
const storage = new Storage({keyFilename: 'storageKey.json'});

module.exports = storage;
