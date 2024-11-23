plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "com.appsheaven.pl"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.aws.lambda.java)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:deprecation")
}

kotlin {
    jvmToolchain(21)
}
