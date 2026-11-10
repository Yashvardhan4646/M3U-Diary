package com.example.drdiary.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.LockOpen
import androidx.compose.material.icons.rounded.Mood
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Title
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.drdiary.R
import com.example.drdiary.data.UserPreferences

@Composable
fun AppDrawerContent(
    preferences: UserPreferences,
    totalEntries: Int,
    currentScreenKey: String,
    onNavigateHome: () -> Unit,
    onNavigateMood: () -> Unit,
    onNavigateCalendar: () -> Unit,
    onNavigateSaved: () -> Unit,
    onNavigateSettings: () -> Unit,
    onOpenThemeDialog: () -> Unit,
    onOpenReminderDialog: () -> Unit,
    onOpenRenameDialog: () -> Unit,
    onOpenLockDialog: () -> Unit,
    onLockAppNow: () -> Unit,
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(
        modifier = modifier
            .width(320.dp)
            .fillMaxHeight(),
        drawerContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        drawerShape = RoundedCornerShape(topEnd = 28.dp, bottomEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            // Drawer Header with App Icon & Branding
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_icon),
                        contentDescription = "App Icon",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = preferences.appName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = preferences.appHeading,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Quick Stats Pill Card
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.EditNote,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Entries",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "$totalEntries recorded",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    if (preferences.isAppLockEnabled) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.secondaryContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Lock,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Locked",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            }
                        }
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )

            // Navigation Destinations
            Text(
                text = "DISCOVER",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )

            NavigationDrawerItem(
                label = { Text("My Diary") },
                selected = currentScreenKey == "home",
                onClick = {
                    onCloseDrawer()
                    onNavigateHome()
                },
                icon = { Icon(Icons.Rounded.Home, contentDescription = null) },
                shape = RoundedCornerShape(28.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )

            NavigationDrawerItem(
                label = { Text("Mood Analytics") },
                selected = currentScreenKey == "mood",
                onClick = {
                    onCloseDrawer()
                    onNavigateMood()
                },
                icon = { Icon(Icons.Rounded.Mood, contentDescription = null) },
                shape = RoundedCornerShape(28.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )

            NavigationDrawerItem(
                label = { Text("Calendar") },
                selected = currentScreenKey == "calendar",
                onClick = {
                    onCloseDrawer()
                    onNavigateCalendar()
                },
                icon = { Icon(Icons.Rounded.CalendarMonth, contentDescription = null) },
                shape = RoundedCornerShape(28.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    selectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )

            NavigationDrawerItem(
                label = { Text("Saved / Favorites") },
                selected = false,
                onClick = {
                    onCloseDrawer()
                    onNavigateSaved()
                },
                icon = { Icon(Icons.Rounded.Favorite, contentDescription = null) },
                shape = RoundedCornerShape(28.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unselectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
            )

            // Features & Tools
            Text(
                text = "CUSTOMIZATION & TOOLS",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )

            NavigationDrawerItem(
                label = { Text("Color Theme") },
                selected = false,
                onClick = {
                    onCloseDrawer()
                    onOpenThemeDialog()
                },
                icon = { Icon(Icons.Rounded.Palette, contentDescription = null) },
                badge = {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    )
                },
                shape = RoundedCornerShape(28.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )

            NavigationDrawerItem(
                label = { Text("Daily Reminders") },
                selected = false,
                onClick = {
                    onCloseDrawer()
                    onOpenReminderDialog()
                },
                icon = { Icon(Icons.Rounded.NotificationsActive, contentDescription = null) },
                badge = {
                    if (preferences.reminderEnabled) {
                        Text(
                            text = "${preferences.reminderHour}:${preferences.reminderMinute.toString().padStart(2, '0')}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                shape = RoundedCornerShape(28.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )

            NavigationDrawerItem(
                label = { Text("App Name & Heading") },
                selected = false,
                onClick = {
                    onCloseDrawer()
                    onOpenRenameDialog()
                },
                icon = { Icon(Icons.Rounded.Title, contentDescription = null) },
                shape = RoundedCornerShape(28.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )

            NavigationDrawerItem(
                label = { Text(if (preferences.isAppLockEnabled) "App Lock (Active)" else "App Lock") },
                selected = false,
                onClick = {
                    onCloseDrawer()
                    onOpenLockDialog()
                },
                icon = {
                    Icon(
                        imageVector = if (preferences.isAppLockEnabled) Icons.Rounded.Lock else Icons.Rounded.LockOpen,
                        contentDescription = null
                    )
                },
                shape = RoundedCornerShape(28.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )

            if (preferences.isAppLockEnabled) {
                NavigationDrawerItem(
                    label = { Text("Lock App Now") },
                    selected = false,
                    onClick = {
                        onCloseDrawer()
                        onLockAppNow()
                    },
                    icon = { Icon(Icons.Rounded.Lock, contentDescription = null, tint = MaterialTheme.colorScheme.error) },
                    shape = RoundedCornerShape(28.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedTextColor = MaterialTheme.colorScheme.error,
                        unselectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                    )
                )
            }

            NavigationDrawerItem(
                label = { Text("Settings") },
                selected = currentScreenKey == "settings",
                onClick = {
                    onCloseDrawer()
                    onNavigateSettings()
                },
                icon = { Icon(Icons.Rounded.Settings, contentDescription = null) },
                shape = RoundedCornerShape(28.dp),
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unselectedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(16.dp))

            // Footer
            Text(
                text = "M3 Expressive • High Refresh Rate Ready",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
    }
}
