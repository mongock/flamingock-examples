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

import io.flamingock.community.Flamingock;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class CommunityStandaloneMongodbSyncApp {


    public final static String DATABASE_NAME = "test";
    public static void main(String[] args) {
        new CommunityStandaloneMongodbSyncApp()
                .run(getMongoClient("mongodb://localhost:27017/"), DATABASE_NAME); // Set your MongoDB endpoint
    }


    public  void run(MongoClient mongoClient, String databaseName) {
        Flamingock.builder()
                .addDependency(mongoClient)
                .addDependency("mongodb.databaseName", databaseName)
                .addDependency(mongoClient.getDatabase(databaseName))
                //Build and Run
                .build()
                .run();
    }

    private static MongoClient getMongoClient(String connectionString) {

        CodecRegistry codecRegistry = fromRegistries(CodecRegistries.fromCodecs(new ZonedDateTimeCodec()),
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClientSettings.Builder builder = MongoClientSettings.builder();
        builder.applyConnectionString(new ConnectionString(connectionString));
        builder.codecRegistry(codecRegistry);
        MongoClientSettings build = builder.build();
        return MongoClients.create(build);
    }
}