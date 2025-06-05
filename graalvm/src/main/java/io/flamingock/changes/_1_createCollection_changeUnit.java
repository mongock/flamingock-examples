package io.flamingock.changes;

import com.mongodb.client.MongoDatabase;
import io.flamingock.core.api.annotations.BuildTimeProcessable;
import io.flamingock.core.api.annotations.ChangeUnit;
import io.flamingock.core.api.annotations.Execution;
import io.flamingock.core.api.annotations.RollbackExecution;

@BuildTimeProcessable
@ChangeUnit(id = "create-collection", order = "1", transactional = false)
public class _1_createCollection_changeUnit {

    @Execution
    public void execution(MongoDatabase mongoDatabase) {
        mongoDatabase.createCollection("clientCollection");
    }

    @RollbackExecution
    public void rollBack() {
    }
}
