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

import io.flamingock.core.configurator.standalone.FlamingockStandalone;
import io.flamingock.core.pipeline.Stage;
import io.flamingock.examples.community.events.PipelineFailedListener;
import io.flamingock.examples.community.events.PipelineStartedListener;
import io.flamingock.examples.community.events.PipelineCompletedListener;
import io.flamingock.examples.community.events.StageCompletedListener;
import io.flamingock.examples.community.events.StageFailedListener;
import io.flamingock.examples.community.events.StageStartedListener;
import io.flamingock.oss.driver.mongodb.sync.v4.driver.MongoSync4Driver;
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
        FlamingockStandalone.local()
                .setDriver(new MongoSync4Driver(mongoClient, databaseName))
                .addStage(new Stage("stage-name")
                        .addCodePackage("io.flamingock.examples.community.changes"))
                .addDependency(mongoClient.getDatabase(databaseName))
                // All of these configurations are optional and set to their default values
                .setLockAcquiredForMillis(60 * 1000L)
                .setLockQuitTryingAfterMillis(3 * 60 * 1000L)
                .setLockTryFrequencyMillis(1000L)
                .setTrackIgnored(true)
                // Set optional Pipeline Listeners
                .setPipelineStartedListener(new PipelineStartedListener())
                .setPipelineCompletedListener(new PipelineCompletedListener())
                .setPipelineFailedListener(new PipelineFailedListener())
                .setStageStartedListener(new StageStartedListener())
                .setStageCompletedListener(new StageCompletedListener())
                .setStageFailedListener(new StageFailedListener())
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