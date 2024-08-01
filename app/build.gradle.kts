plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.adilashraf.retrofitandvolley"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.adilashraf.retrofitandvolley"
        minSdk = 26
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    // Volley
    implementation("com.android.volley:volley:1.2.1")
     // Gson Convertor
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    // GLide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    // Circle Image
    implementation("de.hdodenhof:circleimageview:3.1.0")
    // OkaHttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("io.github.tashilapathum:please-wait:0.5.0")


}