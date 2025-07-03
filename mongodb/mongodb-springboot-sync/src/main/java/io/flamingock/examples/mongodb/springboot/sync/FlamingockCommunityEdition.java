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

package io.flamingock.examples.mongodb.springboot.sync;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import io.flamingock.examples.mongodb.springboot.sync.events.FailureEventListener;
import io.flamingock.examples.mongodb.springboot.sync.events.StartedEventListener;
import io.flamingock.examples.mongodb.springboot.sync.events.SuccessEventListener;
import io.flamingock.springboot.v2.context.EnableFlamingock;
import io.flamingock.oss.driver.mongodb.sync.v4.driver.MongoSync4Driver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//Set Flamingock On
@EnableFlamingock
@SpringBootApplication
public class FlamingockCommunityEdition {
    public final static String DATABASE_NAME = "test";

    public final static String CLIENTS_COLLECTION_NAME = "clients";

    public static void main(String[] args) {
        SpringApplication.run(FlamingockCommunityEdition.class, args);
    }

//    Configure bean for Flamingock Driver to use
    @Bean
    public MongoSync4Driver connectionDriver(MongoClient mongoClient) {
        return new MongoSync4Driver(mongoClient, DATABASE_NAME);
    }

//    Configure beans for use in Changes
    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient) {
        return mongoClient.getDatabase(DATABASE_NAME);
    }

//    Configure Listeners beans
    @Bean
    public StartedEventListener startFlamingockListener() {
        return new StartedEventListener();
    }

    @Bean
    public SuccessEventListener successFlamingockListener() {
        return new SuccessEventListener();
    }

    @Bean
    public FailureEventListener sailedFlamingockListener() {
        return new FailureEventListener();
    }

}