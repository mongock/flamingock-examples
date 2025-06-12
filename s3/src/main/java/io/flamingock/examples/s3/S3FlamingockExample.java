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

package io.flamingock.examples.s3;

import io.flamingock.examples.s3.util.DynamoDBUtil;
import io.flamingock.examples.s3.util.S3Util;
import io.flamingock.community.Flamingock;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URISyntaxException;

public class S3FlamingockExample {

    public static void main(String[] args) throws URISyntaxException {
        new S3FlamingockExample().run(S3Util.getClient(), DynamoDBUtil.getClient());
    }

    public void run(S3Client s3Client, DynamoDbClient dynamoDbClient) {
        Flamingock.builder()
                .addDependency(dynamoDbClient)
                .addDependency(s3Client)
                .build()
                .run();
    }
}