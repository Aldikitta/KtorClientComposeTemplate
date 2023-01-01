package com.aldikitta.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

fun <T> Flow<Response<T>>.onProgress() =
    onStart {
        emit(Response.Loading(true))
    }.onCompletion {
        emit(Response.Loading(false))
    }

fun <T> Flow<Response<T>>.buildNetworkRequest() =
    catch { error ->
        emit(Response.Error(Throwable(message = error.message)))
        emit(Response.Loading(false))
    }
        .flowOn(Dispatchers.IO)