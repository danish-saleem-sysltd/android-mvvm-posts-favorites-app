package com.example.mvvm.postsfavorites.core.common.exception

class ApiException(
    val apiError: ApiError,
    override val message: String? = null,
    val statusCode: Int? = null,
    val errorCode: String? = null,
) : Exception(message)
