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

val flamingockVersion = flamingockVersion("0.0.34-beta")

val mongodbVersion = "4.3.3"

dependencies {
//    Flamingock Dependencies
    implementation("io.flamingock:flamingock-springboot-v2-runner:$flamingockVersion")
    implementation("io.flamingock:mongodb-sync-v4-driver:$flamingockVersion")
    annotationProcessor("io.flamingock:flamingock-processor:$flamingockVersion")

//    Springboot dependency
    implementation("org.springframework.boot:spring-boot-starter-web")

//    MongoDB dependencies
    implementation("org.mongodb:mongodb-driver-sync:$mongodbVersion")
    implementation("org.mongodb:mongodb-driver-core:$mongodbVersion")
    implementation("org.mongodb:bson:$mongodbVersion")

//    Others dependencies needed for this example
    implementation("org.slf4j:slf4j-simple:2.0.6")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    testImplementation("org.testcontainers:mongodb:1.18.3")
    testImplementation("org.testcontainers:junit-jupiter:1.18.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.flamingock:mongodb-facade:$flamingockVersion")
}

application {
    mainClass = "io.flamingock.examples.mongodb.springboot.sync.CommunitySpringbootMongodbSyncApp"
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

/**
 * Retrieves the Flamingock library version to be used during the build process.
 *
 * Version resolution follows this priority:
 * 1. If a version is passed as a Gradle script property (`-PflamingockVersion=...`), it takes precedence.
 * 2. If a version is passed directly to the method via [inlinedVersion], it is used as a fallback.
 * 3. If neither of the above is provided, it fetches the latest version available from Maven Central.
 *
 * Example usage:
 * ```
 * dependencies {
 *     implementation("io.flamingock:flamingock-core:${flamingockVersion()}")
 * }
 * ```
 *
 * @param inlinedVersion optional version string passed directly to the method.
 * @return the resolved Flamingock version to use.
 * @throws RuntimeException if the latest version cannot be retrieved from Maven metadata.
 */
fun flamingockVersion(inlinedVersion: String? = null): String {
    val versionAsScriptParameter: String? = project.findProperty("flamingockVersion")?.toString()

    return if(versionAsScriptParameter != null) {
        logger.lifecycle("Building with flamingock version[from parameter]: $versionAsScriptParameter")
        versionAsScriptParameter
    } else if(inlinedVersion != null) {
        logger.lifecycle("Building with flamingock version[inlined]: $inlinedVersion")
        inlinedVersion
    } else {
        //using "release.latest" doesn't play nice with intellij
        val metadataUrl = "https://repo.maven.apache.org/maven2/io/flamingock/flamingock-core/maven-metadata.xml"
        try {
            val metadata = URL(metadataUrl).readText()
            val documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            val inputStream = metadata.byteInputStream()
            val document = documentBuilder.parse(inputStream)
            val latestVersion = document.getElementsByTagName("latest").item(0).textContent
            logger.lifecycle("Building with flamingock version[latest]: $latestVersion")
            latestVersion
        } catch (e: Exception) {
            throw RuntimeException("Cannot obtain Flamingock's latest version")
        }
    }
}
