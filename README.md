# RIRI-CH2-PS323-CC

## Main Architecture
![RIRI Main Architecture](https://github.com/rivanap23/RIRI-CH2-PS323/raw/RIRI-Development-CC/Architecture/RIRI-MAIN-ARCHITECTURE.png)


Here, I will explain about the cloud resources that we use in the RIRI application. Broadly speaking, there are three cloud resources that we use in the development of the RIRI application, namely Cloud Run as a serverless computing option, Firestore NoSQL Database to store user information, and cloud storage to store image files sent by users. All of these resources are run in the same region, namely asia-southeast2 (Jakarta) to reduce the latency of accessing the RIRI application by our users who are targeted to be in Indonesia.

## Compute Architecture Detail
![RIRI Compute Architecture](https://github.com/rivanap23/RIRI-CH2-PS323/raw/RIRI-Development-CC/Architecture/RIRI-COMPUTE-ARCHITECTURE.png)

Now I will explain about the details for the cloud computing resource that we use which is Cloud Run. Cloud Run provides serverless computing services that allow developers to run Docker container-based applications automatically, besides that Cloud Run is also easy to manage in handling scalability and resource allocation according to the needs of the applications we develop.

To deploy our applications on Cloud Run is quite easy, first we need to build a Docker container for the application we created in this case we use the CI / CD service provided by Google Cloud, namely cloud build, after that the container application that we have built, is pushed to the Container Registery on Google Cloud. If our container application is already in the container registery then we can use it to be used in the Cloud Run service.

In the RIRI application, we run two Cloud Run services for two different APIs, the first is the NodeJs API to manage application functionality such as user authentication and features in the RIRI application such as discussion forums and user reports. Then, the Flask API for the fake and spam detection machine learning model in the user report feature.

## Database Architecture Detail
![RIRI Database Architecture](https://github.com/rivanap23/RIRI-CH2-PS323/raw/RIRI-Development-CC/Architecture/RIRI-DATABASE-ARCHITECTURE.png)

Next are the details for the Firestore Database service used in the RIRI application. In the RIRI application, the firestore database service is used to store user information which is divided into three collections, namely users to store user information data documents, user discussions to store data documents for posts, comments, and likes made by users, and finally user reports to store report information documents created by users.

## Storage Architecture Detail
![RIRI Storage Architecture](https://github.com/rivanap23/RIRI-CH2-PS323/raw/RIRI-Development-CC/Architecture/Storage%20Architecture.png)

The last detail about the cloud resources of the RIRI application that I will explain is cloud storage. In developing the RIRI application, we created three storage buckets to store image files sent by users. The three buckets have functions to store different image objects.

The first bucket serves to store image objects in the form of profile images from users with object names that have been set for each user, so that if the user changes his profile image, the previous object will be replaced and will not be stored in the bucket. The second bucket serves to store image objects in the form of user post images on the user discussion feature, and the third bucket serves to store image objects in the form of report images on the user report feature.

These buckets are set to be stored in a single region location type, namely asia-southeast2 and the same class that was originally created, namely standard storage. For object life cycle rules in buckets, it is only applied to buckets that store user report image data. Objects in this bucket will go to class nearline 30 days after the object is created, class coldline 90 days after the object is created, and class archive after one year in 365 days after the object is created.


## More Information about API

### NodeJs API Code
https://github.com/rivanap23/RIRI-CH2-PS323/tree/RIRI-Development-CC/RIRI-API

### Flask API Code
https://github.com/rivanap23/RIRI-CH2-PS323/tree/RIRI-Development-ML/api

### Endpoint Documentation
https://github.com/rivanap23/RIRI-CH2-PS323/tree/RIRI-Development-CC/Dokumentasi%20API

## Also Check Admin API for Admin Controller on User Reports
https://github.com/rivanap23/RIRI-CH2-PS323/tree/RIRI-Development-CC/RIRI-ADMIN-API


# Thank You!
