package io.flamingock;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.flamingock.core.configurator.standalone.FlamingockStandalone;
import io.flamingock.core.pipeline.Stage;
import io.flamingock.core.runner.Runner;
import io.flamingock.oss.driver.mongodb.sync.v4.driver.MongoSync4Driver;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class App {

    public static void main(String[] args) {
        getRunner().execute();
    }

    public final static String MONGODB_CONNECTION_STRING = "mongodb://localhost:27017/";
    public final static String MONGODB_MAIN_DB_NAME = "test";


    private static Runner getRunner() {
        MongoClient mainMongoClient = getMainMongoClient();
        FlamingockStandalone.local()
                .setDriver(new MongoSync4Driver(mainMongoClient, MONGODB_MAIN_DB_NAME))
                .setTrackIgnored(true)
                .setTransactionEnabled(true)
                .build();

         return FlamingockStandalone
                 .local()
                 .setDriver(new MongoSync4Driver(mainMongoClient, MONGODB_MAIN_DB_NAME))
                 .addStage(new Stage("stage-name").addCodePackage("io.flamingock.changes"))
                 .setLockAcquiredForMillis(60 * 1000L)//this is just to show how is set. Default value is still 60 * 1000L
                 .setLockQuitTryingAfterMillis(3 * 60 * 1000L)//this is just to show how is set. Default value is still 3 * 60 * 1000L
                 .setLockTryFrequencyMillis(1000L)//this is just to show how is set. Default value is still 1000L
                 .addDependency(mainMongoClient.getDatabase(MONGODB_MAIN_DB_NAME))
                 .setTrackIgnored(true)
                 .setTransactionEnabled(true)
                 .build();
    }

    private static MongoClient getMainMongoClient() {
        return buildMongoClientWithCodecs(MONGODB_CONNECTION_STRING);
    }

    private static MongoClient buildMongoClientWithCodecs(String connectionString) {

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