# M3Diary Release v1.0.0 🚀

**Release Date:** November 10, 2026  
**Artifact:** `M3Diary-v1.0.0-release.apk`  
**Size:** 12.78 MB (12,788,721 bytes)  
**SHA-256:** `32C18B79C24477268704B8A2D0C91CD84A94B48867F0AA064B7983759337EE5A`  
**Signature Scheme:** APK Signature Scheme v2 (Signed & Verified)  
**Minimum Android Version:** Android 7.0 (API level 24)  
**Target Android Version:** Android 15 (API level 36)  

---

## 🌟 Highlights & Features

### 🎨 Material 3 Expressive Design System
- Built from the ground up strictly targeting the latest **Material 3 Expressive** guidelines.
- Dynamic semantic color role theming with 5 expressive palettes:
  - **M3 Purple** (Default `#6750A4`)
  - **Ocean Blue** (`#0061A4`)
  - **Botanical Emerald** (`#006C4C`)
  - **Berry Rose** (`#9C4146`)
  - **Warm Amber** (`#865300`)
- Expressive shape scales (pill buttons, 20dp cards, 28dp modal dialogs, 16dp text fields and FABs).
- Smooth standard motion (`MotionScheme.standard()`) with non-bouncing transitions and press-scale ripple feedback.
- 120Hz/90Hz high refresh rate display synchronization enabled.

### ✍️ Daily Journaling & Thought Capture
- **Home Screen**: Stacked diary items with 28dp outer / 8dp inner corner radius, leading category icons on 40dp circles, headlines, supporting snippets, and search filtering.
- **Connected Button Group**: Filter quickly between "Favorites" (filled pill) and "All Entries" (tonal pill).
- **Auto-fetching Date & Time**: Initializes new reflections with exact current date and time with one-tap M3 pickers.
- **Responsive 2×2 Mood Selector**: Intuitive emotional state selection (`😊 Happy`, `😌 Calm`, `🤔 Reflective`, `⚡ Energetic`).
- **Media Attachments**: Capture photos via camera intent or import from device gallery; embedded gallery with zoomable dialog and delete options.
- **Word & Character Counters**: Real-time stats while composing.

### 📊 Mood Analytics & Wellbeing Dashboard
- **Timeframe Filtering**: Filter by "All Time", "Past 30 Days", or "This Week".
- **Hero Wellbeing Card**: Total reflections, dominant emotional mood, mindful journaling streak, and positive mindset ratio.
- **7-Day Mood Trail**: Weekly matrix charting daily emotions.
- **Interactive Distribution**: Progress bars with counts and percentages, plus click-to-filter reflections.
- **Mindful Insights**: Actionable, personalized journaling advice based on predominant mood.

### 📅 Calendar Screen
- Monthly calendar grid with mood indicators on days with reflections.
- One-tap date selection and quick reflection creation for any selected day.

### 🗂️ Working Navigation Sidebar (Drawer)
- Sliding M3 Drawer with custom branding, quick stats, navigation destinations, and quick tool shortcuts.

### ⏰ Daily Mindful Reminders
- Scheduled via Android's `AlarmManager` and `NotificationManager` with Android 13+ runtime permission support.
- User-selectable reminder time with M3 `TimePicker`.
- Test notification button for immediate status bar verification.

### 🔒 App Lock & PIN Security
- 4-digit PIN security with an expressive numeric keypad, circular PIN indicators, and "Forgot PIN" reset safeguarding.
- "Lock App Now" instant lock action.

### 🛡️ Local-First Privacy Sandbox
- 100% private, atomic JSON persistence in device internal storage (`filesDir/diary_entries.json`).
- Zero telemetry, zero analytics tracking, zero third-party cloud requirements.
- Full diary export to clipboard and non-destructive sample loading.
