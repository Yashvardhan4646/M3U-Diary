package com.example.drdiary.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.drdiary.data.AppThemePalette
import com.example.drdiary.data.ThemeMode

@Composable
fun DrDiaryTheme(
    palette: AppThemePalette = AppThemePalette.PURPLE,
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val systemInDark = isSystemInDarkTheme()
    val isDark = when (themeMode) {
        ThemeMode.SYSTEM -> systemInDark
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.AMOLED -> true
    }
    val isAmoled = themeMode == ThemeMode.AMOLED

    val scheme = getAppColorScheme(palette, isDark = isDark, isAmoled = isAmoled)
    MaterialTheme(
        colorScheme = scheme,
        typography = Typography,
        content = content
    )
}
