pluginManagement {
    includeBuild("..")
    repositories {
        gradlePluginPortal()
    }
}

plugins {
    id("io.github.DancingSnow0517.gtnh-catalog.settings")
}

rootProject.name = "gtnh-catalog-plugin-test"

`gtnh-catalog` {
    create("gtnh") {
        version = "test"
        useCache = true
    }
}
