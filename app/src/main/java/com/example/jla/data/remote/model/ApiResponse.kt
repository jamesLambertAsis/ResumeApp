package com.example.jla.data.remote.model

sealed class ApiResponse<out T, out E: Exception> {
    data class Success<T>(val data: T): ApiResponse<T, Nothing>()
    data class Error<E : Exception>(val exceptionError: E? = null, val errorMessage: String?): ApiResponse<Nothing, E>()
}