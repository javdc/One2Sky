package com.javdc.one2sky.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun TextWithIcon(
    text: String,
    iconPainter: Painter,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    fontSize: TextUnit = TextUnit. Unspecified,
    iconSize: Dp? = null,
    iconPadding: Dp = 0.dp,
    iconTint: Color = LocalContentColor.current,
    iconContentDescription: String? = null,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(iconPadding, horizontalAlignment),
        modifier = modifier,
    ) {
        Icon(
            painter = iconPainter,
            tint = iconTint,
            contentDescription = iconContentDescription,
            modifier = iconSize?.let { Modifier.size(it) } ?: Modifier,
        )
        Text(
            text = text,
            style = style,
            fontSize = fontSize,
        )
    }
}
