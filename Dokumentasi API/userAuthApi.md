# User Auth Api
Dokumentasi authentikasi pengguna


## Register User

Endpoint : POST /register

Request Body :

```json
{
    "email": "user email",
    "username": "user username",
    "placeOfBirth": "City",
    "dateOfBirth": "DD/MM/YYYY",
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


## Login User

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


## Logout User

Endpoint : DELETE /:userId/logout

Path Variables
- userId

Response Body Success :

```json
{
  "message": "Logout berhasil"
}
```
