![Header Image](../../misc/logo-with-text.png)
___

# MongoDB Standalone Example

## 📖 Example Overview

Welcome to the MongoDB Standalone Example. This demonstrates how to use Flamingock with MongoDB in a Java
standalone application. It highlights key functionalities such as auditing changes, configuring advanced and optional
Flamingock Builder options, and implementing Pipeline Listeners.

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
    implementation("io.flamingock:flamingock-core:0.0.32-beta")
    implementation("io.flamingock:mongodb-sync-v4-driver:0.0.32-beta")

### MongoDB Dependencies
    implementation("org.mongodb:mongodb-driver-sync:4.3.3")
    implementation("org.mongodb:mongodb-driver-core:4.3.3")
    implementation("org.mongodb:bson:4.3.3")

## 🛠 How to Run this Example

There are two ways to run this example:

### 1. Run Test (Recomended)
The recommended method to run this example is by executing the tests, which include a MongoDB TestContainer for testing
purposes.
```shell
./gradlew test
```

### 2. Run Main Class
To run the main class, ensure you have MongoDB running. Configure the MongoDB client with your settings:

1. Open the main class file
2. Change the MongoDB endpoint with your own:
```java
public static void main(String[] args) {
    new CommunityStandaloneMongodbSyncApp()
            .run(getMongoClient("mongodb://localhost:27017/"), DATABASE_NAME); // Set your MongoDB endpoint
}
```
3. Run the example:
```shell
./gradlew run
```

## ✅ Proven functionalities

This example demonstrates the following functionalities:
1. Auditing Changes with MongoDB
   - Demonstrates how to audit changes using MongoDB as the storage backend.
2. Configuring advanced and optional Flamingock Builder Options
   - All configurations are optional and set in the example to their default values, providing flexibility for customization.
3. Implementing Pipeline Listeners
   - Illustrates the use of Pipeline Listeners for additional functionality and customization.

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
