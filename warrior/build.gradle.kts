plugins {
    kotlin("jvm")
    id("kr.entree.spigradle")
}

repositories {
    // PlaceholderAPI repository
    maven(url = "https://repo.extendedclip.com/content/repositories/placeholderapi/")
}

dependencies {
    implementation(project(":warrior-api"))
    compileOnly("me.clip:placeholderapi:2.10.9")
    compileOnly("io.netty:netty-all:4.1.24.Final")
}
