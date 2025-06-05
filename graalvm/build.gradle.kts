plugins {
    id("java")
}

group = "io.flamingock"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

val mongodbVersion = "4.3.3"
val jacksonVersion = "2.15.2"
val flamingockVersion = "0.0.30-beta"
dependencies {
    implementation("io.flamingock:mongodb-sync-v4-driver:$flamingockVersion")
    implementation("io.flamingock:flamingock-core:$flamingockVersion")
    implementation("io.flamingock:flamingock-graalvm:$flamingockVersion")

    annotationProcessor("io.flamingock:flamingock-core:$flamingockVersion")


    implementation("org.mongodb:mongodb-driver-sync:$mongodbVersion")

    implementation("org.slf4j", "slf4j-api", "2.0.6")
    implementation("org.slf4j:slf4j-simple:2.0.6")

}

tasks.test {
    useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "io.flamingock.App"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(sourceSets.main.get().output)

    from({
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    })
}

// Add this task to generate a native image
tasks.register<Exec>("nativeImage") {
    dependsOn("jar")
    
    val outputDir = file("$buildDir/native-image")
    val outputFile = file("$outputDir/app")
    
    doFirst {
        outputDir.mkdirs()
    }
    
    // Set system property for slf4j configuration at build time
    environment("JAVA_TOOL_OPTIONS", "-Dorg.slf4j.simpleLogger.logFile=System.out")
    
    commandLine(
        "native-image",
        "--no-fallback",
        "--features=io.flamingock.graalvm.RegistrationFeature",
        "-H:ResourceConfigurationFiles=resource-config.json",
        "-H:ReflectionConfigurationFiles=reflect-config.json",
        "-H:+ReportExceptionStackTraces",
        "--initialize-at-build-time=org.slf4j,org.slf4j.impl,org.slf4j.simple",
        "-Dorg.slf4j.simpleLogger.defaultLogLevel=info",
        "-Dorg.slf4j.simpleLogger.showDateTime=true",
        "-Dorg.slf4j.simpleLogger.dateTimeFormat=yyyy-MM-dd HH:mm:ss:SSS Z",
        "-Dorg.slf4j.simpleLogger.showThreadName=true",
        "-Dorg.slf4j.simpleLogger.showLogName=true",
        "-Dorg.slf4j.simpleLogger.showShortLogName=false",
        "-Dorg.slf4j.simpleLogger.levelInBrackets=false",
        "-Dorg.slf4j.simpleLogger.logFile=System.out",
        "-jar", "${tasks.jar.get().archiveFile.get().asFile.absolutePath}",
        "-H:Name=$outputFile"


    )
}