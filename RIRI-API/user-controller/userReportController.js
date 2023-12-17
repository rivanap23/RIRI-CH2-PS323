/* eslint-disable require-jsdoc */
/* eslint-disable max-len */
const {format} = require('util');
const processFileMiddleware = require('../middleware/upload');
const admin = require('firebase-admin');

const storage = require('../storage-config/storage');
const bucket = storage.bucket('riri-user-report');
const db = admin.firestore();

// User membuat laporan
async function createReport(req, res) {
  try {
    const userId = req.params.userId;

    if (!userId) {
      return res.status(400).send({message: 'User ID is missing.'});
    }

    const userDocRef = db.collection('users').doc(userId);
    const userDoc = await userDocRef.get();

    if (!userDoc.exists) {
      return res.status(404).send({message: 'User not found in the database.'});
    }

    const userData = userDoc.data();
    const username = userData.username;

    await processFileMiddleware(req, res);

    const {judulLaporan, instansi, kategoriLaporan, deskripsiLaporan, lokasi, detailLokasi} = req.body;

    if (!judulLaporan || !instansi || !kategoriLaporan || !deskripsiLaporan || !lokasi || !detailLokasi || !req.file) {
      return res.status(400).send({message: 'Lengkapi semua data yang diperlukan.'});
    }

    const timestamp = new Date().getTime();
    const randomString = Math.random().toString(36).substring(2, 8);
    const reportId = `${timestamp}-${randomString}`;

    const imageName = `${reportId}-bukti-foto.png`;
    const blob = bucket.file(imageName);

    const blobStream = blob.createWriteStream({
      resumable: false,
    });

    blobStream.on('error', (err) => {
      res.status(500).send({message: err.message});
    });

    blobStream.on('finish', async () => {
      const publicUrl = format(
          `https://storage.googleapis.com/${bucket.name}/${imageName}`,
      );

      try {
        await blob.makePublic();

        const reportData = {
          judulLaporan,
          instansi,
          kategoriLaporan,
          deskripsiLaporan,
          lokasi,
          detailLokasi,
          buktiFoto: publicUrl,
          userId,
          username,
          status: 'verifikasi',
          createdAt: admin.firestore.FieldValue.serverTimestamp(),
        };

        await db.collection('userReport').doc(reportId).set(reportData);

        res.status(200).send({
          message: 'Pelaporan berhasil!',
          reportId,
        });
      } catch (err) {
        return res.status(500).send({
          message: `Uploaded the file successfully: ${imageName}, but public access is denied!`,
          url: publicUrl,
        });
      }
    });

    blobStream.end(req.file.buffer);
  } catch (err) {
    res.status(500).send({
      message: `Could not create the report. ${err}`,
    });
  }
};

// Mengambil semua laporan dengan status 'diproses' yang dibuat oleh user
async function getProcessedReports(req, res) {
  try {
    const userId = req.params.userId;

    if (!userId) {
      return res.status(400).send({message: 'User ID is missing.'});
    }

    const reportsSnapshot = await db.collection('userReport')
        .where('userId', '==', userId)
        .where('status', '==', 'verifikasi')
        .get();

    const processedReports = [];

    reportsSnapshot.forEach((doc) => {
      const reportData = doc.data();
      processedReports.push({
        reportId: doc.id,
        judulLaporan: reportData.judulLaporan,
        instansi: reportData.instansi,
        kategoriLaporan: reportData.kategoriLaporan,
        deskripsiLaporan: reportData.deskripsiLaporan,
        lokasi: reportData.lokasi,
        detailLokasi: reportData.detailLokasi,
        buktiFoto: reportData.buktiFoto,
        createdAt: reportData.createdAt,
        status: reportData.status,
      });
    });

    res.status(200).send({
      message: 'Berhasil mendapatkan laporan yang sedang diproses.',
      processedReports,
    });
  } catch (err) {
    res.status(500).send({
      message: `Could not get processed reports. ${err}`,
    });
  }
};

module.exports = {
  createReport,
  getProcessedReports,
};
