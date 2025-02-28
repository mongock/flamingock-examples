plugins {
    java
    id("org.springframework.boot") version "2.7.12"
    id("io.spring.dependency-management") version "1.1.0"
}


val flamingockVersion = project.findProperty("flamingockVersion") as String

val mongodbVersion = "4.3.3"
dependencies {

    implementation("io.flamingock:flamingock-springboot-v2-runner:$flamingockVersion")
    implementation("io.flamingock:mongodb-sync-v4-driver:$flamingockVersion")

    implementation("org.slf4j:slf4j-simple:2.0.6")
    implementation("org.mongodb:mongodb-driver-sync:$mongodbVersion")
    implementation("org.mongodb:mongodb-driver-core:$mongodbVersion")
    implementation("org.mongodb:bson:$mongodbVersion")
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.testcontainers:mongodb:1.18.3")

    testImplementation("org.testcontainers:junit-jupiter:1.18.3")

    testImplementation("org.springframework.boot:spring-boot-starter-test")


}

springBoot {
    mainClass.set("io.flamingock.examples.mongodb.springboot.sync.CommunitySpringbootMongodbSyncApp")
}

