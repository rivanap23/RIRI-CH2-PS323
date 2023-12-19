/* eslint-disable max-len */
const express = require('express');
const userAuthController = require('../user-controller/userAuthController');
const userProfileController = require('../user-controller/userProfileController');
const userPostController = require('../user-controller/userPostController');
const userReportController = require('../user-controller/userReportController');
const app = express();

app.use(express.json());
app.use(express.urlencoded({extended: true}));

// Auth Routes
// User Register
app.post('/register', async (req, res) => {
  try {
    await userAuthController.registerUser(req, res);
  } catch (error) {
    console.error(error);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// User login
app.post('/login', async (req, res) => {
  try {
    await userAuthController.loginUser(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(401).json({error: error.message});
  }
});

// User logout
app.delete('/:userId/logout', async (req, res) => {
  try {
    await userAuthController.logoutUser(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(401).json({error: error.message});
  }
});

// User Profile Routes
// Memperbarui profile image
app.post('/:userId/update-prof-image', async (req, res) => {
  try {
    await userProfileController.updateUserProfileImg(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// Menghapus profile image
app.delete('/:userId/delete-prof-image', async (req, res) => {
  try {
    await userProfileController.deleteUserProfileImg(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// Mengganti Email atau Username
app.put('/:userId/edit-profile/edit', async (req, res) => {
  try {
    await userProfileController.updateUserInfo(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// Mengambil detail user
app.get('/:userId/user-detail', async (req, res) => {
  try {
    await userProfileController.getUserDetailById(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// User Post Routes
// Membuat postingan
app.post('/:userId/discussions/create-discussion', async (req, res) => {
  try {
    await userPostController.createPost(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// Memberi komentar pada postingan
app.post('/discussions/:postId/comment', async (req, res) => {
  try {
    await userPostController.addComment(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// Memberi like pada postingan
app.post('/discussions/:postId/like', async (req, res) => {
  try {
    await userPostController.addLike(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// Menghapus like pada postingan
app.delete('/discussions/:postId/unlike', async (req, res) => {
  try {
    await userPostController.removeLike(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// Mengambil detail postingan forum diskusi berdasarkan id postingan
app.get('/discussions/:postId', async (req, res) => {
  try {
    await userPostController.getPostDetail(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// Mengambil seluruh postingan diskusi yang ada di forum diskusi
app.get('/discussions', async (req, res) => {
  try {
    await userPostController.getAllPosts(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// User Report Routes
// Membuat user report
app.post('/:userId/report/create-report', async (req, res) => {
  try {
    await userReportController.createReport(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// Mendapatkan report dengan status/verifikasi
app.get('/:userId/report/report-history/terverifikasi', async (req, res) => {
  try {
    await userReportController.getVerifiedReports(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// Mendapatkan report dengan status/diproses
app.get('/:userId/report/report-history/diproses', async (req, res) => {
  try {
    await userReportController.getProcessedReports(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// Mendapatkan report dengan status/ditolak
app.get('/:userId/report/report-history/ditolak', async (req, res) => {
  try {
    await userReportController.getRejectedReports(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

// Mendapatkan report dengan status/diterima
app.get('/:userId/report/report-history/selesai', async (req, res) => {
  try {
    await userReportController.getFinishedReports(req, res);
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
});

module.exports = app;
