plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.movimaps"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.movimaps"
        minSdk = 26
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
}

dependencies {
    // Por default
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Para mapa nativo con osmdroid
    implementation(libs.osmdroid.core)
    implementation(libs.osmdroid.wms)
    implementation(libs.osmdroid.mapsforge)

    // Para permisos de ubicación
    implementation(libs.androidx.core)

    // Para requests HTTP (backend)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)

    // Para Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // Para Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
}