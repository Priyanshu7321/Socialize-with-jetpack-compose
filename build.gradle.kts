// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra["compose_ui_version"] = "1.4.0-beta02"
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("com.google.gms.google-services") version "4.4.3" apply false
}