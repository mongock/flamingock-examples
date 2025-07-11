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

package io.flamingock.examples.s3.util;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.net.URISyntaxException;

public final class DynamoDBUtil {
    public DynamoDBUtil() {
    }

    public static DynamoDbClient getClient() throws URISyntaxException {
        return DynamoDbClient.builder()
                .region(Region.EU_WEST_1) // Set your AWS region
                .endpointOverride(new URI("http://localhost:4566")) // Set your DynamoDB endpoint
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create("dummye", "dummye") // Set your AWS credentials
                        )
                )
                .build();
    }
}
