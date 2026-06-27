import org.gradle.plugin.compatibility.compatibility

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "2.1.1"
    kotlin("plugin.serialization") version embeddedKotlinVersion
}

group = "io.github.DancingSnow0517"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
}

gradlePlugin {
    website.set("https://github.com/DancingSnow0517/gtnh-catalog")
    vcsUrl.set("https://github.com/DancingSnow0517/gtnh-catalog.git")

    plugins {
        create("gtnhCatalogSettings") {
            id = "io.github.DancingSnow0517.gtnh-catalog.settings"
            implementationClass = "io.github.dancingsnow0517.gtnhcatalog.GTNHCatalogSettingsPlugin"
            displayName = "GTNH Catalog Settings Plugin"
            description = "Generates Gradle version catalogs from GT New Horizons manifests."
            tags.set(listOf("gtnh", "minecraft", "version-catalog", "settings-plugin"))
            compatibility {
                features {
                    configurationCache = false
                }
            }
        }
    }
}
