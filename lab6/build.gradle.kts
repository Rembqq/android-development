plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.lab6"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.lab6"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
//    implementation("androidx.core:core-ktx:1.12.0")
//    implementation("androidx.appcompat:appcompat:1.6.1")
//    implementation("com.google.android.material:material:1.11.0")
//    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
//
//    // CameraX
//    val cameraxVersion = "1.3.1"
//    implementation("androidx.camera:camera-core:$cameraxVersion")
//    implementation("androidx.camera:camera-camera2:$cameraxVersion")
//    implementation("androidx.camera:camera-lifecycle:$cameraxVersion")
//    implementation("androidx.camera:camera-view:1.3.1")
//    implementation("androidx.camera:camera-extensions:1.3.1")
//
//    // GPUImage
//    implementation("jp.co.cyberagent.android:gpuimage:2.1.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // CameraX
    implementation("androidx.camera:camera-core:1.3.0")
    implementation("androidx.camera:camera-camera2:1.3.0")
    implementation("androidx.camera:camera-lifecycle:1.3.0")
    implementation("androidx.camera:camera-view:1.3.0")

    // GPUImage
    implementation("jp.co.cyberagent.android:gpuimage:2.1.0")
    implementation(libs.androidx.lifecycle.common.jvm)

    implementation("androidx.camera:camera-extensions:1.3.0")

}
