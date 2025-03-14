package io.flamingock.examples.mongodb.springboot.springdata.mongock;

import com.mongodb.client.MongoDatabase;
import io.mongock.api.annotations.*;
import org.bson.Document;

import java.util.stream.Collectors;
import java.util.stream.IntStream;


@ChangeUnit(id = "legacy-mongock-change-unit", order = "1", author = "mongock")
public class MongockLegacyChangeUnit {

    public final static int INITIAL_CLIENTS = 10;
    public final static String CLIENTS_COLLECTION_NAME = "mongockClientCollection";

    @BeforeExecution
    public void beforeExecution(MongoDatabase mongoDatabase) {

        mongoDatabase.createCollection(CLIENTS_COLLECTION_NAME);
    }

    @RollbackBeforeExecution
    public void rollbackBeforeExecution(MongoDatabase mongoDatabase) {

        mongoDatabase.getCollection(CLIENTS_COLLECTION_NAME).drop();
    }

    @Execution
    public void execution(MongoDatabase mongoDatabase) {

        mongoDatabase.getCollection(CLIENTS_COLLECTION_NAME)
                .insertMany(IntStream.range(0, INITIAL_CLIENTS)
                        .mapToObj(MongockLegacyChangeUnit::getClient)
                        .collect(Collectors.toList()));
    }

    @RollbackExecution
    public void rollbackExecution(MongoDatabase mongoDatabase) {
        mongoDatabase.getCollection(CLIENTS_COLLECTION_NAME).deleteMany(new Document());
    }

    private static Document getClient(int i) {
        return new Document()
                .append("name", "name-" + i)
                .append("email","email-" + i)
                .append("phone","phone" + i).
                append("country","country" + i);
    }
}
