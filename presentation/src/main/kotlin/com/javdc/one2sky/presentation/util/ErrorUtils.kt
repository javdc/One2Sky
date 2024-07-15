package com.javdc.one2sky.presentation.util

import android.content.Context
import com.javdc.one2sky.common.AsyncError
import com.javdc.one2sky.presentation.R

fun getErrorTitle(context: Context, error: AsyncError?): String {
    return when (error) {
        is AsyncError.ServerError -> context.getString(R.string.error_generic_with_http_code, error.code)
        is AsyncError.ConnectionError -> context.getString(R.string.error_connection_error)
        is AsyncError.TimeoutError -> context.getString(R.string.error_timeout_error)
        is AsyncError.EmptyResponseError -> context.getString(R.string.error_empty_response_error)
        is AsyncError.DataParseError -> context.getString(R.string.error_data_parse_error)
        else -> context.getString(R.string.error_generic)
    }
}

fun getErrorDescription(context: Context, error: AsyncError?): String {
    return when (error) {
        is AsyncError.ServerError -> context.getString(R.string.error_generic_description)
        is AsyncError.ConnectionError -> context.getString(R.string.error_connection_error_description)
        is AsyncError.TimeoutError -> context.getString(R.string.error_timeout_error_description)
        is AsyncError.EmptyResponseError -> context.getString(R.string.error_empty_response_error_description)
        is AsyncError.DataParseError -> context.getString(R.string.error_data_parse_error_description)
        else -> context.getString(R.string.error_generic_description)
    }
}
