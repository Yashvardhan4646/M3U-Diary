# M3Diary v1.1.0 — View-Only Reader Mode, Biometrics & Expressive Dark Mode

**Release Date:** November 11, 2026  
**Package:** `com.example.drdiary`  
**License:** Apache 2.0  
**Target:** Android 7.0+ (API 24 to API 36)

---

## 🚀 What's New in v1.1.0

### 📖 View-Only Reader Mode with Instant Edit
- **Distraction-Free Journal Reader**: When viewing existing reflections or upon saving a new entry, reflections display in a beautiful, formatted Material 3 reader view.
- **Top-Right Edit Button**: A prominent edit icon button (`Icons.Rounded.Edit`) on the top right allows transitioning directly into edit mode at any time.
- **Rich Reflection Badges**: Displays formatted date & time chips, mood emoji and descriptive badge, category icon, real-time word count, and full-screen image gallery with tap-to-enlarge.
- **One-Tap Actions**: Copy entry text to clipboard or delete with confirmation.

### 🏛️ Guided Journaling Templates
- **Structured Prompts**: 5 built-in guided templates:
  - 🌅 **Morning Gratitude**: 3 daily gratitudes, intentions, and affirmation.
  - 🌙 **Evening Reflection**: Day's highlight, lessons learned, and improvements.
  - 🏛️ **Daily Stoic**: Dichotomy of control and handling challenges.
  - 💡 **Brain Dump**: Ideas, inspirations, and priorities.
  - 🌿 **Mindful Check-in**: Mind & body check-in and emotional awareness.
- **One-Tap Insertion**: Tapping any template automatically formats and inserts the questions into your diary.

### ✨ "On This Day" Memory Flashbacks
- **Anniversary & Memory Discovery**: Highlights reflections written on the same calendar date in past years or meaningful older memories.
- **Hero Feed Card**: Elegantly featured on the Home Screen with direct one-tap opening in the View-Only reader.

### 🔒 Biometric Security & App Switcher Privacy
- **Biometric Unlock**: Fingerprint and face ID authentication via Android's native `BiometricPrompt` and `FingerprintManagerCompat` with 4-digit PIN fallback.
- **App Switcher Privacy (`FLAG_SECURE`)**: Configurable toggle in Settings to hide/blur diary contents in Android's recent apps switcher and prevent external screenshot capture.

### 🌙 Dark Mode & AMOLED Pitch Black
- **4 Appearance Modes**:
  - System Default (syncs with device settings)
  - Light Mode
  - Dark Mode
  - Pitch Black (AMOLED) — true `#000000` deep black saving OLED battery.
- **5 Palette Support**: Seamless across M3 Purple, Ocean Blue, Botanical Emerald, Berry Rose, and Warm Amber.

---

## 📦 Verification & Checksums
- Built with Android Gradle Plugin 9.0.1, Kotlin 2.3.20, and Compose BOM 2026.03.01.
- Signed with release v2 signature scheme.
