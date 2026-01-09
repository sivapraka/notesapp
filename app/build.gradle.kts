plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Existing plugins
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt)
    kotlin("kapt") // Required for annotation processing
}

android {
    namespace = "com.notesapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.notesapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "TMDB_API_KEY", "\"${property("TMDB_API_KEY")}\"")
        buildConfigField("String", "BASE_URL", "\"${property("BASE_URL")}\"")
             testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            // Enables resource shrinking.
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig=true
    }

}

kotlin {
    compilerOptions {
        jvmTarget.set(
            org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
        )
    }
}

dependencies {

    implementation(libs.kotlin.stdlib)
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.appcompat)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.annotation)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    // Kotlin coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    // Core
    implementation(libs.activity.compose)
    implementation(libs.material) // or latest
    implementation(libs.hilt.navigation.compose)
    kapt(libs.room.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    // Lottie & Image Loading
    implementation(libs.lottie.compose)
    implementation(libs.coil.compose)

    //Security
    implementation(libs.security.crypto)
    implementation(libs.biometric)
    //Gson
    implementation (libs.gson)
    //icons
    implementation(libs.material.icons.extended)

    implementation(libs.paging.compose)
    implementation(libs.room.paging)
    implementation(libs.datastore.preferences)
    // Google Play Services Location
    implementation (libs.play.services.location)
    // Coroutines support for Play‑Services Tasks
    implementation (libs.kotlinx.coroutines.play.services)
    implementation(libs.timber)
    // Play Store -App-Update
    implementation (libs.app.update)
    // Work Manager
    implementation(libs.work.runtime.ktx)
    implementation(libs.hilt.work)
}