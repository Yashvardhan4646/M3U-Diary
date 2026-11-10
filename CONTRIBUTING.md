# Contributing to M3Diary

Thank you for your interest in contributing to **M3Diary**! We welcome bug reports, feature suggestions, architectural improvements, and code contributions from everyone.

---

## 🛠️ Development Setup

### Prerequisites
1. **JDK 17 or higher** (e.g. Eclipse Temurin 17)
2. **Android SDK Platform 36** with **Build-Tools 36.0.0**
3. **Android Studio Ladybug / Meerkat** (or modern CLI tools)

### Building the Project
```bash
# Clone the repository
git clone https://github.com/your-username/M3Diary.git
cd M3Diary

# Build and run local unit tests
./gradlew test

# Assemble debug build
./gradlew assembleDebug

# Assemble signed release build
./gradlew assembleRelease
```

---

## 🎨 Architectural & Design Guidelines

1. **Material 3 Expressive Design**:
   - Always reference colors through semantic `MaterialTheme.colorScheme.*` roles rather than hardcoded hex values.
   - Maintain pill-shaped buttons, 20dp cards, 28dp modal dialogs, and 16dp text field corners.
   - Follow `MotionScheme.standard()` non-bouncing transitions and provide `Modifier.pressScale` feedback on all tappable parts.

2. **Clean Jetpack Compose Architecture**:
   - Keep Composables stateless where feasible; hoist state to ViewModels or Repositories.
   - Use `StateFlow` and Kotlin Coroutines for asynchronous, non-blocking data operations.
   - Ensure list items provide stable `key` identifiers to maintain silky 120Hz scrolling performance.

3. **Privacy & Offline First**:
   - M3Diary is built on the principle of complete offline user privacy. All entry data must remain sandboxed on the local device (`context.filesDir`) unless the user explicitly requests an export.

---

## 📬 Submitting Changes

1. Fork the repository and create your feature branch:
   ```bash
   git checkout -b feature/mindful-prompt-enhancement
   ```
2. Commit your changes with descriptive commit messages following Conventional Commits (`feat: ...`, `fix: ...`, `docs: ...`).
3. Ensure all unit tests pass:
   ```bash
   ./gradlew test
   ```
4. Push to your branch and open a Pull Request!
