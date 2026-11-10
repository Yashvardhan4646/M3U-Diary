package com.example.drdiary

import com.example.drdiary.data.DiaryEntry
import com.example.drdiary.data.EntryIcon
import com.example.drdiary.data.Mood
import org.junit.Assert.assertEquals
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
}
