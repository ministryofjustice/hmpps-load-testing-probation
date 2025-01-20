plugins {
    kotlin("jvm")
    kotlin("plugin.allopen")

    id("io.gatling.gradle") version "3.9.5.6"
}

gatling {
    logLevel = "ERROR"
    logHttp = io.gatling.gradle.LogHttp.FAILURES
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(19))
}

repositories {
    mavenCentral()
}

dependencies {
    gatling("org.postgresql:postgresql:42.6.0")

    gatling(platform("org.http4k:http4k-bom:5.12.0.0"))
    gatling("org.http4k:http4k-core")
    gatling("org.http4k:http4k-server-undertow")
    gatling("org.http4k:http4k-client-apache")
}