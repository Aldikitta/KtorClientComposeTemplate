plugins {
    id("aldikitta.android.library")
    id("aldikitta.android.library.compose")
    id("aldikitta.android.hilt")
}

android {
    namespace = "com.aldikitta.remote_request"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":feature:movie_detail"))
    implementation(project(":ui"))

    implementation(libs.androidx.activity.activity.compose)
    implementation(libs.androidx.lifecycle.lifecycle.runtime.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material.material.icons.extended)
    implementation(libs.androidx.lifecycle.lifecycle.viewmodel.compose)

    //Compose-Paging
    implementation(libs.androidx.paging.paging.compose)

    //Coil
    implementation(libs.io.coil.kt.coil.compose)

    implementation(libs.com.google.accompanist.accompanist.swiperefresh)

    implementation(libs.androidx.navigation.navigation.compose)

    //Dagger-Hilt
    implementation(libs.androidx.hilt.hilt.navigation.compose)
}