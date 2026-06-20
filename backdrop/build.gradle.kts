plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.multiplatform")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11)
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("androidx.compose.ui:ui:1.8.2")
                implementation("androidx.compose.foundation:foundation:1.8.2")
                implementation("androidx.compose.material3:material3:1.3.2")
                implementation("androidx.compose.ui:ui-tooling-preview:1.8.2")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("androidx.compose.ui:ui-tooling:1.8.2")
            }
        }
    }

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

android {
    namespace = "com.kashif_e.backdrop"
    compileSdk = 36

    defaultConfig {
        minSdk = 25
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
