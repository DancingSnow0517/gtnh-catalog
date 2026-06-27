plugins {
    `java-library`
}

tasks.register("verifyCatalog") {
    doLast {
        val dependency = configurations
            .getByName("implementation")
            .dependencies
            .single { it.name == "ae2-stuff" }

        check(dependency.group == "com.github.GTNewHorizons") {
            "Expected group com.github.GTNewHorizons, got ${dependency.group}"
        }
        check(dependency.version == "1.0.0") {
            "Expected version 1.0.0, got ${dependency.version}"
        }

        val adventureBackpackDependency = configurations
            .getByName("implementation")
            .dependencies
            .single { it.name == "AdventureBackpack2" }

        check(adventureBackpackDependency.version == "1.1.0") {
            "Expected version 1.1.0, got ${adventureBackpackDependency.version}"
        }

        val biblioCraftDependency = configurations
            .getByName("implementation")
            .dependencies
            .single { it.name == "biblioCraft: BiblioWoods Biomes O'Plenty Edition" }

        check(biblioCraftDependency.version == "1.2.0") {
            "Expected version 1.2.0, got ${biblioCraftDependency.version}"
        }

        check(gtnh.versions.externalOnlyMod.get() == "2.0.0") {
            "Expected externalOnlyMod version 2.0.0"
        }
    }
}

dependencies {
    implementation(gtnh.ae2Stuff)
    implementation(gtnh.adventureBackpack2)
    implementation(gtnh.biblioCraftBiblioWoodsBiomesOPlentyEdition)
}
