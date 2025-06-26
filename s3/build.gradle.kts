plugins {
    java
    application
}

repositories {
    mavenLocal()
    mavenCentral() // Fallback for other dependencies
}

group = "io.flamingock"
version = "1.0-SNAPSHOT"

val flamingockVersion = "0.0.34-beta"
val awsSdkVersion = "2.25.28"

dependencies {
    // Import the flamingock BOM
    implementation(platform("io.flamingock:flamingock-ce-bom:$flamingockVersion"))

    // Dependencies managed by the BOM
    implementation("io.flamingock:flamingock-core")
    implementation("io.flamingock:flamingock-core-api")
    implementation("io.flamingock:flamingock-ce-dynamodb")
    implementation("io.flamingock:flamingock-ce-commons")

    // AWS SDK dependencies with explicit versions
    implementation(platform("software.amazon.awssdk:bom:$awsSdkVersion"))
    implementation("software.amazon.awssdk:s3")                        // Add S3 client
    implementation("software.amazon.awssdk:dynamodb")                  // Base DynamoDB client
    implementation("software.amazon.awssdk:dynamodb-enhanced")         // Enhanced DynamoDB client
    implementation("software.amazon.awssdk:url-connection-client")     // HTTP client implementation

    annotationProcessor("io.flamingock:flamingock-core:$flamingockVersion")

    // Other dependencies
    implementation("org.slf4j:slf4j-api:2.0.6")
    implementation("org.slf4j:slf4j-simple:2.0.6")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.9.20")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

application {
    mainClass = "io.flamingock.examples.s3.S3FlamingockExample"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
