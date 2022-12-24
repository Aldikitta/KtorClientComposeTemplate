package com.aldikitta.data.remote

sealed class Resource<out R> {
    data class Success<out R>(val result: R): Resource<R>()
    data class Failure(val exception: Exception): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}

//sealed class Resource<T>(val data: T? = null, val message: String? = null) {
//    class Success<T>(data: T?): Resource<T>(data)
//    class Failure<T>(message: String, data: T? = null): Resource<T>(data, message)
//    object Loading: Resource<Nothing>()
//}
//
//sealed class Resource<T>(val data: T? = null, val message: String? = null) {
//    class Success<T>(data: T) : Resource<T>(data)
//    class Loading<T>(data: T? = null) : Resource<T>(data)
//    class Failure<T>(message: String, data: T? = null) : Resource<T>(data, message)
//}

//sealed class Resource<out T> {
//    class Success<T>(val data: T?) : Resource<T>()
//    class Failure<T>(val apiError: ApiError) : Resource<T>()
//    class Exception<T>(val exception: Throwable) : Resource<T>()
//}
//
//fun <T> Resource<T>.onSuccess(onResult: Resource.Success<T>.() -> Unit): Resource<T> {
//    if (this is Resource.Success)
//        onResult(this)
//    return this
//}
//
//fun <T> Resource<T>.onFailure(onResult: Resource.Failure<*>.() -> Unit): Resource<T> {
//    if (this is Resource.Failure<*>)
//        onResult(this)
//    return this
//}
//
//fun <T> Resource<T>.onException(onResult: Resource.Exception<*>.() -> Unit): Resource<T> {
//    if (this is Resource.Exception<*>)
//        onResult(this)
//    return this
//}