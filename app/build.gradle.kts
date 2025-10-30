plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.app.tokosidia"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.app.tokosidia"
        minSdk = 24
        targetSdk = 36
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Android UI dasar
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    //  Navigation Component
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    //  Retrofit + GSON untuk koneksi API (MySQL via JSON)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // \️ Glide untuk load gambar produk
    implementation(libs.glide)

    //  Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
