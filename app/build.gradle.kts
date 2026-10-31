plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "in.growurdhan.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "in.growurdhan.app"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        // Shell phase only. Not a release version.
        versionName = "0.1.0-shell"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            // Signing and minification land with the enterprise close-out.
            // They are deliberately not faked here.
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.navigation.compose)
    implementation(libs.compose.ui.tooling.preview)
    testImplementation(libs.junit)
}
