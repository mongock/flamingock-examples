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

package io.flamingock.examples.community.couchbase;

import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import io.flamingock.community.Flamingock;

public class CommunityStandaloneCouchbaseApp {

    private static final String BUCKET_NAME = "bucket";

    public static void main(String[] args) {
        new CommunityStandaloneCouchbaseApp().run(connect(), BUCKET_NAME);
    }

    public void run(Cluster cluster, String bucketName) {
        Collection collection = cluster.bucket(bucketName).defaultCollection();
        Flamingock.builder()
                .addDependency(cluster)
                .addDependency(collection)
                // These configurations are optional and set to their default values
                .setTrackIgnored(true)
                .disableTransaction()
                //Build and Run
                .build()
                .run();
    }

    private static Cluster connect() {
        return Cluster.connect("couchbase://localhost:11210", // Set your Couchbase endpoint
                "Administrator", // Set your Couchbase username
                "password"); // Set your Couchbase password
    }
}