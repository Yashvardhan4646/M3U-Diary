# Changelog

All notable changes to **M3Diary** are documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.1.0] - 2026-11-11

### Added
- **View-Only Reader Mode & Instant Edit**:
  - Viewing existing reflections or newly saved entries renders in a distraction-free reader layout.
  - Prominent title typography, mood emoji & description badge, category icon badge, metadata chips (formatted date, time, word count), and attached photo gallery with tap-to-zoom.
  - Prominent **Edit button** (`Icons.Rounded.Edit`) on the top-right app bar for one-tap transition into edit mode.
  - Copy reflection text action and entry deletion confirmation.
- **Guided Journaling Templates**:
  - 5 structured reflection prompts: `🌅 Morning Gratitude`, `🌙 Evening Reflection`, `🏛️ Daily Stoic`, `💡 Brain Dump`, and `🌿 Mindful Check-in`.
  - One-tap insertion into the journal content area.
- **"On This Day" Flashbacks**:
  - Hero memory card on `HomeScreen` highlighting past entries from previous years or earlier dates.
  - One-tap link directly into the View-Only reader mode.
- **Biometric Unlock (Fingerprint / Face ID)**:
  - Native biometric authentication (`BiometricPrompt` & `FingerprintManagerCompat`) supporting Android 9+ and legacy devices.
  - Visual fingerprint key on the lock screen keypad and configurable toggle in Settings.
- **Material 3 Dark Mode & AMOLED Pitch Black**:
  - 4 theme appearance modes: `System Default`, `Light Mode`, `Dark Mode`, and `Pitch Black (AMOLED)`.
  - True `#000000` deep black styling saving OLED battery life across all 5 color palettes.
- **App Switcher Privacy (`FLAG_SECURE`)**:
  - Toggle in Settings to mask app content in the recent apps multitask overview and block external screenshot capture.

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
