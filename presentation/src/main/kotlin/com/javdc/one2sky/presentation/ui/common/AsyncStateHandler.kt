package com.javdc.one2sky.presentation.ui.common

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.javdc.one2sky.common.AsyncError
import com.javdc.one2sky.common.AsyncResult

@Composable
fun <T> AsyncStateHandler(
    asyncResult: AsyncResult<T>,
    onClickRetry: (() -> Unit)? = null,
    onLoading: @Composable () -> Unit = {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    },
    onError: @Composable (AsyncError) -> Unit = { error ->
        ErrorInfo(
            error = error,
            onRetry = onClickRetry,
        )
    },
    onSuccess: @Composable (T) -> Unit
) {
    Crossfade(
        targetState = asyncResult,
        label = "AsyncResult Crossfade"
    ) { targetAsyncResult ->
        when (targetAsyncResult) {
            is AsyncResult.Loading -> onLoading()
            is AsyncResult.Error -> onError(targetAsyncResult.error)
            is AsyncResult.Success -> onSuccess(targetAsyncResult.data)
        }
    }
}
