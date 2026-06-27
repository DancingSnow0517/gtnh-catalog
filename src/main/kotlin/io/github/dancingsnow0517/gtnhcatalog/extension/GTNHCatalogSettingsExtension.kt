package io.github.dancingsnow0517.gtnhcatalog.extension

import org.gradle.api.Action
import org.gradle.api.model.ObjectFactory
import javax.inject.Inject

abstract class GTNHCatalogSettingsExtension {
    @get:Inject
    internal abstract val objectFactory: ObjectFactory

    val versions = objectFactory.domainObjectContainer(GTNHVersion::class.java)

    fun create(name: String): GTNHVersion {
        return versions.create(name)
    }

    fun create(name: String, configureAction: Action<in GTNHVersion>): GTNHVersion {
        return versions.create(name, configureAction)
    }
}
