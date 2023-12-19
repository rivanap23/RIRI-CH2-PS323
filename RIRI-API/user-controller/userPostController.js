/* eslint-disable require-jsdoc */
/* eslint-disable max-len */
const admin = require('firebase-admin');
const storage = require('../storage-config/storage');
const bucket = storage.bucket('riri-discussion-file');
const db = require('../db-cofig/db');

const processFileMiddleware = require('../middleware/upload');

// Fungsi untuk membuat postingan
async function createPost(req, res) {
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

    const {title, content} = req.body;

    if (!title || !content) {
      return res.status(400).send({message: 'Judul dan konten perlu diisi!'});
    }

    if (!req.file) {
      return res.status(400).send({message: 'Gambar perlu diisi!'});
    }

    const timestamp = new Date().getTime();
    const imageName = `${username}-${timestamp}.png`;
    const blob = bucket.file(imageName);

    const blobStream = blob.createWriteStream({
      resumable: false,
    });

    blobStream.on('error', (err) => {
      res.status(500).send({message: err.message});
    });

    blobStream.on('finish', async () => {
      try {
        await blob.makePublic();
        const imageUrl = `https://storage.googleapis.com/${bucket.name}/${imageName}`;

        const postDocRef = await db.collection('userDiscussion').add({
          title,
          content,
          imageUrl,
          timestamp: admin.firestore.FieldValue.serverTimestamp(),
          username,
          likes: [],
          comments: [],
        });

        await userDocRef.update({
          posts: admin.firestore.FieldValue.arrayUnion(postDocRef.id),
        });

        await postDocRef.update({
          postId: postDocRef.id,
        });

        res.status(201).json({
          message: 'Postingan berhasil dibuat.',
          postId: postDocRef.id,
          imageUrl,
        });
      } catch (err) {
        res.status(500).send({
          message: `Terjadi error di database ${err}`,
        });
      }
    });

    blobStream.end(req.file.buffer);
  } catch (err) {
    res.status(500).send({
      message: `Gagal menambahkan postingan. ${err}`,
    });
  }
};

// Menambahkan komentar pada postingan
async function addComment(req, res) {
  try {
    const postId = req.params.postId;
    const {username, comment} = req.body;

    if (!postId || !username || !comment) {
      return res.status(400).send({message: 'Gagal menambahkan komentar pada postingan'});
    }

    const usersQuerySnapshot = await db.collection('users').where('username', '==', username).get();

    if (usersQuerySnapshot.empty) {
      return res.status(404).send({message: 'User tidak ditemukan.'});
    }

    const userData = usersQuerySnapshot.docs[0].data();
    const userProfileImage = userData.profileImageUrl || null;

    const postDocRef = db.collection('userDiscussion').doc(postId);
    const postDoc = await postDocRef.get();

    if (!postDoc.exists) {
      return res.status(404).send({message: 'Postingan tidak ditemukan.'});
    }

    const existingComments = postDoc.data().comments || [];

    const commentData = {
      username,
      comment,
      userProfileImage,
    };

    await postDocRef.update({
      comments: [...existingComments, commentData],
    });

    res.status(200).json({
      message: 'Komentar berhasil ditambahkan.',
      comment: commentData,
    });
  } catch (err) {
    res.status(500).send({message: `Gagal menambahkan komentar ${err}`});
  }
};


// Menambahkan like pada postingan
async function addLike(req, res) {
  try {
    const postId = req.params.postId;
    const {username} = req.body;

    if (!postId || !username) {
      return res.status(400).send({message: 'Postingan tidak ditemukan'});
    }

    const userQuerySnapshot = await db.collection('users').where('username', '==', username).get();

    if (userQuerySnapshot.empty) {
      return res.status(404).send({message: 'User tidak ditemukan'});
    }

    const postDocRef = db.collection('userDiscussion').doc(postId);

    await postDocRef.update({
      likes: admin.firestore.FieldValue.arrayUnion(username),
    });

    res.status(200).json({message: 'Like'});
  } catch (err) {
    res.status(500).send({message: `Gagal menambahkan like ${err}`});
  }
};

// Menghilangkan like dari postingan
async function removeLike(req, res) {
  try {
    const postId = req.params.postId;
    const {username} = req.body;

    if (!postId || !username) {
      return res.status(400).send({message: 'Isi semua ketentuan!'});
    }

    const userQuerySnapshot = await db.collection('users').where('username', '==', username).get();

    if (userQuerySnapshot.empty) {
      return res.status(404).send({message: 'User tidak ditemukan'});
    }

    const postDocRef = db.collection('userDiscussion').doc(postId);

    await postDocRef.update({
      likes: admin.firestore.FieldValue.arrayRemove(username),
    });

    res.status(200).json({message: 'Unlike'});
  } catch (err) {
    res.status(500).send({message: `Could not remove like. ${err}`});
  }
};

// Mendapatkan detail postingan
async function getPostDetail(req, res) {
  try {
    const postId = req.params.postId;

    if (!postId) {
      return res.status(400).send({message: 'Postingan tidak ditemukan!'});
    }

    const postDocRef = db.collection('userDiscussion').doc(postId);
    const postDoc = await postDocRef.get();

    if (!postDoc.exists) {
      return res.status(404).send({message: 'Postingan tidak ditemukan'});
    }

    const postData = postDoc.data();

    const username = postData.username;

    const userQuerySnapshot = await db.collection('users').where('username', '==', username).get();

    if (userQuerySnapshot.empty) {
      return res.status(404).send({message: 'User tidak ditemukan pada postingan.'});
    }

    const userData = userQuerySnapshot.docs[0].data();

    const postDetail = {
      postId: postDoc.id,
      title: postData.title,
      content: postData.content,
      imageUrl: postData.imageUrl,
      timestamp: postData.timestamp,
      username: userData.username,
      userProfileImage: userData.profileImageUrl || null,
      likes: postData.likes ? postData.likes.length : 0,
      comments: postData.comments || [],
    };

    res.status(200).json(postDetail);
  } catch (err) {
    res.status(500).send({message: `Gagal mendapatkan detail postingan ${err}`});
  }
};

// Mendapatkan seluruh postingan
async function getAllPosts(req, res) {
  try {
    const userDiscussionSnapshot = await db.collection('userDiscussion').get();

    const allPosts = [];

    await Promise.all(userDiscussionSnapshot.docs.map(async (postDoc) => {
      const postData = postDoc.data();

      const username = postData.username;
      const userQuerySnapshot = await db.collection('users').where('username', '==', username).get();

      if (userQuerySnapshot.empty) {
        console.error(`User tidak ditemukan pada postingan: ${username}`);
        return;
      }

      const userData = userQuerySnapshot.docs[0].data();

      const postDetail = {
        postId: postDoc.id,
        title: postData.title,
        content: postData.content,
        imageUrl: postData.imageUrl,
        timestamp: postData.timestamp,
        username: userData.username,
        userProfileImage: userData.profileImageUrl || null,
        likes: postData.likes ? postData.likes.length : 0,
        comments: postData.comments ? postData.comments.length : 0,
      };

      allPosts.push(postDetail);
    }));

    res.status(200).json(allPosts);
  } catch (err) {
    console.error(err);
    res.status(500).send({message: `Gagal mendapatkan postingan. ${err}`});
  }
};

module.exports = {
  createPost,
  addComment,
  addLike,
  removeLike,
  getPostDetail,
  getAllPosts,
};
