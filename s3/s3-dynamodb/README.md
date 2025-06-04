# Flamingock S3 Example

This example demonstrates how to use Flamingock with AWS S3. It shows how to create a changeUnit that creates an S3 bucket.

## Prerequisites

- Java 11 or higher
- Gradle
- LocalStack (for local development)

## Running the Example

1. Start LocalStack:

```bash
docker run -d -p 4566:4566 -p 8000:8000 localstack/localstack
```

2. Run the example:

```bash
./gradlew :s3:s3-standalone:run
```

## What This Example Does

This example demonstrates how to use Flamingock to manage changes to AWS S3. It includes:

1. A changeUnit that creates an S3 bucket
2. A rollback execution that deletes the bucket if needed

## Code Explanation

The main components of this example are:

### S3Util

A utility class that creates an S3 client for interacting with AWS S3.

```java
public static S3Client getClient() throws URISyntaxException {
    return S3Client.builder()
            .region(Region.EU_WEST_1)
            .endpointOverride(new URI("http://localhost:4566"))
            .credentialsProvider(
                    StaticCredentialsProvider.create(
                            AwsBasicCredentials.create("dummye", "dummye")
                    )
            )
            .build();
}
```

### DynamoDBUtil

A utility class that creates a DynamoDB client for Flamingock to store its metadata. Note that this is only used for Flamingock's internal metadata storage and is not related to the S3 operations.

```java
public static DynamoDbClient getClient() throws URISyntaxException {
    return DynamoDbClient.builder()
            .region(Region.EU_WEST_1)
            .endpointOverride(new URI("http://localhost:8000"))
            .credentialsProvider(
                    StaticCredentialsProvider.create(
                            AwsBasicCredentials.create("dummye", "dummye")
                    )
            )
            .build();
}
```

> **Note**: Flamingock requires a driver to store its metadata. Currently, there is no S3 driver available, so we use the DynamoDB driver for this purpose. The DynamoDB client is only used for Flamingock's internal metadata storage and is not related to the S3 operations, which use the S3 client.

### _0001_CreateS3BucketChange

A changeUnit that creates an S3 bucket and provides a rollback execution to delete it if needed.

```java
@ChangeUnit(id = "create-bucket", order = "0001", author = "dev-team")
public class _0001_CreateS3BucketChange {

  @Execution
  public void execute(S3Client s3Client) {
    s3Client.createBucket(CreateBucketRequest.builder()
        .bucket("flamingock-test-bucket")
        .build());
  }

  @RollbackExecution
  public void rollback(S3Client s3Client) {
    s3Client.deleteBucket(DeleteBucketRequest.builder()
        .bucket("flamingock-test-bucket")
        .build());
  }
}
```

### CommunityS3DynamoDBApp

The main application that configures and runs Flamingock.

```java
public void run(S3Client s3Client, DynamoDbClient dynamoDbClient) {
    FlamingockStandalone.local()
            .setDriver(new DynamoDBDriver(dynamoDbClient))
            .withImporter(CoreConfiguration.ImporterConfiguration.withSource("mongockChangeLog"))
            .addStage(new Stage("stage-name")
                    .addCodePackage("io.flamingock.examples.s3.standalone.changes"))
            .addDependency(s3Client)
            .build()
            .run();
}
```
