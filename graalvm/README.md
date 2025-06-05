# Flamingock with GraalVM Example

This project demonstrates how to integrate [Flamingock](https://github.com/mongock/flamingock-project) and 
GraalVM native image, offering significantly faster startup times and reduced memory footprint compared to traditional JVM applications. It includes examples for both Maven and Gradle.

## Why GraalVM with Flamingock?

- **Reduced Startup Time**: GraalVM native images compile your application ahead-of-time into a standalone executable, resulting in near-instant startup times. This is particularly beneficial for cloud-native applications and microservices.
- **Lower Memory Footprint**: Native images typically consume less memory compared to JVM-based applications, allowing for more efficient resource utilization.
- **Improved Performance**: In some cases, native images can provide performance improvements due to optimizations performed during the native image build process.

## Prerequisites

Before you start, ensure you have the following installed:

- **Java Development Kit (JDK)**: Ensure you have a compatible JDK installed. GraalVM provides its own JDK, which is recommended.
- **GraalVM**: Install GraalVM using SDKMAN or manually.
- **Native Image Tool**: Install the GraalVM `native-image` tool.
- **Maven and Gradle**: Both are supported, and you can choose your preferred build tool.
- **Docker**: If you plan to use a Docker-based native image build.

## Installation

### 1. Install GraalVM using SDKMAN (Recommended)

SDKMAN is a convenient tool for managing parallel versions of multiple Software Development Kits on most Unix-based systems.

```bash
# Install SDKMAN
curl -s "https://get.sdkman.io" | bash

# Open a new terminal or source SDKMAN
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Install GraalVM (e.g., version 21-java17)
sdk install java 21-graalce

# Set GraalVM as the default Java version
sdk use java 21-graalce

# Verify installation
java -version
```

### 2. Install Native Image Tool

After installing GraalVM, install the `native-image` tool using the GraalVM Updater.

```bash
# Install native-image tool
gu install native-image

# Verify installation
native-image --version
```

## Setup
Refer to the setup instructions for your chosen build tool:

- [Maven Setup](SETUP_MAVEN.md)
- [Gradle Setup](SETUP_GRADLE.md)

## Common Issues and Solutions

- **`java.lang.UnsupportedClassVersionError`**:
  - **Problem**: This error typically occurs when the class file is compiled with a newer JDK version than the one used at runtime.
  - **Solution**: Ensure that your `JAVA_HOME` environment variable points to the correct GraalVM JDK and that your project is configured to use the same JDK version.

- **Missing Dependencies**:
  - **Problem**: The native image build may fail due to missing dependencies or reflection configurations.
  - **Solution**: Review the build output for missing dependencies and add them to your project. For reflection issues, create a `reflect.config.json` file with the necessary reflection configurations.

- **Build Errors During Native Image Generation**:
  - **Problem**: Native image generation may fail due to various reasons, such as unsupported features or incorrect configurations.
  - **Solution**: Consult the GraalVM documentation and error messages for guidance. Ensure that your application is compatible with native image compilation.