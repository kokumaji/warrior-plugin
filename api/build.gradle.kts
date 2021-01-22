import kr.entree.spigradle.kotlin.paper

plugins {
    java
    kotlin("jvm")
    id("kr.entree.spigradle")
}

version = "1.0.0"

dependencies {
    implementation("org.projectlombok:lombok:1.18.16")
    compileOnly(paper())
    implementation("org.jetbrains:annotations:16.0.2")
    implementation("com.dumbdogdiner:stickyapi:2.0.0")
}

spotless {
    java {
        importOrder()
        prettier(
            mapOf(
                "prettier" to "2.0.5",
                "prettier-plugin-java" to "0.8.0"
            )
        ).config(
            mapOf(
                "parser" to "java",
                "tabWidth" to 4
            )
        )
        licenseHeaderFile(rootProject.file("LICENSE_HEADER"))
    }
}

tasks {
    generateSpigotDescription {
        enabled = false
    }
}
