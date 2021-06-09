<<<<<<< Updated upstream
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
        prefix = "com.dumbdogdiner.warrior.libs" // Default value is "shadow"
    }

    shadowJar {
        dependsOn("relocateShadowJar")
        archiveClassifier.set("")
        // exclude generated
        exclude("generated/mojangles_width_data.json")

        val pkg = "com.dumbdogdiner.warrior.libs."
        relocate("org.intellij", "${pkg}org.intellij")
        // minimize()
    }
}
=======
import com.github.jengelman.gradle.plugins.shadow.tasks.ConfigureShadowRelocation

plugins {
    kotlin("jvm") version "1.4.32"
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
    implementation(project(":warrior-textadv"))
    compileOnly("me.clip:placeholderapi:2.10.9")
    compileOnly("io.netty:netty-all:4.1.24.Final")
    implementation(fileTree("../libs"))
}

tasks {

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

    build {
        dependsOn("shadowJar")
    }

    shadowJar {
        archiveClassifier.set("")
    }

}
>>>>>>> Stashed changes
