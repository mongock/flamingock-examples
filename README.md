# Flamingock Examples 🐓🔥

Welcome to the **Flamingock Examples** repository!  
This project showcases practical use cases of **[Flamingock](https://github.com/mongock/flamingock-project)** across various
scenarios, frameworks, and technologies. Whether you're working with databases, message queues, or other systems, these
examples will help you integrate and use Flamingock seamlessly.

---

## 🔍 What is Flamingock?

**Flamingock** is a versatile migration tool designed for modern, cloud-native applications. It enables easy tracking,
versioning, and execution of changeUnits in a way that’s scalable and auditable. Flamingock is the evolution of Mongock,
expanding its capabilities to support a broader range of systems.

---

## 📖 Examples Overview

This repository is structured as a **Gradle Kotlin multiproject**, with each subproject demonstrating Flamingock's
integration with different frameworks, technologies, and use cases. Explore the examples to find the one that matches
your needs!

---

## 📂 Index of Examples

| **Technology**       | **Example Subproject**                                                     | **Description**                                                                                                    |
|----------------------|----------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------|
| **MongoDB**          | **[mongodb-sync-standalone](mongodb-sync-standalone)**                     | Example of using Flamingock with the MongoDB synchronous driver in a Java standalone application.                  |
|                      | **[mongodb-springboot-springdata-2.x](mongodb-springboot-springdata-2.x)** | Integration of Flamingock with MongoDB, Spring Boot 2.x, and Spring Data for seamless database migrations.         |
|                      | **[mongodb-springboot-springdata-3.x](mongodb-springboot-springdata-3.x)** | Integration of Flamingock with MongoDB, Spring Boot 3.x, and Spring Data, leveraging the latest Spring features.   |
|                      | **[mongodb-springboot-sync](mongodb-springboot-sync)**                     | Demonstrates Flamingock with MongoDB sync driver and Spring Boot, without relying on Spring Data abstractions.     |
| **DynamoDB**         | **[dynamodb-standalone](dynamodb-standalone)**                             | Example showcasing Flamingock with DynamoDB in a Java standalone application.                                      |
| **Couchbase**        | **[couchbase-standalone](couchbase-standalone)**                           | Example of using Flamingock with Couchbase in a Java standalone application.                                       |
|                      | **[couchbase-springboot-v2](couchbase-springboot-v2)**                     | Demonstrates Flamingock with Couchbase and Spring Boot 2.x for database migrations.                                |
| **MySQL**            | **[mysql-standalone](mysql-standalone)**                                   | Example showcasing Flamingock with MySQL in a Java standalone application.                                         |
|                      | **[mysql-springboot](mysql-springboot)**                                   | Integration of Flamingock with MySQL and Spring Boot 2.x for database schema migrations.                           |

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
cd mongodb-springboot-springdata
```

**3. Run the project using Gradle**
```shell
./gradlew bootRun
```

**4. Follow the instructions in the specific subproject's README for further details.**

___

## 📢 Contributing
We welcome contributions! If you have an idea for a new example or improvement to an existing one, feel free to submit a pull request. Check out our [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

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