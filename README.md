![Header Image](misc/logo-with-text.png)
___

**Flamingock** is the evolution of Mongock, designed as a cloud-native solution for managing versioning, and auditing
changes in systems that evolve alongside your application.

Whilst Mongock focused on versioning NoSQL databases, Flamingock extends this concept to all technologies, systems, and
configurations, with built-in auditing and rollback capabilities. It ensures the application and its dependent
components evolve together, managing configuration changes during the application startup to enable seamless integration
and deployment.

Flamingock also introduces new mechanisms for defining changes in an extensible and customizable manner, beyond
traditional  code-based methods.

> Additionally, Flamingock offers multiple infrastructure setups for providing flexibility to users, as it introduces a
Cloud offering whilst still retaining existing supported databases such as MongoDB, DynamoDB, or Couchbase.

---

## 📖 Examples Overview

This repository is structured as **Individual Gradle Projects**, with each project demonstrating Flamingock's
integration with different frameworks, technologies, and use cases. Explore the examples to find the one that matches
your needs!

Each example is prepared to run from its own test with all infrastructure (databases, mocked servers, etc.) needed.
But you can also  run it with your own infrastructure.


## 🐥 Migration from Mongock to Flamingock

If you're transitioning from Mongock to Flamingock, we have separate migration guides for each use case:

- **[Standalone Applications](https://github.com/mongock/flamingock-project/blob/master/MONGOCK_STANDALONE_MIGRATION.md)**:
Detailed instructions for migrating from Mongock to Flamingock in a standalone Java application.
- **[Spring Boot Applications](https://github.com/mongock/flamingock-project/blob/master/MONGOCK_SPRINGBOOT_MIGRATION.md)**:
Step-by-step guide for migrating from Mongock to Flamingock in a Spring Boot application.

Each guide provides specific instructions tailored to the corresponding environment. Follow the appropriate guide to
ensure a smooth migration process.


---

## 📂 Index of Examples

## 📂 Index of Examples

| **Technology**       | **Example Project**                                                                | **Description**                                                                                                    |
|----------------------|------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------|
| **MongoDB**          | **[mongodb-sync-standalone](mongodb/mongodb-sync-standalone)**                     | Example of using Flamingock with the MongoDB synchronous driver in a Java standalone application.                  |
|                      | **[mongodb-springboot-springdata-2.x](mongodb/mongodb-springboot-springdata-2.x)** | Integration of Flamingock with MongoDB, Spring Boot 2.x, and Spring Data for seamless database migrations.         |
|                      | **[mongodb-springboot-springdata-3.x](mongodb/mongodb-springboot-springdata-3.x)** | Integration of Flamingock with MongoDB, Spring Boot 3.x, and Spring Data, leveraging the latest Spring features.   |
|                      | **[mongodb-springboot-sync](mongodb/mongodb-springboot-sync)**                     | Demonstrates Flamingock with MongoDB sync driver and Spring Boot, without relying on Spring Data abstractions.     |
| **DynamoDB**         | **[dynamodb-standalone](dynamodb/dynamodb-standalone)**                            | Example showcasing Flamingock with DynamoDB in a Java standalone application.                                      |
| **Couchbase**        | **[couchbase-standalone](couchbase/couchbase-standalone)**                         | Example of using Flamingock with Couchbase in a Java standalone application.                                       |
|                      | **[couchbase-springboot-v2](couchbase/couchbase-springboot-v2)**                   | Demonstrates Flamingock with Couchbase and Spring Boot 2.x for database migrations.                                |
| **MySQL**            | **[mysql-springboot](sql/mysql/mysql-springboot)**                                 | Integration of Flamingock with MySQL and Spring Boot 2.x for database schema migrations.                           |
| **GraalVM**          | **[graalvm-example](graalvm-example)**                                             | Demonstrates how to use Flamingock with GraalVM native image compilation for building fast, lightweight applications. |
> 🚀 **New examples will be added regularly!** Stay tuned for updates as we expand the repository to cover even more
> systems and frameworks.

---

## 🛠 How to Run Examples

**1. Clone this repository:**
```shell
   git clone https://github.com/mongock/flamingock-examples.git
   cd flamingock-examples
```

**2. Navigate to the example you want to explore:**
```shell
cd mongodb/mongodb-springboot-springdata
```

**3. Run example**

***3.a. Run the project test using Gradle***
```shell
./gradlew test
```

***3.b. Run the project using Gradle and your own infrastructure***
```shell
./gradlew run
```

**4. Follow the instructions in the specific project's README for further details.**

___

## 📢 Contributing
We welcome contributions! If you have an idea for a new example or improvement to an existing one, feel free to submit a
pull request. Check out our [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

___

## 🤝 Get Involved
⭐ Star the [Flamingock repository](https://github.com/mongock/flamingock-project) to show your support!

🐞 Report issues or suggest features in the [Flamingock issue tracker](https://github.com/mongock/flamingock-project/issues).

💬 Join the discussion in the [Flamingock community](https://github.com/mongock/flamingock-project/discussions).

___

## 📜 License
This repository is licensed under the [Apache License 2.0](LICENSE.md).

___

## 🔥 Explore, experiment, and empower your projects with Flamingock!
Let us know what you think or where you’d like to see Flamingock used next.
