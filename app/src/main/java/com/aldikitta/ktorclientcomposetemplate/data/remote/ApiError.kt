package com.aldikitta.ktorclientcomposetemplate.data.remote

data class ApiError(
    val errorCode: Int,
    val statusCode: Int?,
    val statusMessage: String?
)