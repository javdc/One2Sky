package com.javdc.one2sky.data.repository.util

import co.touchlab.kermit.Logger
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import com.javdc.one2sky.common.AsyncError
import com.javdc.one2sky.common.AsyncResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.internal.http2.ConnectionShutdownException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

object RepositoryErrorManager {

    inline fun <T> wrap(crossinline block: suspend () -> T): Flow<AsyncResult<T>> =
        flow {
            emit(AsyncResult.Loading())
            try {
                emit(AsyncResult.Success(block()))
            } catch (e: CancellationException) {
                // CancellationException should never be intercepted, throw it as-is
                throw e
            } catch (e: Exception) {
                Logger.e("Caught exception in RepositoryErrorManager wrapper", e, "RepositoryErrorManager")
                emit(AsyncResult.Error(e.toAsyncError()))
            }
        }

    fun Exception.toAsyncError(): AsyncError {
        return when (this) {
            is HttpException -> AsyncError.ServerError(code())
            is UnknownHostException,
            is ConnectionShutdownException -> AsyncError.ConnectionError
            is SocketTimeoutException -> AsyncError.TimeoutError
            is MalformedJsonException,
            is JsonParseException -> AsyncError.DataParseError
            else -> AsyncError.UnknownError(this)
        }
    }

}
