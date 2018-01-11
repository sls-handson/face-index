# DEPLOY

	mvn clean package -Dno=xxx
	sls deploy --no xxx

	e.g. sls deploy --no A001

# Invoke

	sls invoke -f put -p event.json --no A001


# e.g.
## maven build
```
E:\workspaces\e.4.7.2\face-index>mvn clean package -Dno=A001
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building aws-spring-cloud-function-maven dev
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] --- maven-clean-plugin:2.6.1:clean (default-clean) @ aws-spring-cloud-function-maven ---
[INFO] Deleting E:\workspaces\e.4.7.2\face-index\target
～～～～～～
[INFO] Attaching shaded artifact.
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 6.978 s
[INFO] Finished at: 2018-01-10T18:04:36+09:00
[INFO] Final Memory: 87M/402M
[INFO] ------------------------------------------------------------------------

E:\workspaces\e.4.7.2\face-index>
```
## serverless deploy
```
E:\workspaces\e.4.7.2\face-index>sls deploy --no A001
Serverless: Packaging service...
Serverless: Creating Stack...
Serverless: Checking Stack create progress...
.....
Serverless: Stack create finished...
Serverless: Uploading CloudFormation file to S3...
Serverless: Uploading artifacts...
Serverless: Validating template...
Serverless: Updating Stack...
Serverless: Checking Stack update progress...
...............
Serverless: Stack update finished...
Service Information
service: A001-face-index
stage: dev
region: us-east-1
stack: A001-face-index-dev
api keys:
  None
endpoints:
  None
functions:
  put: A001-face-index-dev-put

E:\workspaces\e.4.7.2\face-index>
```
## Invoke
```
E:\workspaces\e.4.7.2\face-index>sls invoke -f put -p event.json --no A001
{
    "faceId": "5224adaf-36a2-45a8-94e8-49df9c2e863e",
    "boundingBox": {
        "width": 0.4091653,
        "height": 0.5118456,
        "left": 0.3717559,
        "top": 0.14331676
    },
    "imageId": "6b31eaef-e3c7-51fd-97c0-6a6072d7d53f",
    "externalImageId": "image3.jpg",
    "confidence": 99.99857
}

E:\workspaces\e.4.7.2\face-index>
```

## aws cli
```
aws rekognition list-faces --collection-id rekognition-satoh-test-id --region us-east-1 --profile sls-handson
{
    "Faces": [
        {
            "BoundingBox": {
                "Width": 0.40916499495506287,
                "Top": 0.14331699907779694,
                "Left": 0.371755987405777,
                "Height": 0.5118460059165955
            },
            "FaceId": "c9749b96-113e-4494-8867-fa5ba0d7872b",
            "ExternalImageId": "image3.jpg",
            "Confidence": 99.99859619140625,
            "ImageId": "6b31eaef-e3c7-51fd-97c0-6a6072d7d53f"
        }
    ]
}

aws rekognition delete-faces --collection-id rekognition-satoh-test-id --face-ids c9749b96-113e-4494-8867-fa5ba0d7872b --region us-east-1 --profile sls-handson
```