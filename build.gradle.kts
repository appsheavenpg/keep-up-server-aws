plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.sonarqube)
}

repositories {
    mavenCentral()
}

sonar {
    properties {
        property("sonar.projectKey", "appsheavenpg_keep-up-server-aws")
        property("sonar.organization", "apps-heaven")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

kotlin {
    jvmToolchain(21)
}
