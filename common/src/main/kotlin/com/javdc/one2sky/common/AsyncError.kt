package com.javdc.one2sky.common

sealed class AsyncError {
    data object ConnectionError : AsyncError()
    data object TimeoutError : AsyncError()
    data object DataParseError : AsyncError()
    data class ServerError(val code: Int) : AsyncError()
    data class UnknownError(val errorThrown: Throwable? = null) : AsyncError()
    data object EmptyResponseError : AsyncError()
    open class CustomError : AsyncError()
}
