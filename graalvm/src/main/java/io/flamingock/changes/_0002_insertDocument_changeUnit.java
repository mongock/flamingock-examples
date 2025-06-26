package io.flamingock.changes;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.flamingock.api.annotations.ChangeUnit;
import io.flamingock.api.annotations.Execution;
import io.flamingock.api.annotations.RollbackExecution;
import org.bson.Document;

@ChangeUnit(id = "insert-document", order = "0002")
public class _0002_insertDocument_changeUnit {
    @Execution
    public void execution(MongoDatabase mongoDatabase, ClientSession clientSession) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("clientCollection");
        collection.insertOne(clientSession, new Document().append("name", "Federico"));
    }

    @RollbackExecution
    public void rollBack() {
    }
}
