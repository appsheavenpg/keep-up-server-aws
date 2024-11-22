import java.util.Properties

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.sonarqube)
}

repositories {
    mavenCentral()
}

var env = Properties().apply {
    file(".env").takeIf { it.exists() }?.inputStream()?.use { load(it) }
}

val projectKey = env.getProperty("SONAR_PROJECT_KEY")
val organization = env.getProperty("SONAR_ORGANIZATION")
val hostUrl = env.getProperty("SONAR_HOST_URL")

sonar {
    properties {
        property("sonar.projectKey", projectKey)
        property("sonar.organization", organization)
        property("sonar.host.url", hostUrl)
    }
}

kotlin {
    jvmToolchain(21)
}
