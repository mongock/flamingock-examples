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

package io.flamingock.examples.community;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.flamingock.examples.mongodb.springboot.sync.FlamingockCommunityEdition;
import io.flamingock.examples.mongodb.springboot.sync.changes._1_create_clients_collection;
import io.flamingock.examples.mongodb.springboot.sync.changes._2_insert_client_federico;
import io.flamingock.examples.mongodb.springboot.sync.changes._3_insert_client_jorge;
import io.flamingock.examples.mongodb.springboot.sync.events.FailureEventListener;
import io.flamingock.examples.mongodb.springboot.sync.events.StartedEventListener;
import io.flamingock.examples.mongodb.springboot.sync.events.SuccessEventListener;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static io.flamingock.oss.driver.common.mongodb.MongoDBDriverConfiguration.DEFAULT_MIGRATION_REPOSITORY_NAME;
import static io.flamingock.examples.mongodb.springboot.sync.FlamingockCommunityEdition.CLIENTS_COLLECTION_NAME;
import static io.flamingock.examples.mongodb.springboot.sync.FlamingockCommunityEdition.DATABASE_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@Import({HappyPathTest.TestConfiguration.class, FlamingockCommunityEdition.class})
class HappyPathTest {

    @Container
    public static final MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @Autowired
    private StartedEventListener startedEventListener;

    @Autowired
    private SuccessEventListener successEventListener;

    @Autowired
    private FailureEventListener failureEventListener;

    @Autowired
    private MongoClient mongoClient;


    @Test
    @DisplayName("SHOULD create clientCollection and insert two clients")
    void functionalTest() {
        Set<String> clients = mongoClient.getDatabase(DATABASE_NAME)
                .getCollection(CLIENTS_COLLECTION_NAME)
                .find()
                .map(document -> document.getString("name"))
                .into(new HashSet<>());

        assertTrue(clients.contains("Jorge"));
        assertTrue(clients.contains("Federico"));
        assertEquals(2, clients.size());
    }

    @Test
    @DisplayName("SHOULD insert the Flamingock change history")
    void flamingockLogsTest() {
        ArrayList<Document> flamingockDocuments = mongoClient.getDatabase(DATABASE_NAME)
                .getCollection(DEFAULT_MIGRATION_REPOSITORY_NAME)
                .find()
                .into(new ArrayList<>());

        Document aCreateCollection = flamingockDocuments.get(0);
        assertEquals("create-clients-collection", aCreateCollection.get("changeId"));
        assertEquals("EXECUTED", aCreateCollection.get("state"));
        assertEquals(_1_create_clients_collection.class.getName(), aCreateCollection.get("changeLogClass"));

        Document bInsertDocument = flamingockDocuments.get(1);
        assertEquals("insert-client-federico", bInsertDocument.get("changeId"));
        assertEquals("EXECUTED", bInsertDocument.get("state"));
        assertEquals(_2_insert_client_federico.class.getName(), bInsertDocument.get("changeLogClass"));

        Document cInsertAnotherDocument = flamingockDocuments.get(2);
        assertEquals("insert-client-jorge", cInsertAnotherDocument.get("changeId"));
        assertEquals("EXECUTED", cInsertAnotherDocument.get("state"));
        assertEquals(_3_insert_client_jorge.class.getName(), cInsertAnotherDocument.get("changeLogClass"));

        assertEquals(3, flamingockDocuments.size());
    }



    @Test
    @DisplayName("SHOULD trigger start and success event WHEN executed IF happy path")
    void events() {
        assertTrue(startedEventListener.executed);
        assertTrue(successEventListener.executed);
        assertFalse(failureEventListener.executed);
    }

    @Configuration
    static class TestConfiguration {
        @Bean
        @Primary
        public MongoClient mongoClient() {
            return MongoClients.create(MongoClientSettings
                    .builder()
                    .applyConnectionString(new ConnectionString(mongoDBContainer.getConnectionString()))
                    .build());
        }
    }
}