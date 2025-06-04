/*
 * Copyright 2023 Flamingock (https://oss.flamingock.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.flamingock.examples.s3.dynamodb.changes;

import io.flamingock.core.api.annotations.ChangeUnit;
import io.flamingock.core.api.annotations.Execution;
import io.flamingock.core.api.annotations.RollbackExecution;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;

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