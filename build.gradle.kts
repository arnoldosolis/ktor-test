
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
    id("org.liquibase.gradle") version "2.2.0"
}

group = "com.test"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.postgresql)
    implementation(libs.h2)
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    implementation("com.mchange:c3p0:0.9.5.5")
    implementation("org.liquibase:liquibase-core:4.27.0")
    liquibaseRuntime(libs.postgresql)
    liquibaseRuntime("org.liquibase:liquibase-core:4.27.0")
    liquibaseRuntime("info.picocli:picocli:4.7.5") // Required to avoid NoClassDefFoundError
    implementation("org.quartz-scheduler:quartz:2.5.0")
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}

liquibase {
    activities.register("local") {
        this.arguments = mapOf(
            "logLevel" to "debug",
            "changelogFile" to "src/main/resources/db/migration/migrations.xml",
            "url" to "jdbc:postgresql://localhost:5432/mydb",
            "username" to "myuser",
            "password" to "mypassword",
        )
    }
}