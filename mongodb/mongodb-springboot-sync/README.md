![Header Image](../../misc/logo-with-text.png)

---

# MongoDB Spring Boot Example (Community Edition)

## ✨ Overview

This example showcases how to use **Flamingock Community Edition** in a Java **Spring Boot 2.x** application with **MongoDB Sync 4.x**. 
It demonstrates how to configure and execute Flamingock with **code-based change definitions** (it also supports **template-based** changes).

> While this example focuses on applying changes to a MongoDB database, Flamingock is designed to support a wide range of technologies—not limited to databases—and allows combining changes across different systems within the same project.

---

## 📊 What the Application Does

This example application demonstrates a simple process to apply and audit system changes during application startup using Flamingock. The configured pipeline contains a single stage with the following changes:

1. `_1_create_clients_collection.java`: **Creates** the `clients` collection in the MongoDB database.
2. `_2_insert_client_federico.java`: **Inserts a new client** into the `clients` collection.
3. `_3_insert_client_jorge.java`: **Inserts another new client** into the same collection.

---

## ⚖️ Flamingock Setup Highlights

### ⚡ Community Edition
This example uses the **Community Edition**, which runs entirely locally.

### 🔹 Annotation Processor
To enable Flamingock to detect and process changes, you must include the **annotation processor** in your Gradle configuration:
```kotlin
annotationProcessor("io.flamingock:flamingock-processor:$flamingockVersion")
```

### 📜 Pipeline Configuration
The pipeline is defined in:
```
src/main/resources/flamingock/pipeline.yaml
```
This file declares the stages to be processed. Each stage must define **at least one** of the following:

- `sourcesPackage`: A Java package containing Flamingock `@Change` classes or template-based files.
- `resourcesDir`: A directory containing template-based changes (such as YAML files).

You can use both in the same stage if needed.

> **Note**: The `pipeline.yaml` defines the order of the **stages**, while the order of the changes within each stage is defined by the `order` attribute in each change class or template.

#### 📅 Example pipeline.yaml
```yaml
pipeline:
  stages:
    - name: "mongodb-stage"
      description: "Stage processing a MongoDB migration"
      sourcesPackage: "io.flamingock.examples.mongodb.springboot.sync.changes"
```

---

## 📅 How to Run This Example

### ✅ Recommended: Run with TestContainers
This example uses [Testcontainers](https://www.testcontainers.org/) to spin up a real MongoDB instance for testing.
```bash
./gradlew test
```

### 🚀 Run the Application
Ensure MongoDB is running locally, then run:
```bash
./gradlew run
```
You can configure a custom `MongoClient` bean if needed.

---

## 🔹 Dependencies

### Flamingock:
```kotlin
implementation("io.flamingock:flamingock-springboot-v2-runner:$flamingockVersion")
implementation("io.flamingock:mongodb-sync-v4-driver:$flamingockVersion")
annotationProcessor("io.flamingock:flamingock-processor:$flamingockVersion")
```

### Spring Boot:
```kotlin
implementation("org.springframework.boot:spring-boot-starter-web")
```

### MongoDB:
```kotlin
implementation("org.mongodb:mongodb-driver-sync:4.3.3")
implementation("org.mongodb:mongodb-driver-core:4.3.3")
implementation("org.mongodb:bson:4.3.3")
```

---

## 🔧 Proven Functionalities

- ✅ Integration of Flamingock with Spring Boot 2.x
- ✅ Configuration via Spring Boot beans
- ✅ Pipeline execution through `pipeline.yaml`
- ✅ Use of Flamingock’s Java-based change API
- ✅ Event listeners for execution lifecycle (start, success, failure)

---

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

