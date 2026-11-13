# M3Diary v1.2.0 Release Notes

**Release Date:** November 13, 2026  
**Artifact:** `release/M3Diary-v1.2.0-release.apk`  
**Size:** 12.26 MB (12,854,513 bytes)  
**SHA-256 Checksum:** `B0ED943E5C01BCD860ED95D4C572A2E5E8C07F3EBB165E20E012C50409DC3CCB`  
**Signature Scheme:** APK Signature Scheme v2 (Signed)

---

## 🎙️ What's New in v1.2.0

### 1. Voice Audio Notes Recorder & Player
- **In-App Voice Recording:** Record personal audio memos directly inside any diary reflection.
- **High-Fidelity Audio:** Encoded using AAC in MPEG-4 (`.m4a`) container at 44.1 kHz, 128 kbps for crystal-clear clarity with minimal storage footprint.
- **Offline & Private:** Voice notes are saved directly in app-internal storage (`context.filesDir/entry_audio/`) with zero external cloud or server dependencies.
- **Full Playback Player:** Interactive audio playback in both **View-Only Reader Mode** and **Edit Mode** featuring:
  - Play / Pause toggle with animated iconography
  - Scrubbable progress slider
  - Real-time time formatting (`mm:ss`) for elapsed and total duration
  - Delete memo action with user confirmation
- **Runtime Microphone Permission:** Integrated runtime permission launcher (`android.permission.RECORD_AUDIO`) with graceful fallback.

---

## 🐛 Bug Fixes & Stability Improvements

### 2. Camera Button Crash Fix
- **Root Cause:** Launching `ActivityResultContracts.TakePicture()` without requesting `android.permission.CAMERA` at runtime triggered a `SecurityException` on Android 10+ devices. In addition, uncreated cache files could cause `FileProvider` exceptions.
- **Fix:** Added runtime camera permission checking, pre-created destination image files in internal storage, widened `file_paths.xml` paths, and added protective try/catch guards.

### 3. App Name & Heading Customization Persistence Fix
- **Root Cause:** Java's `File.renameTo()` silently returned `false` on filesystems where the destination preference file already existed, preventing new names from saving. Additionally, `RenameAppDialog` states did not re-initialize on open.
- **Fix:** Implemented safe atomic write-and-replace in `PreferencesRepository`, bound dialog fields with `remember(initialName, initialHeading)`, and added immediate UI snackbar feedback upon saving.

### 4. Layout & Button Overflow Fixes
- **TopAppBar Collision:** Replaced rigid box layouts in TopAppBar headers with flexible `Row` and `Modifier.weight(1f)` plus `TextOverflow.Ellipsis`, preventing back/edit button clipping on narrow devices.
- **Connected Button Group:** Replaced fixed-width paddings in `ConnectedButtonGroup` with proportional weights so "Favorites" and "All Entries" tabs fit seamlessly on all screen densities.
- **Photo & Audio Buttons:** Adaptive compact sizing to prevent multi-line height clipping.

---

## 📦 Verification & Installation
```bash
# Verify APK signature
apksigner verify --verbose M3Diary-v1.2.0-release.apk

# Install via ADB
adb install -r release/M3Diary-v1.2.0-release.apk
```
