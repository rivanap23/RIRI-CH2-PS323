/* eslint-disable max-len */
/* eslint-disable require-jsdoc */
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const admin = require('firebase-admin');

const db = require('../db-cofig/db');

// Registrasi User
function generateAccessToken(userId) {
  return jwt.sign({userId}, 'accessTokenSecret', {expiresIn: '3d'});
};

async function registerUser(req, res) {
  try {
    console.log(req.body);

    const {email, username, placeOfBirth, dateOfBirth, password, password2} = req.body;

    if (!email || !username || !placeOfBirth || !dateOfBirth || !password || !password2) {
      return res.status(400).json({message: 'Semua kolom harus diisi'});
    }

    if (password !== password2) {
      return res.status(400).json({message: 'Password harus sama'});
    }

    const usernameExists = await db.collection('users').where('username', '==', username).get();
    if (!usernameExists.empty) {
      return res.status(409).json({message: 'Username sudah digunakan'});
    }

    const emailExists = await db.collection('users').where('email', '==', email).get();
    if (!emailExists.empty) {
      return res.status(409).json({message: 'Email sudah terdaftar'});
    }

    const hashedPassword = await bcrypt.hash(password, 10);

    const userRef = await db.collection('users').add({
      email,
      username,
      placeOfBirth,
      dateOfBirth,
      password: hashedPassword,
    });

    const userId = userRef.id;

    await db.collection('users').doc(userId).update({
      userId: userId,
    });

    res.status(201).json({
      userId,
      message: 'sukses mendaftar',
    });
  } catch (error) {
    console.error(error);
    res.status(500).json({error: 'Internal Server Error'});
  }
};

// Login User
async function loginUser(req, res) {
  try {
    const {email, password} = req.body;

    if (!email || !password) {
      return res.status(400).json({message: 'Email dan password harus diisi'});
    }

    const userSnapshot = await db.collection('users').where('email', '==', email).get();

    if (userSnapshot.empty) {
      return res.status(401).json({message: 'Email tidak ditemukan'});
    }

    const userDoc = userSnapshot.docs[0];
    const user = userDoc.data();

    const passwordMatch = await bcrypt.compare(password, user.password);

    if (!passwordMatch) {
      return res.status(401).json({message: 'Password tidak sesuai'});
    }

    const accessToken = generateAccessToken(userDoc.id);

    await db.collection('users').doc(userDoc.id).update({
      accessToken,
    });

    res.status(200).json({
      userId: userDoc.id,
      username: user.username,
      accessToken,
      message: 'Login berhasil',
    });
  } catch (error) {
    console.error(error.message);
    res.status(401).json({error: error.message});
  }
};

// Logout User
async function logoutUser(req, res) {
  try {
    const userId = req.params.userId;

    if (!userId) {
      return res.status(400).json({message: 'Invalid user ID'});
    }

    await db.collection('users').doc(userId).update({
      accessToken: admin.firestore.FieldValue.delete(),
    });

    res.status(200).json({
      message: 'Logout berhasil',
    });
  } catch (error) {
    console.error(error.message);
    res.status(500).json({error: 'Internal Server Error'});
  }
};

module.exports = {registerUser, loginUser, logoutUser};
