package com.aldikitta.di

import android.util.Log
import com.aldikitta.data.remote.api.movie.MoviesApi
import com.aldikitta.data.remote.api.movie.MoviesApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.kotlinx.serializer.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    fun getHttpClient(): HttpClient {
        return HttpClient(Android) {
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                url {
                    url("https://api.themoviedb.org/3/")
                    parameters.append("api_key", "59cd6896d8432f9c69aed9b86b9c2931")
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
//                    explicitNulls = true
//                    encodeDefaults = false

                })
                engine {
                    connectTimeout = 10_000
                    socketTimeout = 10_000
                }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("ktor_logger:", message)
                    }

                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("http_status:", "${response.status.value}")
                }
            }
        }
    }

    @Provides
    @Singleton
    fun provideDefaultCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    fun provideExternalCoroutineScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Singleton
    @Provides
    fun getMoviesApiImpl(impl: MoviesApiImpl): MoviesApi = impl
}