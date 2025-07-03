import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.20"
    id("com.google.gms.google-services")
}

kotlin {

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    applyDefaultHierarchyTemplate()
    val iosTargets = listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    )
    iosTargets.forEach { target ->
        target.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
        target.compilations.getByName("main") {
            cinterops {
                val firebase by creating {
                    definitionFile = file("src/iosMain/c_interop/firebase_remote_config.def")
                    packageName = "br.com.kmp.remoteconfig"
                }
            }
        }
    }

    sourceSets {
        val ktorVersion = "3.1.3"
        val coroutinesVersion = "1.8.0"
        val koinVersion = "4.1.0"
        val slf4jVersion = "2.0.13"

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("io.ktor:ktor-client-okhttp:${ktorVersion}")
            implementation("io.insert-koin:koin-android:3.5.3")
            implementation("com.google.firebase:firebase-config:22.1.1")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation("io.insert-koin:koin-core:$koinVersion")
            implementation("io.insert-koin:koin-compose:$koinVersion")
            implementation("io.insert-koin:koin-compose-viewmodel:$koinVersion")

            implementation("io.ktor:ktor-client-core:$ktorVersion")
            implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
            implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
            implementation("io.ktor:ktor-client-logging:$ktorVersion")
            implementation("io.ktor:ktor-client-cio:${ktorVersion}")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            implementation("org.slf4j:slf4j-simple:$slf4jVersion")
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.0-beta03")
            implementation(kotlin("stdlib"))
            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.0-beta03")
        }
        iosMain.dependencies {
            implementation("io.ktor:ktor-client-darwin:${ktorVersion}")
        }
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

    }

}

compose.resources {
    nameOfResClass = "Res"
    publicResClass = false
    packageOfResClass = "br.com.kmp.demo.resources"
    generateResClass = auto
}

android {
    namespace = "br.com.kmp.demo"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "br.com.kmp.demo"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.firebase.config.ktx)
    debugImplementation(compose.uiTooling)
}
