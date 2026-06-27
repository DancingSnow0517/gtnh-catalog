package io.github.dancingsnow0517.gtnhcatalog

import io.github.dancingsnow0517.gtnhcatalog.data.Manifest
import io.github.dancingsnow0517.gtnhcatalog.extension.GTNHCatalogSettingsExtension
import io.github.dancingsnow0517.gtnhcatalog.extension.GTNHVersion
import io.github.dancingsnow0517.gtnhcatalog.util.ManifestUtils
import kotlinx.serialization.json.Json
import org.gradle.api.Plugin
import org.gradle.api.initialization.Settings
import org.gradle.api.initialization.dsl.VersionCatalogBuilder
import java.io.File
import java.util.Locale

class GTNHCatalogSettingsPlugin : Plugin<Settings> {
    override fun apply(settings: Settings) {
        val extension = settings.extensions.create("gtnh-catalog", GTNHCatalogSettingsExtension::class.java)

        settings.gradle.settingsEvaluated {
            settings.dependencyResolutionManagement {
                versionCatalogs {
                    extension.versions.forEach { version ->
                        create(version.name) {
                            fromManifest(this@settingsEvaluated, version)
                        }
                    }
                }
            }
        }
    }

    private fun VersionCatalogBuilder.fromManifest(settings: Settings, version: GTNHVersion) {
        val manifest = loadManifest(settings, version)

        (manifest.githubMods + manifest.externalMods).forEach { (artifactId, mod) ->
            val alias = catalogAlias(artifactId)
            version(alias, mod.version)
        }

        manifest.githubMods.forEach { (artifactId, _) ->
            val alias = catalogAlias(artifactId)
            library(alias, "com.github.GTNewHorizons", artifactId).versionRef(alias)
        }
    }

    private fun loadManifest(settings: Settings, version: GTNHVersion): Manifest {
        val manifestVersion = version.version.get()
        val cacheFile = manifestCacheFile(settings, manifestVersion)
        val canUseCache = version.useCache.get() && manifestVersion !in NON_CACHEABLE_MANIFESTS

        val manifestJson = if (canUseCache && cacheFile.isFile) {
            cacheFile.readText()
        } else {
            ManifestUtils.downloadManifest(manifestVersion).also { downloaded ->
                if (canUseCache) {
                    cacheFile.parentFile.mkdirs()
                    cacheFile.writeText(downloaded)
                }
            }
        }

        return json.decodeFromString(manifestJson)
    }

    private fun manifestCacheFile(settings: Settings, version: String): File {
        return settings.gradle.gradleUserHomeDir
            .resolve("caches")
            .resolve("gtnh-catalog")
            .resolve("manifests")
            .resolve("$version.json")
    }

    private fun catalogAlias(artifactId: String): String {
        return artifactId
            .split(NON_ALIAS_CHARACTERS)
            .filter { it.isNotEmpty() }
            .mapIndexed { index, part ->
                if (index == 0) {
                    part.replaceFirstChar { char -> char.lowercase(Locale.ROOT) }
                } else {
                    part.replaceFirstChar { char ->
                        if (char.isLowerCase()) char.titlecase(Locale.ROOT) else char.toString()
                    }
                }
            }
            .joinToString("")
    }

    private companion object {
        val NON_CACHEABLE_MANIFESTS = setOf("daily", "nightly", "experimental")
        val NON_ALIAS_CHARACTERS = Regex("[^A-Za-z0-9]+")

        val json = Json {
            ignoreUnknownKeys = true
        }
    }
}
