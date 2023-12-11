const express = require("express");
const app = express();
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");

const admin = require("firebase-admin");
const credentials = require("./firestoreKey.json");

admin.initializeApp({
    credential: admin.credential.cert(credentials)
});

const db = admin.firestore();

app.use(express.json());

app.use(express.urlencoded({extended: true}));

//User Auth
//Generate Token
function generateAccessToken(userId) {
    return jwt.sign({ userId }, 'accessTokenSecret', { expiresIn: '2h' });
}

function generateRefreshToken(userId) {
    return jwt.sign({ userId }, 'refreshTokenSecret', { expiresIn: '1d' });

}

//User Register
app.post('/register', async (req, res) => {
    try {
        console.log(req.body);

        const { email, username, placeOfBirth, dateOfBirth, password, password2 } = req.body;

        if (!email || !username || !placeOfBirth || !dateOfBirth || !password || !password2) {
            throw new Error('Semua parameter harus diisi');
        }

        if (password !== password2) {
            throw new Error('Password harus sama');
        }

        const usernameExists = await db.collection("users").where('username', '==', username).get();
        if (!usernameExists.empty) {
            throw new Error('Username sudah digunakan');
        }
        
        const emailExists = await db.collection("users").where('email', '==', email).get();
        if (!emailExists.empty) {
            throw new Error('Email sudah terdaftar');
        }

        const hashedPassword = await bcrypt.hash(password, 10);

        const userRef = await db.collection("users").add({
            email,
            username,
            placeOfBirth,
            dateOfBirth,
            password: hashedPassword
        });

        const userId = userRef.id;
        res.status(201).json({ userId });
    } catch (error) {
        console.error(error);
        res.status(500).json({ error: 'Internal Server Error' });
    }
});

//UserLogin
app.post('/login', async (req, res) => {
    try {
        const { email, password } = req.body;

        if (!email || !password) {
            throw new Error('Email dan password harus diisi');
        }

        // Cari user berdasarkan email di Firestore
        const userSnapshot = await db.collection('users').where('email', '==', email).get();

        if (userSnapshot.empty) {
            throw new Error('Email tidak ditemukan');
        }

        const user = userSnapshot.docs[0].data();

        const passwordMatch = await bcrypt.compare(password, user.password);

        if (!passwordMatch) {
            throw new Error('Password tidak sesuai');
        }

        //accessToken dan refreshToken
        const accessToken = generateAccessToken(userSnapshot.docs[0].id);
        const refreshToken = generateRefreshToken(userSnapshot.docs[0].id);

        res.json({ accessToken, refreshToken });
    } catch (error) {
        console.error(error.message);
        res.status(401).json({ error: error.message });
    }
});

const PORT = process.env.PORT || 8000;
app.listen(PORT, () => {
    console.log(`Server is running on PORT ${PORT}.`)
});