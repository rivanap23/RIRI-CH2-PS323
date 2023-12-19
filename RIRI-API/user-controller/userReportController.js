/* eslint-disable require-jsdoc */
/* eslint-disable max-len */
const {format} = require('util');
const processFileMiddleware = require('../middleware/upload');
const admin = require('firebase-admin');

const storage = require('../storage-config/storage');
const bucket = storage.bucket('Bucket Name');
const db = require('../db-cofig/db');

// User membuat laporan
async function createReport(req, res) {
  try {
    const userId = req.params.userId;

    if (!userId) {
      return res.status(400).send({message: 'User ID tidak ditemukan.'});
    }

    const userDocRef = db.collection('users').doc(userId);
    const userDoc = await userDocRef.get();

    if (!userDoc.exists) {
      return res.status(404).send({message: 'User tidak ditemukan di database.'});
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
          reportId,
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

        await userDocRef.update({
          reports: admin.firestore.FieldValue.arrayUnion(reportId),
        });

        res.status(200).send({
          message: 'Pelaporan berhasil!',
          reportId,
        });
      } catch (err) {
        return res.status(500).send({
          message: `Gambar berhasil diunggah: ${imageName}, namun akses publik ditolak!`,
          url: publicUrl,
        });
      }
    });

    blobStream.end(req.file.buffer);
  } catch (err) {
    res.status(500).send({
      message: `Gagal membuat laporan. ${err}`,
    });
  }
};

// Mengambil semua laporan dengan status 'verifikasi' yang dibuat oleh user
async function getVerifiedReports(req, res) {
  try {
    const userId = req.params.userId;

    if (!userId) {
      return res.status(400).send({message: 'User ID tidak ditemukan.'});
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
      message: 'Berhasil menampilkan laporan yang sudah terverifikasi.',
      processedReports,
    });
  } catch (err) {
    res.status(500).send({
      message: `Gagal menampilkan laporan yang sudah terverifikasi. ${err}`,
    });
  }
};

// Mengambil semua laporan dengan status 'diproses' yang dibuat oleh user
async function getProcessedReports(req, res) {
  try {
    const userId = req.params.userId;

    if (!userId) {
      return res.status(400).send({message: 'User ID tidak ditemukan.'});
    }

    const reportsSnapshot = await db.collection('userReport')
        .where('userId', '==', userId)
        .where('status', '==', 'diproses')
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
      message: 'Berhasil menampilkan laporan yang sedang diproses.',
      processedReports,
    });
  } catch (err) {
    res.status(500).send({
      message: `Gagal menampilkan laporan yang sedang diproses. ${err}`,
    });
  }
};

// Mengambil semua laporan dengan status 'ditolak' yang dibuat oleh user
async function getRejectedReports(req, res) {
  try {
    const userId = req.params.userId;

    if (!userId) {
      return res.status(400).send({message: 'User ID tidak ditemukan.'});
    }

    const reportsSnapshot = await db.collection('userReport')
        .where('userId', '==', userId)
        .where('status', '==', 'ditolak')
        .get();

    const rejectedReports = [];

    reportsSnapshot.forEach((doc) => {
      const reportData = doc.data();
      rejectedReports.push({
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
      message: 'Berhasil mendapatkan laporan yang sudah ditolak.',
      rejectedReports,
    });
  } catch (err) {
    res.status(500).send({
      message: `Gagal mendapatkan laporan yang sudah ditolak. ${err}`,
    });
  }
};

async function getFinishedReports(req, res) {
  try {
    const userId = req.params.userId;

    if (!userId) {
      return res.status(400).send({message: 'User ID tidak ditemukan'});
    }

    const reportsSnapshot = await db.collection('userReport')
        .where('userId', '==', userId)
        .where('status', '==', 'selesai')
        .get();

    const acceptedReports = [];

    reportsSnapshot.forEach((doc) => {
      const reportData = doc.data();
      acceptedReports.push({
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
      message: 'Berhasil menampilkan laporan selesai.',
      acceptedReports,
    });
  } catch (err) {
    res.status(500).send({
      message: `Gagal menampilkan laporan selesai. ${err}`,
    });
  }
};

module.exports = {
  createReport,
  getVerifiedReports,
  getProcessedReports,
  getFinishedReports,
  getRejectedReports,
};
