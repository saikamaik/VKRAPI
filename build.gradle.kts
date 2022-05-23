val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kgraphql_version: String by project
val hikari_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
}

tasks.create("stage") {
    dependsOn("installDist")
}

group = "com.diploma"
version = "0.0.1"

application {
    mainClass.set("com.diploma.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {

    implementation("io.ktor:ktor-jackson:${ktor_version}")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("io.ktor:ktor-server-core:1.6.8")
    implementation("io.ktor:ktor-server-netty:1.6.8")
    implementation("ch.qos.logback:logback-classic:1.2.5")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation ("io.ktor:ktor-auth-jwt:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("io.ktor:ktor-gson:$ktor_version")

    implementation("org.jetbrains.exposed", "exposed-core", "0.37.3")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.37.3")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.37.3")
    implementation("org.jetbrains.exposed", "exposed-jodatime", "0.37.3")

    implementation("org.postgresql:postgresql:42.3.3")
    implementation("com.graphql-java:graphql-java:230521-nf-execution")
    implementation("ch.qos.logback:logback-classic:1.2.11")
    implementation("log4j", "log4j", "1.2.17")

    implementation ("com.graphql-java:graphql-java-extended-scalars:17.0")

    implementation("com.graphql-java:graphql-java:16.2")
    implementation( "commons-codec:commons-codec:1.15")

    implementation("com.h2database:h2:2.1.210")
    implementation ("org.postgresql:postgresql:42.2.18")

    implementation("com.apurebase:kgraphql:$kgraphql_version")      // <-- Add these two lines
    implementation("com.apurebase:kgraphql-ktor:$kgraphql_version")

    implementation("com.zaxxer:HikariCP:$hikari_version")
}