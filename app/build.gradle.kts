plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.main.limitless"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.main.limitless"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }
    buildFeatures {
        viewBinding = true
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
}
dependencies {
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.gson)
    implementation(libs.androidx.media)
    implementation(libs.play.services.auth)
    implementation(libs.androidx.security.crypto)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation("com.github.1902shubh:SendMail:1.0.0")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.2.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    testImplementation ("org.robolectric:robolectric:4.9")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("com.github.kittinunf.fuel:fuel-gson:2.3.1")
    implementation("com.google.code.gson:gson:2.8.7")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.gms:play-services-location:20.0.0")
    //implementation ("com.android.volley:volley:1.0.0")
    implementation ("com.squareup.retrofit2:retrofit:2.3.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.3.0")
    implementation ("com.google.android.material:material:1.3.0")



}