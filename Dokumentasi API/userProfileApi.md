# User Report API
Dokumentasi report yang dibuat user.


## Update Profile Image

Endpoint : POST /:userId/update-prof-imagecreate-discussion

Path Variables
- userId

Request Body :

```form-data

- file: image file

```

Response Body Success :

```json
{
  "data": {
    "message": "Uploaded the file successfully",
    "imageUrl": "imageUrl",
  }
}
```


## Delete Profile Image

Endpoint : DELETE /:userId/delete-prof-image

Path Variables
- userId

Response Body Success :

```json
{
  "data": {
    "message": "Profile image berhasil dihapus.",
  }
}
```


## Edit Username and Email

Endpoint : PUT /:userId/edit-profile/edit

Path Variables
- userId

Request Body :

```json
{
    "email": "exampleio@gmail.com",
    "username": "example444"
}

{
    "email": "exampleio@gmail.com"
}

{
    "username": "example444"
}
```

Response Body Success :

```json
{
    "message": "Informasi pengguna berhasil diperbaharui",
}
```


## Get User Data by Id

Endpoint : GET /:userId/user-detail

Path Variables
- userId

Response Body Success :

```json
{
  "data": {
      "email": "user email",
      "username": "user username",
      "dateOfBirth": "user DOB",
      "placeOfBirth": "user POB",
      "profileImageUrl": "user profile image url",
  }
}
```
