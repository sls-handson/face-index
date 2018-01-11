package functions;

import java.util.List;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.Face;
import com.amazonaws.services.rekognition.model.FaceDetail;
import com.amazonaws.services.rekognition.model.FaceRecord;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.services.rekognition.model.IndexFacesRequest;
import com.amazonaws.services.rekognition.model.IndexFacesResult;
import com.amazonaws.services.rekognition.model.S3Object;

import functions.Function.Input;
import functions.Function.Output;

public class Function implements java.util.function.Function<Input, Output> {


	private final AmazonRekognition amazonRekognition;

	public Function(AmazonRekognition amazonRekognition) {
		this.amazonRekognition = amazonRekognition;
	}

	@Override
	public Output apply(final Input input) {
		final Output output = new Output();

		final Image image = new Image().withS3Object(new S3Object().withBucket(input.getS3Bucket()).withName(input.getS3Key()));

		final String[] keys = input.getS3Key().split("/");

		final IndexFacesRequest indexFacesRequest =
				new IndexFacesRequest()
				.withImage(image)
				.withCollectionId(input.getCollectionId())
				.withExternalImageId(keys[keys.length - 1])
				.withDetectionAttributes("ALL");

		final IndexFacesResult indexFacesResult = amazonRekognition.indexFaces(indexFacesRequest);

		final List<FaceRecord> faceRecords = indexFacesResult.getFaceRecords();

		final FaceRecord indexFaceRecord = faceRecords.get(0);

		final Face indexFace = indexFaceRecord.getFace();

		output.setBoundingBox(indexFace.getBoundingBox());
		output.setConfidence(indexFace.getConfidence());
		output.setExternalImageId(indexFace.getExternalImageId());
		output.setFaceId(indexFace.getFaceId());
		output.setImageId(indexFace.getImageId());

		return output;
	}

	public static final class Input {
		private String userId, s3Key, s3Bucket, collectionId;
		private FaceDetail detectedFaceDetails;

		public String getS3Key() {
			return s3Key;
		}

		public void setS3Key(String s3Key) {
			this.s3Key = s3Key;
		}

		public String getS3Bucket() {
			return s3Bucket;
		}

		public void setS3Bucket(String s3Bucket) {
			this.s3Bucket = s3Bucket;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getCollectionId() {
			return collectionId;
		}

		public void setCollectionId(String collectionId) {
			this.collectionId = collectionId;
		}

		public FaceDetail getDetectedFaceDetails() {
			return detectedFaceDetails;
		}

		public void setDetectedFaceDetails(FaceDetail detectedFaceDetails) {
			this.detectedFaceDetails = detectedFaceDetails;
		}

	}

	public static final class Output {
		private String faceId;
		private BoundingBox boundingBox;
		private String imageId;
		private String externalImageId;
		private Float confidence;

		public String getFaceId() {
			return faceId;
		}

		public void setFaceId(String faceId) {
			this.faceId = faceId;
		}

		public BoundingBox getBoundingBox() {
			return boundingBox;
		}

		public void setBoundingBox(BoundingBox boundingBox) {
			this.boundingBox = boundingBox;
		}

		public String getImageId() {
			return imageId;
		}

		public void setImageId(String imageId) {
			this.imageId = imageId;
		}

		public String getExternalImageId() {
			return externalImageId;
		}

		public void setExternalImageId(String externalImageId) {
			this.externalImageId = externalImageId;
		}

		public Float getConfidence() {
			return confidence;
		}

		public void setConfidence(Float confidence) {
			this.confidence = confidence;
		}
	}

}
