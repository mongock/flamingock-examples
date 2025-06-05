package io.flamingock.changes;

import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.flamingock.core.api.annotations.BuildTimeProcessable;
import io.flamingock.core.api.annotations.ChangeUnit;
import io.flamingock.core.api.annotations.Execution;
import io.flamingock.core.api.annotations.RollbackExecution;
import org.bson.Document;


@BuildTimeProcessable
@ChangeUnit(id = "insert-document", order = "2")
public class _2_insertDocument_changeUnit {
    @Execution
    public void execution(MongoDatabase mongoDatabase, ClientSession clientSession) {
        MongoCollection<Document> collection = mongoDatabase.getCollection("clientCollection");
        collection.insertOne(clientSession, new Document().append("name", "Federico"));
    }

    @RollbackExecution
    public void rollBack() {
    }
}
