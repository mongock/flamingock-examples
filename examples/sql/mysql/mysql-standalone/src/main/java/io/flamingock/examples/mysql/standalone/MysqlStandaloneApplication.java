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

package io.flamingock.examples.mysql.standalone;

import io.flamingock.cloud.transaction.sql.SqlCloudTransactioner;
import io.flamingock.cloud.transaction.sql.SqlDialect;
import io.flamingock.core.cloud.transaction.CloudTransactioner;
import io.flamingock.core.configurator.standalone.FlamingockStandalone;
import io.flamingock.core.pipeline.Stage;
import io.flamingock.template.sql.SqlTemplateModule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlStandaloneApplication {

    private static final String SERVICE_NAME = "clients-service";
    private static final String ENVIRONMENT = "development";
    private static final String API_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6ImZsYW1pbmdvY2staW50ZXJuYWwifQ.eyJpc3MiOiJodHRwczovL2ZsYW1pbmdvY2suZXUuYXV0aDAuY29tIiwiYXVkIjoiaHR0cHM6Ly9hcGkuZmxhbWluZ29jay5pbyIsImlhdCI6MTczNzQ4NDMwOCwiZXhwIjoxNzY5MDIwMzA4LCJ0b2tlbl90eXBlIjoiYXBpX3Rva2VuIiwib3JnYW5pemF0aW9uIjoiMjlhNTlhOWYtNWJmNS00YTBiLWE3ZDUtZWJjNDcyNDU2ZTU2IiwicHJvamVjdCI6IjkyYTVhYzcwLTAxMzMtNDUzMS1hYmQyLTNkNDA5OTM3YjRjMyIsImVudmlyb25tZW50IjoiYWIzYTI2MmUtNjNjMy00YzMwLWE5MDMtOWVhZjlmMjViOWM4Iiwic2VydmljZSI6ImVjNzM3NzY2LWEzYzUtNGJjNS05NDNjLTJjNDRlNDRmYWIwYSJ9.Go1I4PExbu6srPVLZAbyfM3ABppddncZz6lbHYfB7LGyogJSaLJXxcNUbEvOMRwGBSFh7euIp9aj3H3l9wRz7-Zccr_EjQpnAkVdqY7-Lgj7x4UiD81LR_nDShUiqqwYSwhwx7B8OSZ8KzX1xzUpGYECsm-bGfKqR3IBi-sOqhSo7Ew4ygUhL-a7dSrwvikNqroXutp-8T_aBk2_fcBevIaIR6UcQSiwxyP2V61KZocI4tb6xMZ2CijRimeA970CezFQKLuGn0tqUV8mP1mUHzLJaC7fKbXNkpvaHc-Ai-YjUNi_gbAdWeGlo-MA2Tj_YuVeOScl5k-MXHqGsQ";

    public static void main(String[] args) throws ClassNotFoundException {
        new MysqlStandaloneApplication()
                .run();

    }

    public void run() throws ClassNotFoundException {
        try (CloudTransactioner cloudTransactioner = getSqlCloudTransactioner()) {
            FlamingockStandalone
                    .cloud()
                    .setHost("http://localhost:8080")
                    .setApiToken(API_TOKEN)
                    .setEnvironment(ENVIRONMENT)
                    .setService(SERVICE_NAME)
                    .setCloudTransactioner(cloudTransactioner)//for cloud transactions with Sql
                    .setLockAcquiredForMillis(6 * 1000L)//this is just to show how is set. Default value is still 60 * 1000L
                    .setLockQuitTryingAfterMillis(10 * 1000L)//this is just to show how is set. Default value is still 3 * 60 * 1000L
                    .setLockTryFrequencyMillis(3000L)//this is just to show how is set. Default value is still 1000L
                    .addStage(new Stage("database_stage").addFileDirectory("flamingock/stage1"))
                    .addTemplateModule(new SqlTemplateModule())
                    .addDependency(Connection.class, getConnection())
                    .build()
                    .run();
        }
    }

    private static SqlCloudTransactioner getSqlCloudTransactioner() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return new SqlCloudTransactioner()
                .setDialect(SqlDialect.MYSQL)
                .setUrl("jdbc:mysql://localhost:3307/flamingock")
                .setUser("root")
                .setPassword("strong_password");
    }

    //Temporally because we haven't injected transactional = true
    private static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3307/flamingock", "root", "strong_password");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}