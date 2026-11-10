package com.example.drdiary.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Mood
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Title
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.drdiary.data.DiaryEntry
import com.example.drdiary.data.DiaryRepository
import com.example.drdiary.data.PreferencesRepository
import com.example.drdiary.data.UserPreferences
import com.example.drdiary.ui.components.AppDrawerContent
import com.example.drdiary.ui.components.AppLockSettingsDialog
import com.example.drdiary.ui.components.ConnectedButtonGroup
import com.example.drdiary.ui.components.DiaryFilter
import com.example.drdiary.ui.components.ReminderSettingsDialog
import com.example.drdiary.ui.components.RenameAppDialog
import com.example.drdiary.ui.components.StackedDiaryItem
import com.example.drdiary.ui.components.ThemeSelectionDialog
import com.example.drdiary.ui.components.pressScale
import kotlinx.coroutines.launch

enum class HomeBottomNavDestination {
    HOME,
    SEARCH,
    SAVED,
    SETTINGS
}

@Composable
fun HomeScreen(
    entries: List<DiaryEntry>,
    repository: DiaryRepository,
    preferences: UserPreferences,
    preferencesRepository: PreferencesRepository,
    onNavigateToEditor: (String?) -> Unit,
    onNavigateToMoodAnalytics: () -> Unit,
    onNavigateToCalendar: () -> Unit,
    onNavigateToSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    var currentFilter by remember { mutableStateOf(DiaryFilter.ALL_ENTRIES) }
    var selectedDestination by remember { mutableStateOf(HomeBottomNavDestination.HOME) }
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var showMoreMenu by remember { mutableStateOf(false) }

    // Dialogs state
    var showThemeDialog by remember { mutableStateOf(false) }
    var showReminderDialog by remember { mutableStateOf(false) }
    var showRenameDialog by remember { mutableStateOf(false) }
    var showLockDialog by remember { mutableStateOf(false) }

    // Apply Filter & Search
    val displayedEntries: List<DiaryEntry> = remember(entries, currentFilter, selectedDestination, searchQuery) {
        val baseList = when {
            selectedDestination == HomeBottomNavDestination.SAVED -> entries.filter { it.isFavorite }
            currentFilter == DiaryFilter.FAVORITES -> entries.filter { it.isFavorite }
            else -> entries
        }

        if (searchQuery.isNotBlank()) {
            baseList.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.content.contains(searchQuery, ignoreCase = true) ||
                        it.dateFormatted.contains(searchQuery, ignoreCase = true) ||
                        it.mood.displayName.contains(searchQuery, ignoreCase = true)
            }
        } else {
            baseList
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                preferences = preferences,
                totalEntries = entries.size,
                currentScreenKey = "home",
                onNavigateHome = { },
                onNavigateMood = onNavigateToMoodAnalytics,
                onNavigateCalendar = onNavigateToCalendar,
                onNavigateSaved = {
                    selectedDestination = HomeBottomNavDestination.SAVED
                    currentFilter = DiaryFilter.FAVORITES
                },
                onNavigateSettings = onNavigateToSettings,
                onOpenThemeDialog = { showThemeDialog = true },
                onOpenReminderDialog = { showReminderDialog = true },
                onOpenRenameDialog = { showRenameDialog = true },
                onOpenLockDialog = { showLockDialog = true },
                onLockAppNow = { preferencesRepository.lockSession() },
                onCloseDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                Surface(
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .windowInsetsPadding(WindowInsets.statusBars)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .padding(horizontal = 8.dp)
                        ) {
                            // Left 48dp button: Working Sidebar Hamburger Menu
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                },
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .size(48.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Menu,
                                    contentDescription = "Open Navigation Drawer",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            // Centered Title in titleLarge
                            Text(
                                text = preferences.appHeading,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.align(Alignment.Center)
                            )

                            // Right action buttons: Palette (theme switcher) + More options
                            Row(
                                modifier = Modifier.align(Alignment.CenterEnd),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = { showThemeDialog = true },
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Palette,
                                        contentDescription = "Change Theme",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                Box {
                                    IconButton(
                                        onClick = { showMoreMenu = true },
                                        modifier = Modifier.size(44.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.MoreVert,
                                            contentDescription = "More options",
                                            tint = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = showMoreMenu,
                                        onDismissRequest = { showMoreMenu = false },
                                        shape = RoundedCornerShape(16.dp),
                                        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("Color Theme") },
                                            leadingIcon = {
                                                Icon(
                                                    Icons.Rounded.Palette,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            },
                                            onClick = {
                                                showMoreMenu = false
                                                showThemeDialog = true
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Daily Reminders") },
                                            leadingIcon = {
                                                Icon(
                                                    Icons.Rounded.NotificationsActive,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            },
                                            onClick = {
                                                showMoreMenu = false
                                                showReminderDialog = true
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Customize Title") },
                                            leadingIcon = {
                                                Icon(
                                                    Icons.Rounded.Title,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            },
                                            onClick = {
                                                showMoreMenu = false
                                                showRenameDialog = true
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Load Sketch Sample Entries") },
                                            leadingIcon = {
                                                Icon(
                                                    Icons.Rounded.AutoAwesome,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            },
                                            onClick = {
                                                showMoreMenu = false
                                                scope.launch {
                                                    repository.seedSampleEntries()
                                                    snackbarHostState.showSnackbar("Sample sketch entries loaded")
                                                }
                                            }
                                        )
                                        if (preferences.isAppLockEnabled) {
                                            DropdownMenuItem(
                                                text = { Text("Lock App Now") },
                                                leadingIcon = {
                                                    Icon(
                                                        Icons.Rounded.Lock,
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.error
                                                    )
                                                },
                                                onClick = {
                                                    showMoreMenu = false
                                                    preferencesRepository.lockSession()
                                                }
                                            )
                                        }
                                        DropdownMenuItem(
                                            text = { Text("Settings") },
                                            leadingIcon = {
                                                Icon(
                                                    Icons.Rounded.Settings,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            },
                                            onClick = {
                                                showMoreMenu = false
                                                onNavigateToSettings()
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            },
            bottomBar = {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .windowInsetsPadding(WindowInsets.navigationBars)
                    ) {
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surfaceContainer,
                            modifier = Modifier.height(80.dp)
                        ) {
                            NavigationBarItem(
                                selected = selectedDestination == HomeBottomNavDestination.HOME,
                                onClick = {
                                    selectedDestination = HomeBottomNavDestination.HOME
                                    isSearchActive = false
                                    searchQuery = ""
                                },
                                icon = {
                                    Icon(Icons.Rounded.Home, contentDescription = "Home")
                                },
                                label = {
                                    Text(
                                        text = "Home",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                                )
                            )

                            NavigationBarItem(
                                selected = selectedDestination == HomeBottomNavDestination.SEARCH,
                                onClick = {
                                    selectedDestination = HomeBottomNavDestination.SEARCH
                                    isSearchActive = true
                                },
                                icon = {
                                    Icon(Icons.Rounded.Search, contentDescription = "Search")
                                },
                                label = {
                                    Text(
                                        text = "Search",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                                )
                            )

                            NavigationBarItem(
                                selected = selectedDestination == HomeBottomNavDestination.SAVED,
                                onClick = {
                                    selectedDestination = HomeBottomNavDestination.SAVED
                                    currentFilter = DiaryFilter.FAVORITES
                                    isSearchActive = false
                                    searchQuery = ""
                                },
                                icon = {
                                    Icon(Icons.Rounded.Favorite, contentDescription = "Saved")
                                },
                                label = {
                                    Text(
                                        text = "Saved",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                                )
                            )

                            NavigationBarItem(
                                selected = selectedDestination == HomeBottomNavDestination.SETTINGS,
                                onClick = {
                                    onNavigateToSettings()
                                },
                                icon = {
                                    Icon(Icons.Rounded.Settings, contentDescription = "Settings")
                                },
                                label = {
                                    Text(
                                        text = "Settings",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                                )
                            )
                        }
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { onNavigateToEditor(null) },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = RoundedCornerShape(16.dp),
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 10.dp
                    ),
                    modifier = Modifier
                        .size(56.dp)
                        .pressScale(onClick = { onNavigateToEditor(null) })
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "New Diary Entry",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.surface),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Centered Connected Button Group: "Favorites" (filled), "All Entries" (tonal)
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        ConnectedButtonGroup(
                            selectedFilter = currentFilter,
                            onFilterSelected = { filter ->
                                currentFilter = filter
                                if (filter == DiaryFilter.ALL_ENTRIES && selectedDestination == HomeBottomNavDestination.SAVED) {
                                    selectedDestination = HomeBottomNavDestination.HOME
                                }
                            },
                            favoritesCount = entries.count { it.isFavorite },
                            allCount = entries.size
                        )
                    }
                }

                // Search Bar when Search destination active or toggled
                if (isSearchActive || selectedDestination == HomeBottomNavDestination.SEARCH) {
                    item {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 14.dp),
                            placeholder = { Text("Search title, memories, tags...") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Search,
                                    contentDescription = "Search",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(
                                            imageVector = Icons.Rounded.Clear,
                                            contentDescription = "Clear search",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                            )
                        )
                    }
                }

                // Empty State with Load Sample action
                if (displayedEntries.isEmpty()) {
                    item {
                        Surface(
                            shape = RoundedCornerShape(24.dp),
                            color = MaterialTheme.colorScheme.surfaceContainerLow,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Edit,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = if (searchQuery.isNotBlank()) "No Matching Reflections"
                                    else if (currentFilter == DiaryFilter.FAVORITES) "No Saved Favorites Yet"
                                    else "Your Journal is Empty",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = if (searchQuery.isNotBlank()) "Try searching for a different keyword or mood."
                                    else if (currentFilter == DiaryFilter.FAVORITES) "Star entries as favorite to see them listed here."
                                    else "Begin documenting your reflections, trips, and gratitude with the edit button below.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                if (searchQuery.isBlank() && currentFilter == DiaryFilter.ALL_ENTRIES && entries.isEmpty()) {
                                    Button(
                                        onClick = {
                                            scope.launch {
                                                repository.seedSampleEntries()
                                                snackbarHostState.showSnackbar("Sample sketch entries loaded")
                                            }
                                        },
                                        shape = RoundedCornerShape(28.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.primary
                                        )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.AutoAwesome,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Load Sketch Sample Entries")
                                    }
                                } else {
                                    OutlinedButton(
                                        onClick = { onNavigateToEditor(null) },
                                        shape = RoundedCornerShape(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.Add,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text("Write New Entry")
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Stacked List Items (72dp tall, 3dp gaps, 28dp outer & 8dp inner corners)
                    itemsIndexed(
                        items = displayedEntries,
                        key = { _, entry -> entry.id }
                    ) { index, entry ->
                        Box(modifier = Modifier.padding(bottom = 3.dp)) {
                            StackedDiaryItem(
                                entry = entry,
                                index = index,
                                totalCount = displayedEntries.size,
                                onClick = { onNavigateToEditor(entry.id) },
                                onFavoriteToggle = {
                                    scope.launch {
                                        repository.toggleFavorite(entry.id)
                                    }
                                },
                                onDelete = {
                                    scope.launch {
                                        repository.deleteEntry(entry.id)
                                        snackbarHostState.showSnackbar("Entry deleted")
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    // Dialogs
    if (showThemeDialog) {
        ThemeSelectionDialog(
            currentPalette = preferences.selectedTheme,
            onSelectPalette = { palette ->
                scope.launch {
                    preferencesRepository.updateTheme(palette)
                }
            },
            onDismiss = { showThemeDialog = false }
        )
    }

    if (showReminderDialog) {
        ReminderSettingsDialog(
            preferences = preferences,
            onSaveReminder = { enabled, hour, minute ->
                scope.launch {
                    preferencesRepository.updateReminder(enabled, hour, minute)
                }
            },
            onDismiss = { showReminderDialog = false }
        )
    }

    if (showRenameDialog) {
        RenameAppDialog(
            initialName = preferences.appName,
            initialHeading = preferences.appHeading,
            onSave = { name, heading ->
                scope.launch {
                    preferencesRepository.updateAppNameAndHeading(name, heading)
                }
            },
            onDismiss = { showRenameDialog = false }
        )
    }

    if (showLockDialog) {
        AppLockSettingsDialog(
            preferences = preferences,
            onSaveLock = { enabled, pin ->
                scope.launch {
                    preferencesRepository.setAppLock(enabled, pin)
                }
            },
            onDismiss = { showLockDialog = false }
        )
    }
}
