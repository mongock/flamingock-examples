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

package io.flamingock.examples.s3.dynamodb;

import io.flamingock.core.configurator.core.CoreConfiguration;
import io.flamingock.core.configurator.standalone.FlamingockStandalone;
import io.flamingock.core.pipeline.Stage;
import io.flamingock.oss.driver.dynamodb.driver.DynamoDBDriver;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URISyntaxException;

public class CommunityS3DynamoDBApp {

    public static void main(String[] args) throws URISyntaxException {
        new CommunityS3DynamoDBApp().run(S3Util.getClient(), DynamoDBUtil.getClient());
    }

    public void run(S3Client s3Client, DynamoDbClient dynamoDbClient) {

        FlamingockStandalone.local()
                .setDriver(new DynamoDBDriver(dynamoDbClient))
                .addStage(new Stage("stage-name")
                        .addCodePackage("io.flamingock.examples.s3.standalone.changes"))
                .addDependency(s3Client)
                .build()
                .run();
    }
}
