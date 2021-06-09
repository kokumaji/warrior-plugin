plugins {
    kotlin("jvm") version "1.4.32"
}

val kotlinVersion = "1.4.32"
val gradleVersion = "6.8+"

dependencies {
    implementation(project(":warrior-api"))
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    compileJava {
        options.encoding = "UTF-8"
    }
}
