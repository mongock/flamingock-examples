![Header Image](../../misc/logo-with-text.png)
___

# MongoDB Springboot with Springdata Example

## 📖 Example Overview

Welcome to the MongoDB Springboot with Springdata Example. This demonstrates how to use Flamingock with MongoDB in a
Java Springboot application with Springdata. It highlights key functionalities such as configuring Flamingock to use
Springdata as storage driver and importing previous audits from our legacy Mongock Library.

As Springboot application, Flamingock configuration was in *resources/application.yml*:
```yaml
flamingock:
   stages:
      - name: mongodb-migration
        code-packages:
           - io.flamingock.examples.mongodb.springboot.springdata.mongock
           - io.flamingock.examples.mongodb.springboot.springdata.changes
   transactionDisabled: false
   legacy-mongock-changelog-source: mongockChangeLog # setting this for importing Mongock legacy audits
```

This example has 3 Flamingock Changes:
1. Creates a new collection called *clientCollection*.
2. Adds one document to collection.
3. Adds another document to collection.

## Table of Contents

1. [📌 Dependencies](#-dependencies)
2. [🛠 How to Run this Example](#-how-to-run-this-example)
3. [✅ Proven Functionalities](#-proven-functionalities)

---

## 📌 Dependencies

This example requires the following dependencies:
### Flamingock Dependencies
    implementation("io.flamingock:flamingock-springboot-v2-runner:0.0.32-beta")
    implementation("io.flamingock:mongodb-springdata-v3-driver:0.0.32-beta")

### Springboot Dependency
    implementation("org.springframework.boot:spring-boot-starter-web")

### Springdata for MongoDB Dependency
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

Also, it requieres the following plugins:
### Springboot plugins
    id("org.springframework.boot") version "2.7.12"
    id("io.spring.dependency-management") version "1.1.0"

## 🛠 How to Run this Example

There are two ways to run this example:

### 1. Run Test (Recomended)
The recommended method to run this example is by executing the tests, which include a MongoDB TestContainer for testing
purposes.
```shell
./gradlew test
```

### 2. Run Main Class
To run the main class, ensure you have MongoDB running. Configure Springdata to use your own endpoint. Additionally:

1. Open the main class file
2. Change MongoDD endpoint in the following line for Mongock simulated execution:
```java
//        This line adds data to simulate previous legacy Mongock executions
        MongockExecutor.addMongockLegacyData("mongodb://localhost:27017/", DATABASE_NAME);
```
3. Run the example:
```shell
./gradlew run
```

## ✅ Proven functionalities

This example demonstrates the following functionalities:
1. Configuring Flamingock in a Springboot application for use Springdata as storage driver
   - Set Stages and other configuration in *resources/application.yml*
   - Set Listeners as Springboot Beans
2. Importing from Mongock
   - Setting Flamingock to import legacy Mongock audits using Springboot.

___

### 📢 Contributing
We welcome contributions! If you have an idea for a new example or improvement to an existing one, feel free to submit a
pull request. Check out our [CONTRIBUTING.md](../../CONTRIBUTING.md) for guidelines.

___

### 🤝 Get Involved
⭐ Star the [Flamingock repository](https://github.com/mongock/flamingock-project) to show your support!

🐞 Report issues or suggest features in the [Flamingock issue tracker](https://github.com/mongock/flamingock-project/issues).

💬 Join the discussion in the [Flamingock community](https://github.com/mongock/flamingock-project/discussions).

___

### 📜 License
This repository is licensed under the [Apache License 2.0](../../LICENSE.md).

___

### 🔥 Explore, experiment, and empower your projects with Flamingock!
Let us know what you think or where you’d like to see Flamingock used next.
