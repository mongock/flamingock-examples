val flamingockVersion = project.findProperty("flamingockVersion") as String


dependencies {
    implementation("io.flamingock:flamingock-core:$flamingockVersion")
    implementation("io.flamingock:couchbase-driver:$flamingockVersion")

    implementation("com.couchbase.client:java-client:3.4.4")
    implementation("org.slf4j:slf4j-simple:2.0.6")

    
    testImplementation("org.testcontainers:couchbase:1.18.3")
    testImplementation("org.testcontainers:junit-jupiter:1.18.3")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "io.flamingock.examples.community.couchbase.CommunityStandaloneCouchbaseApp"
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