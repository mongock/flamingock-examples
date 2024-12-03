plugins {
    java
    id("org.springframework.boot") version "2.7.12"
    id("io.spring.dependency-management") version "1.1.0"
}


val flamingockVersion = project.findProperty("flamingockVersion") as String



val jacksonVersion = "2.16.0"
dependencies {
    implementation(project("io.flamingock:flamingock-core:$flamingockVersion")
    implementation(project("io.flamingock:mongodb-sync4-cloud-importer:$flamingockVersion")
    implementation(project("io.flamingock:sql-cloud-transactioner:$flamingockVersion")
    implementation(project("io.flamingock:sql-template:$flamingockVersion")

    implementation("com.mysql:mysql-connector-j:8.2.0")
    implementation("org.slf4j:slf4j-simple:2.0.6")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("commons-logging:commons-logging:1.2")

}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "io.flamingock.examples.mysql.standalone.MysqlStandaloneApplication"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(sourceSets.main.get().output)
    dependsOn(configurations.compileClasspath)
    from({
        configurations.compileClasspath.get().filter {
            it.name.endsWith("jar")
        }.map { zipTree(it) }
    })
}
