import java.util.Properties
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.detekt)
}

repositories {
    mavenCentral()
}

var env = Properties().apply {
    file(".env").takeIf { it.exists() }?.inputStream()?.use { load(it) }
}

val projectKey: String = env.getProperty("SONAR_PROJECT_KEY")
val organization: String = env.getProperty("SONAR_ORGANIZATION")
val hostUrl: String = env.getProperty("SONAR_HOST_URL")

val sources = listOf(
//    "HelloWorldFunction/src/main/java",
    "HelloWorldFunction/src/test/java"
)

sonar {
    properties {
        property("sonar.projectKey", projectKey)
        property("sonar.organization", organization)
        property("sonar.host.url", hostUrl)
//        property("sonar.sources", sources)
    }
}

detekt {
    config.setFrom(file("./.detekt/detekt.yml"))
    source.setFrom(source.map { file(it) })
    buildUponDefaultConfig = true
}

tasks.withType<Detekt>().configureEach {
    reports {
        sarif.required.set(true)
        sarif.outputLocation.set(file("build/reports/detekt/detekt.sarif"))
    }
}

kotlin {
    jvmToolchain(21)
}
