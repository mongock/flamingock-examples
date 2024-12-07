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

package io.flamingock.mongock.importer.local.changes;

import io.flamingock.core.api.annotations.ChangeUnit;
import io.flamingock.core.api.annotations.Execution;
import io.flamingock.mongock.importer.local.client.Client;
import io.flamingock.mongock.importer.local.client.ClientRepository;

@ChangeUnit( id="insert-document" , order = "2")
public class BInsertDocument {

    @Execution
    public void execution(ClientRepository clientRepository) {
        clientRepository.save(new Client("Federico", null, null, null));
    }
}