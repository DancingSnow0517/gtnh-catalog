# GTNH Catalog Settings Plugin

Gradle settings plugin that generates version catalogs from GT New Horizons manifest files.

The plugin downloads or reuses cached manifests from
`GTNewHorizons/DreamAssemblerXXL` and turns manifest entries into Gradle version
catalog aliases.

## Usage

```kotlin
// settings.gradle.kts
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("io.github.DancingSnow0517.gtnh-catalog.settings") version "1.0.1"
}

`gtnh-catalog` {
    create("gtnh") {
        version = "2.8.4"
        useCache = true
    }
}
```

While the Gradle Plugin Portal release is waiting for approval, the plugin can
also be resolved from JitPack:

```kotlin
// settings.gradle.kts
pluginManagement {
    repositories {
        maven("https://jitpack.io")
        gradlePluginPortal()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "io.github.DancingSnow0517.gtnh-catalog.settings") {
                useModule(
                    "com.github.DancingSnow0517.gtnh-catalog:" +
                        "io.github.DancingSnow0517.gtnh-catalog.settings.gradle.plugin:" +
                        requested.version,
                )
            }
        }
    }
}

plugins {
    id("io.github.DancingSnow0517.gtnh-catalog.settings") version "1.0.1"
}
```

```kotlin
// build.gradle.kts
dependencies {
    implementation(gtnh.someModAlias)
}
```

## Catalog Generation

For each configured entry in `gtnh-catalog.versions`, the plugin creates a
version catalog with the same name.

Manifest map keys are used as Maven artifact IDs. Catalog aliases are converted
from kebab, dot, or snake case to camel case:

```text
ae2-stuff -> ae2Stuff
external-only-mod -> externalOnlyMod
```

Both `github_mods` and `external_mods` generate version aliases.

Only `github_mods` generate library aliases. Libraries use:

```text
group = com.github.GTNewHorizons
artifact = manifest map key
version = manifest mod version
```

## Cache

Manifests are cached under:

```text
~/.gradle/caches/gtnh-catalog/manifests/<version>.json
```

`useCache` defaults to `true`.

When `useCache` is `true` and a matching cached file exists, the plugin reads
the local file. Otherwise, it downloads the manifest and writes it to the cache.

`daily`, `nightly`, and `experimental` are floating manifest versions. They
always bypass the cache and are not written back to the cache.

## Development

Compile the plugin:

```powershell
.\gradlew compileKotlin
```

Run the included sample build:

```powershell
.\gradlew -p test-plugin verifyCatalog
```

Validate Plugin Portal publishing configuration:

```powershell
.\gradlew publishPlugins --validate-only
```

## Publishing

Configure Plugin Portal credentials in `~/.gradle/gradle.properties`:

```properties
gradle.publish.key=...
gradle.publish.secret=...
```

Then publish:

```powershell
.\gradlew publishPlugins
```
