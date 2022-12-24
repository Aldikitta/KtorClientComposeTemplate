plugins {
    id("aldikitta.android.library")
    id("aldikitta.android.hilt")
 }

android {
    namespace = "com.aldikitta.domain"
}

dependencies {
    implementation(project(":core:data"))
//    implementation(libs.androidx.core.core.ktx)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core.jvm)

    //Compose-Paging
    implementation(libs.androidx.paging.paging.compose)

    //Dagger-Hilt
    implementation(libs.javax.inject.javax.inject)
}