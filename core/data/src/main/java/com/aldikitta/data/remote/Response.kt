package com.aldikitta.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

sealed interface Response<out T> {
    data class Success<T>(val data: T) : Response<T>
    data class Error(val exception: Throwable? = null) : Response<Nothing>
    data class Loading(val status: Boolean) : Response<Nothing>
}

fun <T> Flow<T>.asResponse(): Flow<Response<T>> {
    return this
        .map<T, Response<T>> {
            Response.Success(it)
        }
        .onStart { emit(Response.Loading(true)) }
        .onCompletion { emit(Response.Loading(false)) }
        .catch { error ->
            emit(Response.Error(Throwable(message = error.message)))
            emit(Response.Error(error))
            emit(Response.Loading(false))
        }
}

//fun <T> Flow<Response<T>>.onProgress() =
//    onStart {
//        emit(Response.Loading(true))
//    }.onCompletion {
//        emit(Response.Loading(false))
//    }
//
//fun <T> Flow<Response<T>>.buildNetworkRequest() =
//    catch { error ->
//        emit(Response.Error(Throwable(message = error.message)))
//        emit(Response.Loading(false))
//    }
//        .flowOn(Dispatchers.IO)

//sealed class Response<out T> {
//    class Success<T>(val data: T) : Response<T>()
//    class Error(val error: Throwable) : Response<Nothing>()
//    data class Loading(val status: Boolean) : Response<Nothing>()
//}


