plugins {
    id("aldikitta.android.library")
    id("kotlinx-serialization")
    id("aldikitta.android.hilt")
    id("org.jetbrains.kotlin.android")
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

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.github.chuckerteam.chucker:library:3.5.2")
    implementation("com.squareup.moshi:moshi:1.14.0")


    //Dagger-Hilt
    implementation(libs.javax.inject.javax.inject)

    //Room
    implementation(libs.androidx.room.room.runtime)
    implementation(libs.androidx.room.room.ktx)
    implementation(libs.androidx.room.room.paging)
    kapt(libs.androidx.room.room.compiler)
}