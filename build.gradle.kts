plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.sonarqube)
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}
