# User Post API
Dokumentasi pembuatan postingan oleh user


## Create Discussion

Endpoint : POST /:userId/discussions/create-discussion

Path Variables
- userId

Request Body :

```form-data

- title: "title"
- content: "content"
- file: image file

```

Response Body Success :

```json
{
  "data": {
    "message": "Postingan berhasil dibuat.",
    "postId": "postDocRef.id",
    "imageUrl": "imageUrl",
  }
}
```


## Add Comment on Post

Endpoint : POST /discussions/:postId/comment

Path Variables
- postId

Request Body :

```json
{
    "username": "user username",
    "comment": "comment"
}
```

Response Body Success :

```json
{
  "data": {
    "message": "Komentar berhasil ditambahkan.",
    "comment": "commentData",
  }
}
```


## Add Like on Post

Endpoint : POST /discussions/:postId/like

Path Variables
- postId

Request Body :

```json
{
    "username": "user username"
}
```

Response Body Success :

```json
{
    "message": "Berhasil menambahkan like.",
}
```


## Get User Post Detail

Endpoint : GET /discussions/:postId

Path Variables
- postId

Response Body Success :

```json
{
  "postDetail": {
    "postId": "postDoc.id",
    "title": "postData.title",
    "content": "postData.content",
    "imageUrl": "postData.imageUrl",
    "timestamp": "postData.timestamp",
    "username": "userData.username",
    "userProfileImage": "userData.profileImageUrl || null",
    "likes": "postData.likes ? postData.likes.length : 0",
    "comments": "postData.comments || []",
  }
}
```


## Get All User Post

Endpoint : GET /discussions

Response Body Success :

```json
{
  "allPosts": {
    "postDetail": {
      "postId": "postDoc.id",
      "title": "postData.title",
      "content": "postData.content",
      "imageUrl": "postData.imageUrl",
      "timestamp": "postData.timestamp",
      "username": "userData.username",
      "userProfileImage": "userData.profileImageUrl || null",
      "likes": "postData.likes ? postData.likes.length : 0",
      "comments": "postData.comments ? postData.comments.length : 0",
    }
  }
}
```
