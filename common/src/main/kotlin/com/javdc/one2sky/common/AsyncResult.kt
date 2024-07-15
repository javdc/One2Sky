package com.javdc.one2sky.common

sealed class AsyncResult<out T> {
    data class Loading<out T>(val data: T? = null) : AsyncResult<T>()
    data class Success<out T>(val data: T) : AsyncResult<T>()
    data class Error<out T>(val error: AsyncError) : AsyncResult<T>()
}
