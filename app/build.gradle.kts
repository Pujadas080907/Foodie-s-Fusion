plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.foodfusion"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.foodfusion"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // navigation com

    val version = "2.7.7"
    implementation ("androidx.navigation:navigation-fragment-ktx:$version")
    implementation ("androidx.navigation:navigation-ui-ktx:$version")

    //intuit
    implementation  ("com.intuit.sdp:sdp-android:1.0.6")
    implementation  ("com.intuit.ssp:ssp-android:1.0.6")

    //animation
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.17")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.3.0")

    //glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation("androidx.core:core-splashscreen:1.0.1")

    //videoModel mvvm

    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.8.3")
   // implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3")

    //Room
    val roomVersion = "2.6.1"

    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

}