plugins {
    kotlin("jvm")
    id("kr.entree.spigradle")
}

val kotlinVersion = "1.4.32"
val gradleVersion = "6.8+"

repositories {
    // PlaceholderAPI repository
    maven(url = "https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    implementation(project(":warrior-api"))
    compileOnly("me.clip:placeholderapi:2.10.9")
    compileOnly("io.netty:netty-all:4.1.24.Final")
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}