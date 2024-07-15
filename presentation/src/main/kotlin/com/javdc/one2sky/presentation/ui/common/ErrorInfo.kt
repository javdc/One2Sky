package com.javdc.one2sky.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.javdc.one2sky.common.AsyncError
import com.javdc.one2sky.presentation.R
import com.javdc.one2sky.presentation.ui.theme.AppTheme
import com.javdc.one2sky.presentation.util.getErrorDescription
import com.javdc.one2sky.presentation.util.getErrorTitle

@Composable
fun ErrorInfo(
    error: AsyncError?,
    onRetry: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(28.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_error_big),
            contentDescription = null,
        )
        Text(
            text = getErrorTitle(LocalContext.current, error),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp),
        )
        Text(
            text = getErrorDescription(LocalContext.current, error),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 12.dp),
        )
        onRetry?.let {
            Button(
                onClick = { onRetry() },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(id = R.string.action_retry))
            }
        }
    }
}

@PreviewDarkLight
@Composable
fun ErrorPreview(@PreviewParameter(AsyncErrorProvider::class) error: AsyncError) {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            ErrorInfo(error) { /* no-op */ }
        }
    }
}
