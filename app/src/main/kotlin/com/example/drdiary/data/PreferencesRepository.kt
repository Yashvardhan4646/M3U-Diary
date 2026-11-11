package com.example.drdiary.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
enum class AppThemePalette(val label: String, val primaryHex: Long) {
    PURPLE("M3 Purple", 0xFF6750A4),
    OCEAN_BLUE("Ocean Blue", 0xFF0061A4),
    EMERALD("Emerald Green", 0xFF006C4C),
    ROSE("Berry Rose", 0xFF9C4146),
    AMBER("Warm Amber", 0xFF865300)
}

@Serializable
enum class ThemeMode(val label: String) {
    SYSTEM("System Default"),
    LIGHT("Light Mode"),
    DARK("Dark Mode"),
    AMOLED("Pitch Black (AMOLED)")
}

@Serializable
data class UserPreferences(
    val appName: String = "M3Diary",
    val appHeading: String = "My Diary",
    val selectedTheme: AppThemePalette = AppThemePalette.PURPLE,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val isAppLockEnabled: Boolean = false,
    val appLockPin: String = "",
    val biometricUnlockEnabled: Boolean = true,
    val appSwitcherPrivacyEnabled: Boolean = false,
    val reminderEnabled: Boolean = false,
    val reminderHour: Int = 20,
    val reminderMinute: Int = 0
)

class PreferencesRepository(private val context: Context) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }

    private val file: File
        get() = File(context.filesDir, "user_preferences.json")

    private val _preferences = MutableStateFlow(UserPreferences())
    val preferences: StateFlow<UserPreferences> = _preferences.asStateFlow()

    // Runtime lock session state (not persisted so app locks when opened afresh)
    private val _isSessionUnlocked = MutableStateFlow(false)
    val isSessionUnlocked: StateFlow<Boolean> = _isSessionUnlocked.asStateFlow()

    init {
        loadPreferences()
    }

    private fun loadPreferences() {
        scope.launch {
            try {
                if (file.exists()) {
                    val content = file.readText()
                    if (content.isNotBlank()) {
                        val parsed = json.decodeFromString<UserPreferences>(content)
                        _preferences.value = parsed
                        // If lock is not enabled, mark unlocked by default
                        if (!parsed.isAppLockEnabled || parsed.appLockPin.length != 4) {
                            _isSessionUnlocked.value = true
                        }
                        return@launch
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            _preferences.value = UserPreferences()
            _isSessionUnlocked.value = true
        }
    }

    private suspend fun persistPreferences(prefs: UserPreferences) = withContext(Dispatchers.IO) {
        try {
            val content = json.encodeToString(prefs)
            val temp = File(context.filesDir, "user_preferences.json.tmp")
            temp.writeText(content)
            temp.renameTo(file)
            _preferences.value = prefs
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun updateAppNameAndHeading(name: String, heading: String) {
        val updated = _preferences.value.copy(
            appName = name.ifBlank { "M3Diary" },
            appHeading = heading.ifBlank { "My Diary" }
        )
        persistPreferences(updated)
    }

    suspend fun updateTheme(palette: AppThemePalette, mode: ThemeMode = _preferences.value.themeMode) {
        val updated = _preferences.value.copy(selectedTheme = palette, themeMode = mode)
        persistPreferences(updated)
    }

    suspend fun updateThemeMode(mode: ThemeMode) {
        val updated = _preferences.value.copy(themeMode = mode)
        persistPreferences(updated)
    }

    suspend fun updateBiometricUnlock(enabled: Boolean) {
        val updated = _preferences.value.copy(biometricUnlockEnabled = enabled)
        persistPreferences(updated)
    }

    suspend fun updateAppSwitcherPrivacy(enabled: Boolean) {
        val updated = _preferences.value.copy(appSwitcherPrivacyEnabled = enabled)
        persistPreferences(updated)
    }

    suspend fun setAppLock(enabled: Boolean, pin: String) {
        val updated = _preferences.value.copy(
            isAppLockEnabled = enabled && pin.length == 4,
            appLockPin = if (enabled) pin else ""
        )
        persistPreferences(updated)
        if (!enabled) {
            _isSessionUnlocked.value = true
        }
    }

    suspend fun updateReminder(enabled: Boolean, hour: Int, minute: Int) {
        val updated = _preferences.value.copy(
            reminderEnabled = enabled,
            reminderHour = hour,
            reminderMinute = minute
        )
        persistPreferences(updated)
    }

    fun unlockSession() {
        _isSessionUnlocked.value = true
    }

    fun lockSession() {
        if (_preferences.value.isAppLockEnabled && _preferences.value.appLockPin.length == 4) {
            _isSessionUnlocked.value = false
        }
    }
}
