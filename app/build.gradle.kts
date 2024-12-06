import java.io.FileInputStream
import java.util.Properties

val apikeyPropertiesFile = rootProject.file("gradle/apikey.properties")
val apikeyProperties = Properties()
apikeyProperties.load(FileInputStream(apikeyPropertiesFile))

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp") version "2.0.20-1.0.25"

    // Hilt
    id("com.google.dagger.hilt.android") version "2.52"

    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20"

    kotlin("plugin.serialization") version "2.0.20"
}

android {
    namespace = "com.example.leafy"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.leafy"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        // Add API key
        buildConfigField("String", "API_KEY", apikeyProperties["API_KEY"].toString())
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildToolsVersion = "34.0.0"
}

// Allowing code autogeneration
kapt {
    correctErrorTypes = true
}

dependencies {
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0")
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2024.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.test.espresso:espresso-core:3.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("io.coil-kt:coil-compose:2.3.0")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.0")
    implementation("androidx.compose.material:material-icons-extended:1.7.5")

    // Navigation
    val nav_version = "2.8.3"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:$room_version") // KSP for Room
    implementation("androidx.room:room-ktx:$room_version") // KTX for coroutines
    implementation("androidx.room:room-paging:$room_version")

    // Hilt
    val hilt_version = "2.52"
    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Paging
    val paging_version = "3.3.2"
    implementation("androidx.paging:paging-runtime-ktx:$paging_version")
    implementation("androidx.paging:paging-compose:$paging_version")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.11.0")

    //Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")

    //Serialization
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.0.20")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation ("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")


    implementation("io.coil-kt:coil-compose:2.0.0-rc01")

    // CameraX
    val camerax_version = "1.4.0"
    implementation("androidx.camera:camera-camera2:${camerax_version}")
    implementation("androidx.camera:camera-core:${camerax_version}")
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    implementation("androidx.camera:camera-view:${camerax_version}")
    implementation("androidx.camera:camera-extensions:${camerax_version}")
    }



hilt {
    enableAggregatingTask = false
}
