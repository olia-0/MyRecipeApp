plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false


    //id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}
hilt {
    enableAggregatingTask = false
}
android {

    namespace = "com.example.myrecipeapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myrecipeapp"
        minSdk = 25
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
//    viewBinding {
//        isEnabled = true
//    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.android)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.livedata)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.hilt.android)

    //implementation(libs.javapoet)
    implementation("com.squareup:javapoet:1.13.0")
    //Coil

    implementation(libs.coil.compose)

    // Retrofit

//    implementation (libs.retrofit)
//
//    implementation (libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Room

    //implementation(libs.androidx.room.ktx)
    //implementation(libs.androidx.room.runtime)
    //kapt(libs.androidx.room.compiler)
    //add("ksp", "androidx.room:room-compiler:2.5.2")
    //ksp(libs.androidx.room.compiler.v250)

    // Hilt
    //implementation(libs.hilt.android)
//    implementation(libs.hilt.android)
//    kapt(libs.hilt.compiler)
    implementation(libs.hilt.android.v242)
    kapt(libs.hilt.compiler.v242)

    // Hilt для ViewModel
//    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
//    kapt("androidx.hilt:hilt-compiler:1.0.0")

    // Для інтеграції з Jetpack Compose (якщо використовуєш Compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // Для Moshi
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)

// Для конвертера Moshi в Retrofit
    implementation(libs.converter.moshi)

    ///
    implementation(libs.converter.gson)

    /////YouTube
    implementation(libs.core)
    implementation (libs.androidx.lifecycle.runtime.ktx.v251)
}