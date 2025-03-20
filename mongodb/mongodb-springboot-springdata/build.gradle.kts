import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory

plugins {
    java
    application
//    Springboot plugins
    id("org.springframework.boot") version "2.7.12"
    id("io.spring.dependency-management") version "1.1.0"
}

repositories {
    mavenCentral()
    mavenLocal()
}

group = "io.flamingock"
version = "1.0-SNAPSHOT"

val flamingockVersion = flamingockVersion()

dependencies {
//    Flamingock Dependencies
    implementation("io.flamingock:flamingock-springboot-v2-runner:$flamingockVersion")
    implementation("io.flamingock:mongodb-springdata-v3-driver:$flamingockVersion")

//    Springboot dependency
    implementation("org.springframework.boot:spring-boot-starter-web")

//    Springdata for MongoDB dependency
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

//    Others dependencies needed for this example
    implementation("org.slf4j:slf4j-simple:2.0.6")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    testImplementation("org.testcontainers:mongodb:1.18.3")
    testImplementation("org.testcontainers:junit-jupiter:1.18.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Mongock dependency is only included to provide example data for demonstrating how the import works.
    // It is not required for production use.
    implementation("io.mongock:mongock-standalone:5.5.0")
    implementation("io.mongock:mongodb-sync-v4-driver:5.5.0")
}

application {
    mainClass = "io.flamingock.examples.mongodb.springboot.springdata.MongodbSpringbootSpringdata"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    systemProperty("org.slf4j.simpleLogger.logFile", "System.out")
    testLogging {
        events(
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT,
        )
    }
}

// Get Flamingock version from parameter or last
fun flamingockVersion(): String {
    var passedAsParameter = false
    val flamingockVersionAsParameter: String? = project.findProperty("flamingockVersion")?.toString()
    val flamingockVersion: String =  if(flamingockVersionAsParameter != null) {
        passedAsParameter = true
        flamingockVersionAsParameter
    } else {
        //using "release.latest" doesn't play nice with intellij
        val metadataUrl = "https://repo.maven.apache.org/maven2/io/flamingock/flamingock-core/maven-metadata.xml"
        try {
            val metadata = URL(metadataUrl).readText()
            val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val inputStream = metadata.byteInputStream()
            val document = documentBuilder.parse(inputStream)
            document.getElementsByTagName("latest").item(0).textContent
        } catch (e: Exception) {
            throw RuntimeException("Cannot obtain Flamingock's latest version")
        }
    }
    logger.lifecycle("Building with flamingock version${if(passedAsParameter)"[from parameter]" else ""}: $flamingockVersion")
    return flamingockVersion
}
