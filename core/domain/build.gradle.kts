plugins {
    id("aldikitta.android.library")
    id("aldikitta.android.hilt")
 }

android {
    namespace = "com.aldikitta.domain"
}

dependencies {
    implementation(project(":core:data"))
    implementation(libs.org.jetbrains.kotlin.kotlin.stdlib)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core.jvm)

    //Compose-Paging
    implementation(libs.androidx.paging.paging.compose)

    //Dagger-Hilt
    implementation(libs.javax.inject.javax.inject)
}