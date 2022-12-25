plugins {
    id("aldikitta.android.library")
    id("kotlinx-serialization")
    id("aldikitta.android.hilt")
}

android {
    namespace = "com.aldikitta.data"
}

dependencies {
    implementation(libs.androidx.core.core.ktx)
    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.core.jvm)

    //Kotlinx-Serialization
    implementation(libs.org.jetbrains.kotlinx.kotlinx.serialization.json)

    //Compose-Paging
    implementation(libs.androidx.paging.paging.compose)

    //Ktor-Client
    implementation(libs.io.ktor.ktor.client.android)
    implementation(libs.io.ktor.ktor.client.serialization)
    implementation(libs.io.ktor.ktor.client.content.negotiation)
    implementation(libs.io.ktor.ktor.serialization.kotlinx.json)
    implementation(libs.io.ktor.ktor.client.logging.jvm)

    //Dagger-Hilt
    implementation(libs.javax.inject.javax.inject)

    //Room
    implementation(libs.androidx.room.room.runtime)
    implementation(libs.androidx.room.room.ktx)
    implementation(libs.androidx.room.room.paging)
    kapt(libs.androidx.room.room.compiler)
}