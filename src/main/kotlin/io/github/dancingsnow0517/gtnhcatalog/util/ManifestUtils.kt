package io.github.dancingsnow0517.gtnhcatalog.util

import java.net.URI

object ManifestUtils {

    fun manifestUrl(version: String): String {
        return "https://raw.githubusercontent.com/GTNewHorizons/DreamAssemblerXXL/refs/heads/master/releases/manifests/$version.json"
    }

    fun downloadManifest(version: String): String {
        return URI(manifestUrl(version)).toURL().readText()
    }
}
