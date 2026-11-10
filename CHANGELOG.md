# Changelog

All notable changes to **M3Diary** are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.0.0] - 2026-11-10

### Added
- **Material 3 Expressive UI**: Complete Android native app designed with M3 Expressive tokens, pill buttons, 20dp cards, 28dp modal dialogs, and Roboto typography.
- **5 Complete Core Screens**:
  - `HomeScreen`: TopAppBar, connected button group (Favorites/All Entries), stacked list items, tonal FAB, bottom navigation bar.
  - `EntryEditorScreen`: Outlined title input, auto-fetched date & time badges, responsive 2×2 mood selector, photo attachments, category icons, multiline journal editor.
  - `MoodAnalyticsScreen`: Wellbeing hero card, 7-day mood matrix, distribution progress bars, mindful journaling advice.
  - `CalendarScreen`: Month switcher, day grid with mood indicators, day-specific reflections list.
  - `SettingsScreen`: Theme palette switcher, daily reminder configuration, 4-digit PIN app lock, export to clipboard, data management.
- **Working Navigation Drawer (Sidebar)**: Material 3 `ModalNavigationDrawer` with custom branding, statistics, and quick tool links.
- **Color Theme Switcher**: 5 expressive color palettes (M3 Purple, Ocean Blue, Botanical Emerald, Berry Rose, Warm Amber).
- **Daily Reminders**: Scheduled notifications via `AlarmManager` and `NotificationManager` with Android 13+ permission support.
- **App Lock Security**: 4-digit PIN lock screen with custom numeric keypad, indicator dots, and "Lock App Now" capability.
- **Media Attachments**: Camera intent (`FileProvider`) and gallery image picker (`ActivityResultContracts.GetContent`) with embedded photo preview.
- **High Refresh Rate Support**: Window display mode synchronization supporting 90Hz and 120Hz panels.
- **Custom App Branding**: User-customizable App Name and Heading.
- **Non-Destructive Sample Seed**: Append sample sketch entries without overwriting user reflections.
- **Privacy Sandbox**: Atomic JSON file persistence (`filesDir/diary_entries.json`), zero cloud tracking.
