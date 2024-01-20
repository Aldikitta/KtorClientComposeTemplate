package com.aldikitta.util

import com.aldikitta.data.model.MovieDetailRetrofit
import com.google.gson.JsonParser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

const val CLIENT_ERROR = "Terjadi kesalahan, mohon periksa masukan anda"
const val SERVER_ERROR = "Terjadi kesalahan pada Server, coba lagi nanti"
const val NETWORK_ERROR = "Koneksi internet bermasalah, coba lagi nanti"
const val HTTP_UNKNOWN_ERROR = "HTTP Error tidak diketahui (exc: 4xx/5xx)"
const val UNKNOWN_ERROR = "Error tidak diketahui"

sealed interface KreditPlusResponse<out T> {
    data class Success<T>(val data: T) : KreditPlusResponse<T>
    data class Error<T>(
        val exception: AppException,
        val errorCode: String? = null,
        val responseBodyError: String? = null,
        val data: T? = null
    ) :
        KreditPlusResponse<T>

    data class Loading(val status: Boolean) : KreditPlusResponse<Nothing>
}

open class AppException(
    message: String? = null,
    cause: Throwable? = null,
    val responseBodyError: String? = null
) : Throwable(message, cause)

class NetworkException(message: String? = null, cause: Throwable? = null) :
    AppException(message, cause)

class ServerException(
    message: String? = null,
    cause: Throwable? = null,
    responseBodyError: String? = null
) :
    AppException(message, cause, responseBodyError)

class ClientException(
    message: String? = null,
    cause: Throwable? = null,
    responseBodyError: String? = null
) :
    AppException(message, cause, responseBodyError)

class UnknownException(
    message: String? = null,
    cause: Throwable? = null,
    responseBodyError: String? = null
) :
    AppException(message, cause, responseBodyError)

suspend fun <T> asSuspendKreditPlusResponse(apiCall: suspend () -> T, loadingState: (Boolean) -> Unit): KreditPlusResponse<T> {
    return try {
        loadingState(true)
        val response = apiCall.invoke()
        KreditPlusResponse.Success(response)
    } catch (error: Throwable) {
        val exception = when (error) {
            is HttpException -> {
                val errorBody = error.response()?.errorBody()?.string()
                when (error.code()) {
                    in 400..499 -> {
                        ClientException(
                            message = "${CLIENT_ERROR}: ${error.code()}",
                            cause = error,
                            responseBodyError = errorBody
                        )
                    }

                    in 500..599 -> ServerException(
                        message = "${SERVER_ERROR}: ${error.code()}",
                        cause = error,
                        responseBodyError = errorBody
                    )

                    else -> UnknownException(
                        message = "${HTTP_UNKNOWN_ERROR}: ${error.code()}",
                        cause = error,
                        responseBodyError = errorBody
                    )
                }
            }

            is IOException -> NetworkException(
                message = NETWORK_ERROR,
                cause = error
            )

            else -> AppException(
                message = UNKNOWN_ERROR,
                cause = error
            )
        }

        val errorCode = when (error) {
            is HttpException -> {
                when (error.code()) {
                    in 400..499 -> {
                        "#ER${error.code()}"
                    }

                    in 500..599 -> {
                        "#ER${error.code()}"
                    }

                    else -> {
                        "#ER${error.code()}"
                    }
                }
            }

            else -> {
                "Outside: 4xx/5xx"
            }
        }
        KreditPlusResponse.Error(exception, errorCode, exception.responseBodyError)
    } finally {
        loadingState(false)
    }
}


