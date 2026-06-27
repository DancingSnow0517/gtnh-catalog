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

        check(gtnh.versions.externalOnlyMod.get() == "2.0.0") {
            "Expected externalOnlyMod version 2.0.0"
        }
    }
}

dependencies {
    implementation(gtnh.ae2Stuff)
}
