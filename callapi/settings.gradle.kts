pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        id("com.android.library") version "8.4.0"
        kotlin("multiplatform") version "2.1.20"
        kotlin("plugin.serialization") version "2.1.20"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "callapishared"