fun <T> Flow<T>.asFlowKreditPlusResponse(): Flow<KreditPlusResponse<T>> {
    return this
        .map<T, KreditPlusResponse<T>> {
            KreditPlusResponse.Success(it)
        }
        .onStart { emit(KreditPlusResponse.Loading(true)) }
        .onCompletion { emit(KreditPlusResponse.Loading(false)) }
        .catch { error ->
            val exception = when (error) {
                is HttpException -> {
                    val errorBody = error.response()?.errorBody()?.string()
                    when (error.code()) {
                        in 400..499 -> {
                            ClientException(
                                message = "${CLIENT_ERROR}: ${error.code()}",
                                cause = error,
                                responseBodyError = errorBody
                            )
                        }

                        in 500..599 -> {
                            ServerException(
                                message = "${SERVER_ERROR}: ${error.code()}",
                                cause = error,
                                responseBodyError = errorBody
                            )
                        }

                        else -> {
                            UnknownException(
                                message = "${HTTP_UNKNOWN_ERROR}: ${error.code()}",
                                cause = error,
                                responseBodyError = errorBody
                            )
                        }
                    }
                }

                is IOException -> NetworkException(
                    message = NETWORK_ERROR,
                    cause = error
                )

                else -> AppException(
                    message = UNKNOWN_ERROR,
                    cause = error
                )
            }

            val errorCode = when (error) {
                is HttpException -> {
                    when (error.code()) {
                        in 400..499 -> {
                            "#ER${error.code()}"
                        }

                        in 500..599 -> {
                            "#ER${error.code()}"
                        }

                        else -> {
                            "#ER${error.code()}"
                        }
                    }
                }

                else -> {
                    "Outside: 4xx/5xx"
                }
            }
            emit(KreditPlusResponse.Error(exception, errorCode, exception.responseBodyError))
        }
}

// This code is use to handle exception error in paging
fun asPagingKreditPlusResponse(cause: Throwable?): LoadStateError {
    return LoadStateError().apply {
        when (cause) {
            is IOException -> errorMessage = NETWORK_ERROR
            is HttpException -> {
                errorCode = cause.code()
                errorMessage = cause.message()
                responseBodyError = cause.response()?.errorBody()?.string()
                errorMessageStatic = when (errorCode) {
                    in 400..499 -> CLIENT_ERROR
                    in 500..599 -> SERVER_ERROR
                    else -> HTTP_UNKNOWN_ERROR
                }
            }
            else -> errorMessage = UNKNOWN_ERROR
        }
    }
}

data class LoadStateError(
    var errorCode: Int? = null,
    var errorMessage: String? = null,
    var responseBodyError: String? = null,
    var errorMessageStatic: String? = null
)

fun asResponseErrorResponse(
    responseBodyError: String?,
    statusFieldName: String = "status",
    validationArrayName: String = "validation",
    fieldFieldName: String = "field",
    messageFieldName: String = "message",
    statusType: String = "Error validation"
): Map<String, String> {
    val errorMap = mutableMapOf<String, String>()

    responseBodyError?.let {
        try {
            val json = JSONObject(responseBodyError)
            if (json.has(statusFieldName) && json.getString(statusFieldName) == statusType) {
                val validationArray = json.getJSONArray(validationArrayName)
                for (i in 0 until validationArray.length()) {
                    val validationObj = validationArray.getJSONObject(i)
                    val field = validationObj.getString(fieldFieldName)
                    val message = validationObj.getString(messageFieldName)
                    errorMap[field] = message
                }
            } else {
                val validationArray = json.getJSONArray(validationArrayName)
                for (i in 0 until validationArray.length()) {
                    val validationObj = validationArray.getJSONObject(i)
                    val field = validationObj.getString(fieldFieldName)
                    val message = validationObj.getString(messageFieldName)
                    errorMap[field] = message
                }
            }
        } catch (e: JSONException) {
            // Handle JSON parsing error if needed
        }
    }
    return errorMap
}

fun extractMessageFromJson(jsonString: String): String? {
    return try {
        JSONObject(jsonString).optString("message")
    } catch (e: JSONException) {
        null
    }
}

fun handleThrowable(error: Throwable): AppException {
    return when (error) {
        is HttpException -> {
            val errorBody = error.response()?.errorBody()?.string()
            when (error.code()) {
                in 400..499 -> ClientException(CLIENT_ERROR, error, errorBody)
                in 500..599 -> ServerException(SERVER_ERROR, error, errorBody)
                else -> UnknownException(HTTP_UNKNOWN_ERROR, error, errorBody)
            }
        }
        is IOException -> NetworkException(NETWORK_ERROR, error)
        else -> UnknownException(UNKNOWN_ERROR, error)
    }
}