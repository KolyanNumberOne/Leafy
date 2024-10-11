
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id ("kotlin-kapt")

    id("com.google.devtools.ksp") version "2.0.20-1.0.25"

    //Hilt
    id("com.google.dagger.hilt.android") version "2.52"

    //id("org.jetbrains.kotlin.android") version "2.1.0-Beta1"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20"


}


android {
    namespace = "com.example.leafy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.leafy"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

//Allowing code autogeneration
kapt {
    correctErrorTypes = true
}

dependencies {
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation(platform("androidx.compose:compose-bom:2024.09.03"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.test.espresso:espresso-core:3.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.09.03"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //Navigation
    val nav_version = "2.8.2"

    implementation("androidx.navigation:navigation-compose:$nav_version")

    //Room
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    ksp("androidx.room:room-compiler:$room_version") // KAPT
    implementation ("androidx.room:room-ktx:$room_version") // KTX for coroutines
    implementation ("androidx.room:room-paging:$room_version")
    //Hilt
    val hilt_version = "2.52"

    implementation("com.google.dagger:hilt-android:$hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")

    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")


    //Paging
    val paging_version = "3.3.2"
    implementation ("androidx.paging:paging-runtime-ktx:$paging_version")
    implementation ("androidx.paging:paging-compose:$paging_version")
}

hilt {
    enableAggregatingTask = false
}