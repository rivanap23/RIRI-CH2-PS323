const util = require('util');
const multer = require('multer');
const maxSize = 5 * 1024 * 1024;

const processFile = multer({
  storage: multer.memoryStorage(),
  limits: {fileSize: maxSize},
}).single('file');

const processFileMiddleware = util.promisify(processFile);
module.exports = processFileMiddleware;
