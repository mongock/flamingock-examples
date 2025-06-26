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

package io.flamingock.examples.mongodb.springboot.springdata;


import io.flamingock.examples.mongodb.springboot.springdata.config.MongoInitializer;
import io.flamingock.examples.mongodb.springboot.springdata.events.PipelineCompletedListener;
import io.flamingock.examples.mongodb.springboot.springdata.events.PipelineFailedListener;
import io.flamingock.examples.mongodb.springboot.springdata.events.PipelineStartedListener;
import io.flamingock.examples.mongodb.springboot.springdata.events.StageCompletedListener;
import io.flamingock.examples.mongodb.springboot.springdata.events.StageFailedListener;
import io.flamingock.examples.mongodb.springboot.springdata.events.StageStartedListener;
import org.bson.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Import({MongodbSpringbootSpringdata.class})
@ContextConfiguration(initializers = MongoInitializer.class)
class SpringData3SuccessExecutionTest {

    @Autowired
    private PipelineStartedListener pipelineStartedListener;

    @Autowired
    private PipelineCompletedListener pipelineCompletedListener;

    @Autowired
    private PipelineFailedListener pipelineFailedListener;

    @Autowired
    private StageStartedListener stageStartedListener;

    @Autowired
    private StageCompletedListener stageCompletedListener;

    @Autowired
    private StageFailedListener stageFailedListener;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    @DisplayName("SHOULD create clientCollection and insert two clients")
    void functionalTest() {
        Set<String> clients = mongoTemplate.getCollection(MongodbSpringbootSpringdata.CLIENTS_COLLECTION_NAME)
                .find()
                .map(document -> document.getString("name"))
                .into(new HashSet<>());

        assertTrue(clients.contains("Jorge"));
        assertTrue(clients.contains("Federico"));
        assertEquals(2, clients.size());
    }

    @Test
    @DisplayName("SHOULD insert the Flamingock change history for all, code and templated changeUnits")
    void flamingockLogsTest() {
        ArrayList<Document> flamingockDocuments = mongoTemplate.getCollection("flamingockAuditLogs")
                .find()
                .into(new ArrayList<>());


        //New changes added
        Document aCreateCollection = flamingockDocuments.get(5);
        assertEquals("create-collection", aCreateCollection.get("changeId"));
        assertEquals("EXECUTED", aCreateCollection.get("state"));
        assertEquals("io.flamingock.examples.mongodb.springboot.springdata.changes.ACreateCollection", aCreateCollection.get("changeLogClass"));

        Document bInsertDocument = flamingockDocuments.get(6);
        assertEquals("insert-document", bInsertDocument.get("changeId"));
        assertEquals("EXECUTED", bInsertDocument.get("state"));
        assertEquals("io.flamingock.examples.mongodb.springboot.springdata.changes.BInsertDocument", bInsertDocument.get("changeLogClass"));

        Document cInsertAnotherDocument = flamingockDocuments.get(7);
        assertEquals("insert-another-document", cInsertAnotherDocument.get("changeId"));
        assertEquals("EXECUTED", cInsertAnotherDocument.get("state"));
        assertEquals("io.flamingock.examples.mongodb.springboot.springdata.changes.CInsertAnotherDocument", cInsertAnotherDocument.get("changeLogClass"));

        //Form importing mongock legacy data
        Document mongoSystemChangeBefore = flamingockDocuments.get(0);
        assertEquals("[mongock]system-change-00001_before", mongoSystemChangeBefore.get("changeId"));
        assertEquals("EXECUTED", mongoSystemChangeBefore.get("state"));
        assertEquals("io.mongock.runner.core.executor.system.changes.SystemChangeUnit00001", mongoSystemChangeBefore.get("changeLogClass"));

        Document mongoSystemChange = flamingockDocuments.get(1);
        assertEquals("[mongock]system-change-00001", mongoSystemChange.get("changeId"));
        assertEquals("EXECUTED", mongoSystemChange.get("state"));
        assertEquals("io.mongock.runner.core.executor.system.changes.SystemChangeUnit00001", mongoSystemChange.get("changeLogClass"));

        Document changeUnitExecutedInMongockBefore = flamingockDocuments.get(2);
        assertEquals("[mongock]legacy-mongock-change-unit_before", changeUnitExecutedInMongockBefore.get("changeId"));
        assertEquals("EXECUTED", changeUnitExecutedInMongockBefore.get("state"));
        assertEquals("io.flamingock.examples.mongodb.springboot.springdata.mongock.MongockLegacyChangeUnit", changeUnitExecutedInMongockBefore.get("changeLogClass"));

        Document changeUnitExecutedInMongock = flamingockDocuments.get(3);
        assertEquals("[mongock]legacy-mongock-change-unit", changeUnitExecutedInMongock.get("changeId"));
        assertEquals("EXECUTED", changeUnitExecutedInMongock.get("state"));
        assertEquals("io.flamingock.examples.mongodb.springboot.springdata.mongock.MongockLegacyChangeUnit", changeUnitExecutedInMongock.get("changeLogClass"));

        Document legacyImporterChangeUnit = flamingockDocuments.get(4);
        assertEquals("importer-from-mongock", legacyImporterChangeUnit.get("changeId"));
        assertEquals("EXECUTED", legacyImporterChangeUnit.get("state"));
        assertEquals("io.flamingock.core.engine.audit.importer.changeunit.MongockImporterChangeUnit", legacyImporterChangeUnit.get("changeLogClass"));


        //8 changes: 3 new changes we are adding plus that come from legacy importer
        assertEquals(8, flamingockDocuments.size());
    }


    @Test
    @DisplayName("SHOULD trigger start and success event WHEN executed IF happy path")
    void events() {
        assertTrue(pipelineStartedListener.executed);
        assertTrue(pipelineCompletedListener.executed);
        assertFalse(pipelineFailedListener.executed);

        //2 because the importer runs on its own stage
        assertEquals(2, stageStartedListener.executed);
        assertEquals(2, stageCompletedListener.executed);
        assertEquals(0, stageFailedListener.executed);
    }
}