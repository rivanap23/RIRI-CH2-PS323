# userAuthApi
Dokumentasi authentikasi pengguna


## Register User API

Endpoint : POST /register

Request Body :

```json
{
    "email": "rivanakbar230702@gmail.com",
    "username": "escobar_12",
    "placeOfBirth": "City",
    "dateOfBirth": "23/07/20002",
    "password": "password123",
    "password2": "password123"
}
```

Response Body Success :

```json
{
  "data": {
    "userId": "UserId",
    "message": "sukses mendaftar"
  }
}
```


## Login User API

Endpoint : POST /login

Request Body :

```json
{
    "email": "rivanakbar230702@gmail.com",
    "password": "password123"
}
```

Response Body Success :

```json
{
  "data": {
    "userId": "userDoc.id",
      "username": "user.username",
      "accessToken": "accessToken",
      "message": "Login berhasil",
  }
}
```


## Logout User API

Endpoint : DELETE /:userId/logout

Path Variables
- userId

Response Body Success :

```json
{
  "message": "Logout berhasil"
}
```
