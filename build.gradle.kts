// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // Add the dependency for the Google services Gradle plugin
    id("com.google.gms.google-services") version "4.4.2" apply false

}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Firebase plugin for Google services
        classpath("com.google.gms:google-services:4.4.2")
        classpath("com.google.gms:google-services:<latest-version>")

    }
}