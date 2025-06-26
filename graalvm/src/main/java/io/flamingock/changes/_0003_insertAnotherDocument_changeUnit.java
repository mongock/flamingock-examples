package io.flamingock.changes;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.flamingock.api.annotations.ChangeUnit;
import io.flamingock.api.annotations.Execution;
import io.flamingock.api.annotations.RollbackExecution;
import org.bson.Document;

@ChangeUnit(id = "insert-another-document", order = "0003")
public class _0003_insertAnotherDocument_changeUnit {

    @Execution
    public void execution(MongoDatabase mongoDatabase, ClientSession clientSession) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("clientCollection");
        collection.insertOne(clientSession, new Document().append("name", "Jorge"));
    }

    @RollbackExecution
    public void rollBack() {
    }
}
