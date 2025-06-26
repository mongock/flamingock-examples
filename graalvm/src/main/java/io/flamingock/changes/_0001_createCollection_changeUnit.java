package io.flamingock.changes;

import com.mongodb.client.MongoDatabase;
import io.flamingock.api.annotations.ChangeUnit;
import io.flamingock.api.annotations.Execution;
import io.flamingock.api.annotations.RollbackExecution;

@ChangeUnit(id = "create-collection", order = "0001", transactional = false)
public class _0001_createCollection_changeUnit {

    @Execution
    public void execution(MongoDatabase mongoDatabase) {
        mongoDatabase.createCollection("clientCollection");
    }

    @RollbackExecution
    public void rollBack() {
    }
}
