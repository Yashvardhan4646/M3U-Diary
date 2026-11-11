package com.example.drdiary

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.drdiary.data.PreferencesRepository
import com.example.drdiary.ui.navigation.AppNavigation
import com.example.drdiary.ui.theme.DrDiaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable high refresh rate on supported displays (90Hz / 120Hz)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val currentDisplay = display
                val modes = currentDisplay?.supportedModes
                val highestMode = modes?.maxByOrNull { it.refreshRate }
                if (highestMode != null) {
                    val params = window.attributes
                    params.preferredDisplayModeId = highestMode.modeId
                    window.attributes = params
                }
            } catch (_: Exception) {
            }
        }

        enableEdgeToEdge()
        val preferencesRepository = PreferencesRepository(applicationContext)

        setContent {
            val preferences by preferencesRepository.preferences.collectAsState()

            // App Switcher Privacy (FLAG_SECURE)
            LaunchedEffect(preferences.appSwitcherPrivacyEnabled) {
                if (preferences.appSwitcherPrivacyEnabled) {
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_SECURE,
                        WindowManager.LayoutParams.FLAG_SECURE
                    )
                } else {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
                }
            }

            DrDiaryTheme(
                palette = preferences.selectedTheme,
                themeMode = preferences.themeMode
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {
                    AppNavigation(preferencesRepository = preferencesRepository)
                }
            }
        }
    }
}
