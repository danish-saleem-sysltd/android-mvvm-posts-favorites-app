package com.example.mvvm.postsfavorites.core.common.result

import com.example.mvvm.postsfavorites.core.common.exception.ApiException

sealed class Result<out T> {
    data class Success<out T>(val data: T, val message: String? = null) : Result<T>()
    data class Error(val exception: ApiException) : Result<Nothing>()
}
