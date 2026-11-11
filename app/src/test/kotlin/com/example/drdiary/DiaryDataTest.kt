package com.example.drdiary

import com.example.drdiary.data.AppThemePalette
import com.example.drdiary.data.DiaryEntry
import com.example.drdiary.data.EntryIcon
import com.example.drdiary.data.Mood
import com.example.drdiary.data.ThemeMode
import com.example.drdiary.data.UserPreferences
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class DiaryDataTest {
    @Test
    fun testDiaryEntryCreation() {
        val entry = DiaryEntry(
            id = "test-1",
            title = "Today's Reflections",
            content = "Had a productive day working on new designs.",
            dateMillis = 1794304800000L,
            dateFormatted = "Nov 10, 2026",
            timeFormatted = "08:30 PM",
            mood = Mood.REFLECTIVE,
            isFavorite = true,
            icon = EntryIcon.INBOX
        )

        assertEquals("Today's Reflections", entry.title)
        assertTrue(entry.isFavorite)
        assertEquals(Mood.REFLECTIVE, entry.mood)
        assertEquals(EntryIcon.INBOX, entry.icon)
    }

    @Test
    fun testMoodEnum() {
        assertEquals("Happy", Mood.HAPPY.displayName)
        assertEquals("Calm", Mood.CALM.displayName)
        assertEquals("Reflective", Mood.REFLECTIVE.displayName)
        assertEquals("Energetic", Mood.ENERGETIC.displayName)
    }

    @Test
    fun testSeedMergePreservesExisting() {
        val existingEntry = DiaryEntry(
            id = "user-personal-entry-1",
            title = "My Secret Note",
            content = "This should never be deleted by sample loading.",
            dateMillis = 1794304800000L,
            dateFormatted = "Nov 10, 2026"
        )
        val list = mutableListOf(existingEntry)
        val samples = listOf(
            DiaryEntry(id = "sample-1", title = "Sample 1", content = "...", dateMillis = 100L, dateFormatted = "Nov 10, 2026"),
            DiaryEntry(id = "sample-2", title = "Sample 2", content = "...", dateMillis = 200L, dateFormatted = "Nov 10, 2026")
        )
        val existingIds = list.map { it.id }.toSet()
        val toAdd = samples.filterNot { it.id in existingIds }
        list.addAll(0, toAdd)

        assertEquals(3, list.size)
        assertTrue(list.any { it.id == "user-personal-entry-1" })
    }

    @Test
    fun testUserPreferencesSerialization() {
        val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }
        val prefs = UserPreferences(
            appName = "M3Diary",
            appHeading = "My Reflections",
            selectedTheme = AppThemePalette.OCEAN_BLUE,
            themeMode = ThemeMode.AMOLED,
            isAppLockEnabled = true,
            appLockPin = "1234",
            biometricUnlockEnabled = true,
            appSwitcherPrivacyEnabled = true
        )
        val serialized = json.encodeToString(prefs)
        val deserialized = json.decodeFromString<UserPreferences>(serialized)

        assertEquals("M3Diary", deserialized.appName)
        assertEquals(ThemeMode.AMOLED, deserialized.themeMode)
        assertEquals(AppThemePalette.OCEAN_BLUE, deserialized.selectedTheme)
        assertTrue(deserialized.biometricUnlockEnabled)
        assertTrue(deserialized.appSwitcherPrivacyEnabled)
    }

    @Test
    fun testFlashbackSelectionLogic() {
        val today = java.util.Calendar.getInstance()
        val todayMonth = today.get(java.util.Calendar.MONTH)
        val todayDay = today.get(java.util.Calendar.DAY_OF_MONTH)
        val todayYear = today.get(java.util.Calendar.YEAR)

        val oneYearAgoCal = java.util.Calendar.getInstance().apply {
            set(todayYear - 1, todayMonth, todayDay, 12, 0, 0)
        }

        val entries = listOf(
            DiaryEntry(id = "e1", title = "Today", content = "Today note", dateMillis = today.timeInMillis, dateFormatted = "Today"),
            DiaryEntry(id = "e2", title = "Flashback", content = "One year ago note", dateMillis = oneYearAgoCal.timeInMillis, dateFormatted = "1 Year Ago")
        )

        val anniversaryMatch = entries.find { e ->
            val cal = java.util.Calendar.getInstance().apply { timeInMillis = e.dateMillis }
            cal.get(java.util.Calendar.MONTH) == todayMonth &&
                    cal.get(java.util.Calendar.DAY_OF_MONTH) == todayDay &&
                    cal.get(java.util.Calendar.YEAR) < todayYear
        }

        assertNotNull(anniversaryMatch)
        assertEquals("e2", anniversaryMatch?.id)
        assertEquals("Flashback", anniversaryMatch?.title)
    }
}
