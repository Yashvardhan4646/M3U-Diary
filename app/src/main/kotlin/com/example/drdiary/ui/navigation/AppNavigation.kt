package com.example.drdiary.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.drdiary.data.DiaryRepository
import com.example.drdiary.data.PreferencesRepository
import com.example.drdiary.ui.analytics.MoodAnalyticsScreen
import com.example.drdiary.ui.calendar.CalendarScreen
import com.example.drdiary.ui.editor.EntryEditorScreen
import com.example.drdiary.ui.home.HomeScreen
import com.example.drdiary.ui.lock.AppLockScreen
import com.example.drdiary.ui.settings.SettingsScreen
import kotlinx.coroutines.launch

sealed class Screen {
    data object Home : Screen()
    data class EntryEditor(val entryId: String? = null, val initialDateMillis: Long? = null) : Screen()
    data object MoodAnalytics : Screen()
    data object Calendar : Screen()
    data object Settings : Screen()
}

@Composable
fun AppNavigation(
    preferencesRepository: PreferencesRepository,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember { DiaryRepository(context) }
    val entries by repository.entries.collectAsState()
    val preferences by preferencesRepository.preferences.collectAsState()
    val isSessionUnlocked by preferencesRepository.isSessionUnlocked.collectAsState()

    // If App Lock is enabled and session is locked, intercept with AppLockScreen
    if (preferences.isAppLockEnabled && !isSessionUnlocked && preferences.appLockPin.length == 4) {
        AppLockScreen(
            expectedPin = preferences.appLockPin,
            appName = preferences.appName,
            onUnlocked = { preferencesRepository.unlockSession() },
            onResetLock = {
                scope.launch {
                    preferencesRepository.setAppLock(false, "")
                }
            },
            modifier = modifier
        )
        return
    }

    // Backstack state
    val backStack = remember { mutableStateListOf<Screen>(Screen.Home) }
    var isPopping by remember { mutableStateOf(false) }

    val currentScreen = backStack.lastOrNull() ?: Screen.Home

    fun navigateTo(screen: Screen) {
        isPopping = false
        backStack.add(screen)
    }

    fun navigateBack() {
        if (backStack.size > 1) {
            isPopping = true
            backStack.removeAt(backStack.size - 1)
        }
    }

    fun navigateToTab(screen: Screen) {
        isPopping = false
        if (screen is Screen.Home) {
            backStack.clear()
            backStack.add(Screen.Home)
        } else {
            backStack.clear()
            backStack.add(Screen.Home)
            backStack.add(screen)
        }
    }

    // System back gesture / button support
    BackHandler(enabled = backStack.size > 1) {
        navigateBack()
    }

    // MotionScheme.standard(): smooth transitions with no bounce
    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            val animDuration = 300
            if (isPopping) {
                // Popping back: slide in from left, slide out to right
                (slideInHorizontally(
                    animationSpec = tween(animDuration, easing = FastOutSlowInEasing),
                    initialOffsetX = { -it / 3 }
                ) + fadeIn(animationSpec = tween(animDuration)))
                    .togetherWith(
                        slideOutHorizontally(
                            animationSpec = tween(animDuration, easing = FastOutSlowInEasing),
                            targetOffsetX = { it }
                        ) + fadeOut(animationSpec = tween(animDuration))
                    )
            } else {
                // Pushing new screen: slide in from right, slide out to left
                (slideInHorizontally(
                    animationSpec = tween(animDuration, easing = FastOutSlowInEasing),
                    initialOffsetX = { it }
                ) + fadeIn(animationSpec = tween(animDuration)))
                    .togetherWith(
                        slideOutHorizontally(
                            animationSpec = tween(animDuration, easing = FastOutSlowInEasing),
                            targetOffsetX = { -it / 3 }
                        ) + fadeOut(animationSpec = tween(animDuration))
                    )
            }
        },
        label = "screenTransition",
        modifier = modifier.fillMaxSize()
    ) { screen ->
        when (screen) {
            is Screen.Home -> {
                HomeScreen(
                    entries = entries,
                    repository = repository,
                    preferences = preferences,
                    preferencesRepository = preferencesRepository,
                    onNavigateToEditor = { id -> navigateTo(Screen.EntryEditor(entryId = id)) },
                    onNavigateToMoodAnalytics = { navigateTo(Screen.MoodAnalytics) },
                    onNavigateToCalendar = { navigateTo(Screen.Calendar) },
                    onNavigateToSettings = { navigateTo(Screen.Settings) }
                )
            }
            is Screen.EntryEditor -> {
                EntryEditorScreen(
                    entryId = screen.entryId,
                    repository = repository,
                    onNavigateBack = { navigateBack() },
                    onNavigateHome = { navigateToTab(Screen.Home) },
                    onNavigateMood = { navigateToTab(Screen.MoodAnalytics) },
                    onNavigateCalendar = { navigateToTab(Screen.Calendar) },
                    onNavigateSettings = { navigateToTab(Screen.Settings) }
                )
            }
            is Screen.MoodAnalytics -> {
                MoodAnalyticsScreen(
                    entries = entries,
                    onNavigateBack = { navigateBack() },
                    onNavigateHome = { navigateToTab(Screen.Home) },
                    onNavigateCalendar = { navigateToTab(Screen.Calendar) },
                    onNavigateSettings = { navigateToTab(Screen.Settings) },
                    onEntryClick = { id -> navigateTo(Screen.EntryEditor(entryId = id)) }
                )
            }
            is Screen.Calendar -> {
                CalendarScreen(
                    entries = entries,
                    onNavigateBack = { navigateBack() },
                    onNavigateHome = { navigateToTab(Screen.Home) },
                    onNavigateMood = { navigateToTab(Screen.MoodAnalytics) },
                    onNavigateSettings = { navigateToTab(Screen.Settings) },
                    onEntryClick = { id -> navigateTo(Screen.EntryEditor(entryId = id)) },
                    onNewEntryForDate = { dateMillis ->
                        navigateTo(Screen.EntryEditor(initialDateMillis = dateMillis))
                    }
                )
            }
            is Screen.Settings -> {
                SettingsScreen(
                    repository = repository,
                    preferences = preferences,
                    preferencesRepository = preferencesRepository,
                    onNavigateBack = { navigateBack() },
                    onNavigateHome = { navigateToTab(Screen.Home) },
                    onNavigateMood = { navigateToTab(Screen.MoodAnalytics) },
                    onNavigateCalendar = { navigateToTab(Screen.Calendar) }
                )
            }
        }
    }
}
