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

package io.flamingock.examples.dynamodb.standalone;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DynamoDBUtil {
    public DynamoDBUtil() {
    }

    public static DynamoDbClient getClient() throws URISyntaxException {
        return DynamoDbClient.builder()
                .region(Region.EU_WEST_1) // Set your AWS region
                .endpointOverride(new URI("http://localhost:8000")) // Set your DynamoDB endpoint
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create("dummye", "dummye") // Set your AWS credentials
                        )
                )
                .build();
    }

    public static List<AttributeDefinition> getAttributeDefinitions(String pkName, String skName, String... vargs) {
        List<AttributeDefinition> result = new ArrayList<>();
        result.add(
                AttributeDefinition.builder()
                        .attributeName(pkName)
                        .attributeType(ScalarAttributeType.S)
                        .build()
        );
        result.add(
                AttributeDefinition.builder()
                        .attributeName(skName)
                        .attributeType(ScalarAttributeType.S)
                        .build()
        );
        for (String arg : vargs) {
            result.add(
                    AttributeDefinition.builder()
                            .attributeName(arg)
                            .attributeType(ScalarAttributeType.S)
                            .build()
            );
        }
        return result;
    }

    public static List<KeySchemaElement> getKeySchemas(String pkName, String skName) {
        return Arrays.asList(
                KeySchemaElement.builder()
                        .attributeName(pkName)
                        .keyType(KeyType.HASH)
                        .build(),
                KeySchemaElement.builder()
                        .attributeName(skName)
                        .keyType(KeyType.RANGE)
                        .build()
        );
    }

    public static ProvisionedThroughput getProvisionedThroughput(Long readCap, Long writeCap) {
        return ProvisionedThroughput.builder().readCapacityUnits(readCap).writeCapacityUnits(writeCap).build();
    }

    public static LocalSecondaryIndex generateLSI(String lsiName, String lsiPK, String lsiSK) {
        return LocalSecondaryIndex.builder()
                .indexName(lsiName)
                .keySchema(
                        Arrays.asList(
                                KeySchemaElement.builder()
                                        .attributeName(lsiPK).
                                        keyType(KeyType.HASH)
                                        .build(),
                                KeySchemaElement.builder()
                                        .attributeName(lsiSK)
                                        .keyType(KeyType.RANGE)
                                        .build()
                        )
                )
                .projection(Projection.builder()
                        .projectionType(ProjectionType.ALL)
                        .build())
                .build();
    }

    public static void createTable(
            DynamoDbClient dynamoDbClient,
            List<AttributeDefinition> attributeDefinitions,
            List<KeySchemaElement> keySchemas,
            ProvisionedThroughput provisionedVal,
            String tableName,
            List<LocalSecondaryIndex> localSecondaryIndexes,
            List<GlobalSecondaryIndex> globalSecondaryIndexes
    ) {
        try {
            CreateTableRequest.Builder createBuilder = CreateTableRequest.builder()
                    .attributeDefinitions(attributeDefinitions)
                    .keySchema(keySchemas)
                    .provisionedThroughput(provisionedVal)
                    .tableName(tableName);

            if (!localSecondaryIndexes.isEmpty()) {
                createBuilder.localSecondaryIndexes(localSecondaryIndexes);
            }

            if (!globalSecondaryIndexes.isEmpty()) {
                createBuilder.globalSecondaryIndexes(globalSecondaryIndexes);
            }

            DescribeTableRequest tableRequest;
            dynamoDbClient.createTable(createBuilder.build());
            tableRequest = DescribeTableRequest.builder()
                    .tableName(tableName)
                    .build();

            dynamoDbClient
                    .waiter()
                    .waitUntilTableExists(tableRequest).matched();
        } catch (ResourceInUseException e) {
            // Table already exists, continue
        }
    }
}
