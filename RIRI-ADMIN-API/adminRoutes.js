const express = require('express')
const app = express();
const adminController = require('./admin-controller/adminController');

app.use(express.json());
app.use(express.urlencoded({extended: true}));

// Admin memproses laporan
app.put('/:reportId/report/diproses', async (req, res) => {
  try {
    await adminController.processReport(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// Admin menolak laporan
app.put('/:reportId/report/ditolak', async (req, res) => {
  try {
    await adminController.rejectReport(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// Admin menyelesaikan laporan
app.put('/:reportId/report/selesai', async (req, res) => {
  try {
    await adminController.finishReport(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

module.exports = app;