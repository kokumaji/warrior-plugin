import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation

plugins {
    kotlin("jvm") version "1.4.32"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("kr.entree.spigradle") version "2.2.3"
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
    build {
        dependsOn("shadowJar")
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }

    spigot {
        name = "Warrior"
        description = "A fully customizable, class-based competitive FFA plugin."
        authors = mutableListOf("kokumaji")
        apiVersion = "1.13"

        softDepends = mutableListOf("Multiverse", "PlaceholderAPI")

        commands {
            create("debug") {
                usage = "/debug"
            }
        }
    }

    register<ConfigureShadowRelocation>("relocateShadowJar") {
        target = project.tasks.shadowJar.get()
        prefix = "${group}.warrior.libs" // Default value is "shadow"
    }

    shadowJar {
        dependsOn("relocateShadowJar")
        archiveClassifier.set("")
        project.configurations.implementation.configure { isCanBeResolved = true }
        configurations = listOf(
            project.configurations.shadow.get()
        )

        val pkg = "$group.warrior.libs."

        exclude("generated/mojangles_width_data.json")
        relocate("com.zaxxer", "${pkg}com.zaxxer")
        relocate("org.postgresql", "${pkg}org.postgresql")
        relocate("org.postgresql", "${pkg}org.postgresql")

        minimize()
    }


}
