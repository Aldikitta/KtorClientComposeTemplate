plugins {
    id("aldikitta.android.application")
    id("aldikitta.android.application.compose")
    id("aldikitta.android.hilt")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.aldikitta.ktorclientcomposetemplate"

    defaultConfig {
        applicationId = "com.aldikitta.ktorclientcomposetemplate"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":feature:remote-request"))
    implementation(project(":core:data"))
    implementation(project(":feature:local-request"))
    implementation(project(":feature:movie_detail"))
    implementation(project(":ui"))
    implementation(libs.androidx.core.core.ktx)
    implementation(libs.androidx.lifecycle.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.activity.compose)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.material.icons.extended)
    implementation(libs.androidx.compose.material3.window)
    implementation(libs.androidx.tracing.ktx)

//    //Compose-Paging
//    implementation(libs.androidx.paging.paging.compose)
//
//    //Kotlinx-Serialization
//    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)
//
//    //Ktor-Client
//    implementation(libs.io.ktor.ktor.client.android)
//    implementation(libs.io.ktor.ktor.client.serialization)
//    implementation(libs.io.ktor.ktor.client.content.negotiation)
//    implementation(libs.io.ktor.ktor.serialization.kotlinx.json)
//    implementation(libs.io.ktor.ktor.client.logging.jvm)

//    //Dagger-Hilt
//    implementation(libs.androidx.hilt.hilt.navigation.compose)
//
    //Compose-navigation
    implementation(libs.androidx.navigation.navigation.compose)
//
//    //Coil
//    implementation(libs.io.coil.kt.coil.compose)
//
//    //Room
//    implementation(libs.androidx.room.room.runtime)
//    implementation(libs.androidx.room.room.ktx)
//    implementation(libs.androidx.room.room.paging)
//    kapt(libs.androidx.room.room.compiler)
//
//    //Accompanist
//    implementation(libs.com.google.accompanist.accompanist.swiperefresh)
//    implementation(libs.com.google.accompanist.accompanist.pager)
//    implementation(libs.com.google.accompanist.accompanist.pager.indicators)
//    implementation(libs.com.google.accompanist.accompanist.permissions)
//    implementation(libs.com.google.accompanist.accompanist.placeholder)
//
//    testImplementation(libs.junit)
//
//    androidTestImplementation(libs.androidx.test.ext.junit)
//    androidTestImplementation(libs.androidx.test.espresso.espresso.core)
//    androidTestImplementation(libs.androidx.compose.ui.ui.test.junit4)
//
//    debugImplementation(libs.androix.compose.ui.ui.tooling)
//    debugImplementation(libs.androix.compose.ui.ui.test.manifest)
}