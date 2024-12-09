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

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import io.flamingock.examples.dynamodb.standalone.mongock._1_mongockInitialiseTableLegacyChangeUnit;
import io.mongock.driver.dynamodb.driver.DynamoDBDriver;
import io.mongock.runner.standalone.MongockStandalone;
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
                .region(Region.EU_WEST_1)
                .endpointOverride(new URI("http://localhost:8000"))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create("dummye", "dummye")
                        )
                )
                //.httpClient(UrlConnectionHttpClient.builder().build())
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



    public static void addMongockLegacyData() throws URISyntaxException {

        DynamoDBDriver mongockDriver = DynamoDBDriver.withDefaultLock(getAmazonDynamoDBClient());

        MongockStandalone.builder()
                .setDriver(mongockDriver)
                .addMigrationClass(_1_mongockInitialiseTableLegacyChangeUnit.class)
                .addDependency(getDynamoDbClient())
                .setTrackIgnored(true)
                .setTransactional(false)
                .buildRunner()
                .execute();
    }

    private static AmazonDynamoDBClient getAmazonDynamoDBClient() {
        return (AmazonDynamoDBClient) AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(
                                "http://localhost:8000", Region.EU_WEST_1.toString()
                        )
                )
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials("dummye", "dummye")
                        )
                )
                .build();
    }

    private static DynamoDbClient getDynamoDbClient() throws URISyntaxException {
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
}