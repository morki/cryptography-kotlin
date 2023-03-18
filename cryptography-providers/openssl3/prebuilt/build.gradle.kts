/*
 * Copyright (c) 2023 Oleg Yukhnevich. Use of this source code is governed by the Apache 2.0 license.
 */

import org.jetbrains.kotlin.gradle.plugin.mpp.*
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    id("buildx-multiplatform-library")
    id("buildx-target-native-all")
    id("buildx-use-openssl")
}

description = "cryptography-kotlin OpenSSL3 provider (prebuilt)"

tasks.withType<CInteropProcess>().configureEach {
    dependsOn(openssl.prepareOpensslTaskProvider)
    settings.extraOpts("-libraryPath", openssl.libDir(konanTarget).get().absolutePath)
}

kotlin {
    targets.all {
        if (this !is KotlinNativeTarget) return@all

        cinterop("linking", "common")
    }

    sourceSets {
        commonMain {
            dependencies {
                api(projects.cryptographyOpenssl3Api)
            }
        }
        commonTest {
            dependencies {
                api(projects.cryptographyOpenssl3Test)
            }
        }
    }
}
