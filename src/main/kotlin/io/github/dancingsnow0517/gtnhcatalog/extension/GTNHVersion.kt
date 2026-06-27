package io.github.dancingsnow0517.gtnhcatalog.extension

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

abstract class GTNHVersion @Inject constructor(val name: String, objects: ObjectFactory) {
    /**
     * The GTNH manifest version.
     *
     * Get the full list of manifests: [DreamAssemblerXXL](https://github.com/GTNewHorizons/DreamAssemblerXXL/blob/master/releases/manifests).
     */
    val version: Property<String> = objects.property(String::class.java)

    /**
     * Is use local cached manifest
     */
    val useCache: Property<Boolean> = objects.property(Boolean::class.java).convention(true)
}
