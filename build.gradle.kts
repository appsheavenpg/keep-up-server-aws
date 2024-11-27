plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.sonarqube)
    alias(libs.plugins.detekt)
}

repositories {
    mavenCentral()
}

val projectKey: String = "appsheavenpg_keep-up-server-aws"
val organization: String = "apps-heaven"
val hostUrl: String = "https://sonarcloud.io"

val sources = listOf(
    "HelloWorldFunction/src/main/java",
    "HelloWorldFunction/src/test/java"
)

sonar {
    properties {
        property("sonar.projectKey", projectKey)
        property("sonar.organization", organization)
        property("sonar.host.url", hostUrl)
        property("sonar.sources", sources)
    }
}

detekt {
    config.setFrom(file("./.detekt/detekt.yml"))
    source.setFrom(source.map { file(it) })
    buildUponDefaultConfig = true
}

kotlin {
    jvmToolchain(21)
}
