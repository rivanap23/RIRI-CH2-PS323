/* eslint-disable require-jsdoc */
/* eslint-disable max-len */
const processFile = require('../middleware/upload');
const {format} = require('util');
const admin = require('firebase-admin');

const storage = require('../storage-config/storage');
const bucket = storage.bucket('Bucket Name');
const db = require('../db-cofig/db');

// Menambahkan atau mengubah Profil Image
async function updateUserProfileImg(req, res) {
  try {
    const userId = req.params.userId;

    if (!userId) {
      return res.status(400).send({message: 'User ID tidak ditemukan'});
    }

    const userDocRef = db.collection('users').doc(userId);
    const userDoc = await userDocRef.get();

    if (!userDoc.exists) {
      return res.status(404).send({message: 'User tidak ditemukan di database'});
    }

    const userData = userDoc.data();
    const username = userData.username;

    await processFile(req, res);

    if (!req.file) {
      return res.status(400).send({message: 'Gambar diperlukan!'});
    }

    const imageName = `${username}-profile.png`;
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

        await userDocRef.update({
          profileImageUrl: publicUrl,
        });
      } catch (err) {
        return res.status(500).send({
          message: `Berhasil mengunggah gambar: ${imageName}, namun akses publik ditolak!`,
          url: publicUrl,
        });
      }

      res.status(200).send({
        message: 'Foto profil berhasil diperbaharui',
        url: publicUrl,
      });
    });

    blobStream.end(req.file.buffer);
  } catch (err) {
    res.status(500).send({
      message: `Gagal mengunggah gambar. ${err}`,
    });
  }
}

// Hapus user profile
async function deleteUserProfileImg(req, res) {
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
    const profileImageUrl = userData.profileImageUrl;

    if (!profileImageUrl) {
      return res.status(400).send({message: 'Profile image tidak ditemukan'});
    }

    const imageName = profileImageUrl.split('/').pop();
    const blob = bucket.file(imageName);

    await blob.delete();

    await userDocRef.update({
      profileImageUrl: admin.firestore.FieldValue.delete(),
    });

    res.status(200).json({message: 'Profile image berhasil dihapus'});
  } catch (err) {
    res.status(500).send({
      message: `Gagal menghapus profile image. ${err}`,
    });
  }
};

// Ganti Email dan/atau username
async function updateUserInfo(req, res) {
  try {
    const userId = req.params.userId;
    const newEmail = req.body.email;
    const newUsername = req.body.username;

    if (!userId) {
      return res.status(404).send({message: 'User tidak ditemukan'});
    }

    const userDocRef = db.collection('users').doc(userId);
    const userDoc = await userDocRef.get();

    if (!userDoc.exists) {
      return res.status(404).send({message: 'User tidak ditemukan di database.'});
    }

    if (newEmail) {
      const emailInUseQuery = await db.collection('users').where('email', '==', newEmail).get();

      if (!emailInUseQuery.empty) {
        return res.status(400).send({message: 'Email sudah digunakan'});
      }
    }

    if (newUsername) {
      const usernameInUseQuery = await db.collection('users').where('username', '==', newUsername).get();

      if (!usernameInUseQuery.empty) {
        return res.status(400).send({message: 'Username sudah digunakan'});
      }
    }

    const updateData = {};
    if (newEmail !== undefined) {
      updateData.email = newEmail;
    }
    if (newUsername !== undefined) {
      updateData.username = newUsername;
    }

    await userDocRef.update(updateData);

    // Update username or email di semua db.collection
    const collections = await db.listCollections();
    const updatePromises = [];

    for (const collectionRef of collections) {
      const collection = await collectionRef.get();
      const docs = collection.docs;

      for (const doc of docs) {
        const data = doc.data();

        if (data.userId === userId) {
          const docUpdateData = {};
          if (newEmail !== undefined) {
            docUpdateData.email = newEmail;
          }
          if (newUsername !== undefined) {
            docUpdateData.username = newUsername;
          }
          updatePromises.push(doc.ref.update(docUpdateData));
        }
      }
    }

    await Promise.all(updatePromises);

    res.status(200).json({message: 'Informasi pengguna berhasil diperbaharui'});
  } catch (err) {
    res.status(500).send({
      message: `Gagal memperbarui informasi pengguna. ${err}`,
    });
  }
};

// Mengambil data user berdasarkan ID.
async function getUserDetailById(req, res) {
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
    const {email, username, dateOfBirth, placeOfBirth, profileImageUrl} = userData;

    res.status(200).json({
      email,
      username,
      dateOfBirth,
      placeOfBirth,
      profileImageUrl,
    });
  } catch (err) {
    res.status(500).send({
      message: `Gagal mendapatkan informasi user. ${err}`,
    });
  }
};

module.exports = {
  updateUserProfileImg,
  deleteUserProfileImg,
  updateUserInfo,
  getUserDetailById,
};
