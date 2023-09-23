import nu.studer.gradle.jooq.JooqEdition
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Property
import org.jooq.meta.jaxb.ForcedType

plugins {
    kotlin("jvm") version "1.9.0"
    id("nu.studer.jooq") version "8.2"
    application
}

group = "ru.sokolov"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    jooqGenerator("org.postgresql:postgresql:42.2.27")
}

dependencies {
    // Javalin.io
    implementation("io.javalin:javalin:5.6.2")

    // Log
    implementation("org.slf4j:slf4j-simple:2.0.7")

    // Data
    implementation("org.jooq:jooq:3.18.6")
    implementation("org.jooq:jooq-meta:3.18.6")
    implementation("org.jooq:jooq-codegen:3.18.6")

    // Data Base connectors
    implementation("org.postgresql:postgresql:42.2.27")
    implementation("com.zaxxer:HikariCP:5.0.1")

    // JSON
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")

    // Test
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "ru.sokolov.AppKt"
    }

    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}

jooq {
    version.set("3.18.4")
    edition.set(JooqEdition.OSS)

    configurations {
        create("main") {
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/library"
                    user = "postgres"
                    password = "198725"
                    properties = listOf(
                        Property().apply {
                            key = "PAGE_SIZE"
                            value = "2048"
                        }
                    )
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        includes = "author"
                        forcedTypes = listOf(
                            ForcedType().apply {
                                name = "varchar"
                                includeExpression = ".*"
                                includeTypes = "JSONB?"
                            },
                            ForcedType().apply {
                                name = "varchar"
                                includeExpression = ".*"
                                includeTypes = "INET"
                            }
                        )
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = false
                        isImmutablePojos = false
                        isFluentSetters = false

                        isSequences = true
                    }
                    target.apply {
                        packageName = "ru.sokolov.jooq"

                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}