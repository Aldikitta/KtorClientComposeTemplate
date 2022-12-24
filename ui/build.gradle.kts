plugins {
    id("aldikitta.android.library")
    id("aldikitta.android.library.compose")
}

android {
    namespace = "com.aldikitta.ui"
}

dependencies {
    implementation(project(":core:data"))

    implementation(libs.androidx.activity.activity.compose)
    implementation(libs.androidx.lifecycle.lifecycle.runtime.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.material.icons.extended)

    //Compose-Paging
    implementation(libs.androidx.paging.paging.compose)

    implementation(libs.com.google.accompanist.accompanist.placeholder)

    //Coil
    implementation(libs.io.coil.kt.coil.compose)
}