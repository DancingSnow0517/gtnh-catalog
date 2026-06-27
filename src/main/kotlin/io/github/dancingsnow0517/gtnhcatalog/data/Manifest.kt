package io.github.dancingsnow0517.gtnhcatalog.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Manifest(
    val version: String,
    @SerialName("last_version")
    val lastVersion: String,
    @SerialName("last_update")
    val lastUpdate: String,
    val config: String,
    @SerialName("github_mods")
    val githubMods: Map<String, Mod>,
    @SerialName("external_mods")
    val externalMods: Map<String, Mod>
)
