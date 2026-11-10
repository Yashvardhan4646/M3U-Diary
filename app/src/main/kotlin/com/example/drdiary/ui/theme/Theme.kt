package com.example.drdiary.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.drdiary.data.AppThemePalette

@Composable
fun DrDiaryTheme(
    palette: AppThemePalette = AppThemePalette.PURPLE,
    content: @Composable () -> Unit
) {
    val scheme = getAppColorScheme(palette)
    MaterialTheme(
        colorScheme = scheme,
        typography = Typography,
        content = content
    )
}
