package com.javdc.one2sky.presentation.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Dark Mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(name = "Light Mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
annotation class PreviewDarkLight

@Preview(name = "Landscape Mode", showBackground = true, device = "spec:parent=pixel_8,orientation=landscape")
@Preview(name = "Portrait Mode", showBackground = true, device = "spec:parent=pixel_8")
annotation class PreviewOrientations

@Preview(name = "Default Font Size", fontScale = 1f)
@Preview(name = "Large Font Size", fontScale = 1.5f)
annotation class PreviewFontSizes
