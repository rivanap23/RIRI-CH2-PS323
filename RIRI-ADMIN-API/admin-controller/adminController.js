const admin = require('firebase-admin');
const db = require('../db-cofig/db');

// Laporan diproses admin
async function processReport(req, res) {
  try {
    const reportId = req.params.reportId;

    if (!reportId) {
      return res.status(400).send({message: 'ID Laporan tidak ditemukan.'});
    }

    const reportDocRef = db.collection('userReport').doc(reportId);
    const reportDoc = await reportDocRef.get();

    if (!reportDoc.exists) {
      return res.status(404).send({message: 'Laporan tidak ditemukan di database.'});
    }

    const currentStatus = reportDoc.data().status;
    if (currentStatus !== 'verifikasi') {
      return res.status(400).send({message: `Tidak dapat memproses report dengan status: ${currentStatus}`});
    }

    await reportDocRef.update({
      status: 'diproses',
      processedAt: admin.firestore.FieldValue.serverTimestamp(),
    });

    res.status(200).send({message: 'Laporan sedang diproses.'});
  } catch (err) {
    res.status(500).send({
      message: `Gagal memproses laporan. ${err}`,
    });
  }
};


// Laporan ditolak admin
async function rejectReport(req, res) {
  try {
    const reportId = req.params.reportId;

    if (!reportId) {
      return res.status(400).send({message: 'ID Laporan tidak ditemukan.'});
    }

    const reportDocRef = db.collection('userReport').doc(reportId);
    const reportDoc = await reportDocRef.get();

    if (!reportDoc.exists) {
      return res.status(404).send({message: 'Laporan tidak ditemukan di database.'});
    }

    const currentStatus = reportDoc.data().status;
    if (currentStatus !== 'diproses') {
      return res.status(400).send({message: `Tidak bisa menolak laporan dengan status: ${currentStatus}`});
    }

    await reportDocRef.update({
      status: 'ditolak',
      rejectedAt: admin.firestore.FieldValue.serverTimestamp(),
    });

    res.status(200).send({message: 'Laporan ditolak.'});
  } catch (err) {
    res.status(500).send({
      message: `Gagal menolak laporan. ${err}`,
    });
  }
};

// Laporan ditandai selesai
async function finishReport(req, res) {
  try {
    const reportId = req.params.reportId;

    if (!reportId) {
      return res.status(400).send({message: 'ID Laporan tidak ditemukan.'});
    }

    const reportDocRef = db.collection('userReport').doc(reportId);
    const reportDoc = await reportDocRef.get();

    if (!reportDoc.exists) {
      return res.status(404).send({message: 'Laporan tidak ditemukan di database.'});
    }

    const currentStatus = reportDoc.data().status;
    if (currentStatus !== 'diproses') {
      return res.status(400).send({message: `Tidak bisa menyelesaikan laporan dengan status: ${currentStatus}`});
    }

    await reportDocRef.update({
      status: 'selesai',
      acceptedAt: admin.firestore.FieldValue.serverTimestamp(),
    });

    res.status(200).send({message: 'Laporan selesai'});
  } catch (err) {
    res.status(500).send({
      message: `Tidak dapat menyelesaikan laporan. ${err}`,
    });
  }
};


module.exports = {
  processReport,
  finishReport,
  rejectReport,
};
