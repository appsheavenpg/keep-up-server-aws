import io.gitlab.arturbosch.detekt.Detekt
import java.util.Properties

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

allprojects {
    apply(plugin = libs.plugins.detekt.get().pluginId)
    
    detekt {
        toolVersion = "1.23.7"
        config.setFrom(file("config/detekt/detekt.yml"))
        buildUponDefaultConfig = true
    }
    
    tasks.withType<Detekt>().configureEach {
        reports {
            sarif.required.set(true)
            // Store all reports in root project's build directory
            sarif.outputLocation.set(rootProject.layout.buildDirectory.file("reports/detekt/${project.name}-detekt.sarif"))
        }
    }
}

kotlin {
    jvmToolchain(21)
}
