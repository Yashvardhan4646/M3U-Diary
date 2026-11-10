package com.example.drdiary.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class DiaryRepository(private val context: Context) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true
    }

    private val file: File
        get() = File(context.filesDir, "diary_entries.json")

    private val _entries = MutableStateFlow<List<DiaryEntry>>(emptyList())
    val entries: StateFlow<List<DiaryEntry>> = _entries.asStateFlow()

    init {
        loadEntries()
    }

    private fun loadEntries() {
        scope.launch {
            try {
                if (file.exists()) {
                    val text = file.readText()
                    if (text.isNotBlank()) {
                        val parsed = json.decodeFromString<List<DiaryEntry>>(text)
                        _entries.value = parsed.sortedByDescending { it.dateMillis }
                        return@launch
                    }
                }
                _entries.value = emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                _entries.value = emptyList()
            }
        }
    }

    private suspend fun persistEntries(list: List<DiaryEntry>) = withContext(Dispatchers.IO) {
        try {
            val text = json.encodeToString(list)
            val tempFile = File(context.filesDir, "diary_entries.json.tmp")
            tempFile.writeText(text)
            tempFile.renameTo(file)
            _entries.value = list.sortedByDescending { it.dateMillis }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun saveEntry(entry: DiaryEntry) {
        val current = _entries.value.toMutableList()
        val index = current.indexOfFirst { it.id == entry.id }
        if (index >= 0) {
            current[index] = entry
        } else {
            current.add(0, entry)
        }
        persistEntries(current)
    }

    suspend fun deleteEntry(id: String) {
        val filtered = _entries.value.filterNot { it.id == id }
        persistEntries(filtered)
    }

    suspend fun toggleFavorite(id: String) {
        val updated = _entries.value.map {
            if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it
        }
        persistEntries(updated)
    }

    suspend fun clearAll() {
        persistEntries(emptyList())
    }

    suspend fun seedSampleEntries() {
        val samples = listOf(
            DiaryEntry(
                id = "sample-today-reflections",
                title = "Today's Reflections",
                content = "Had a productive day working on new designs. Explored Material 3 Expressive components and interactive prototypes. Felt great seeing the layouts come to life with proper typography and shapes.",
                dateMillis = 1794304800000L, // Nov 10, 2026
                dateFormatted = "Nov 10, 2026",
                timeFormatted = "08:30 PM",
                mood = Mood.REFLECTIVE,
                isFavorite = true,
                icon = EntryIcon.INBOX,
                tags = listOf("Design", "Productivity", "Work")
            ),
            DiaryEntry(
                id = "sample-weekend-trip",
                title = "Weekend Trip Ideas",
                content = "Notes on hiking trails and gear checklist. Check weather forecast for Mount Baldy or Pine Ridge. Pack water filter, extra layers, emergency kit, trail snacks, and lightweight stove.",
                dateMillis = 1794218400000L, // Nov 09, 2026
                dateFormatted = "Nov 09, 2026",
                timeFormatted = "02:15 PM",
                mood = Mood.ENERGETIC,
                isFavorite = true,
                icon = EntryIcon.STAR,
                tags = listOf("Travel", "Hiking", "Outdoors")
            ),
            DiaryEntry(
                id = "sample-gratitude-list",
                title = "Gratitude List",
                content = "Grateful for family, good health, and cozy autumn weather. Enjoyed a warm cup of herbal tea while listening to the rain outside. Peaceful moments like these bring so much perspective.",
                dateMillis = 1794132000000L, // Nov 08, 2026
                dateFormatted = "Nov 08, 2026",
                timeFormatted = "10:00 AM",
                mood = Mood.CALM,
                isFavorite = false,
                icon = EntryIcon.ARCHIVE,
                tags = listOf("Gratitude", "Autumn", "Peace")
            )
        )
        val current = _entries.value.toMutableList()
        val existingIds = current.map { it.id }.toSet()
        val toAdd = samples.filterNot { it.id in existingIds }
        if (toAdd.isNotEmpty()) {
            current.addAll(0, toAdd)
        } else {
            val freshSamples = samples.map { it.copy(id = UUID.randomUUID().toString()) }
            current.addAll(0, freshSamples)
        }
        persistEntries(current)
    }

    companion object {
        fun formatDate(millis: Long): String {
            val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
            return formatter.format(Date(millis))
        }

        fun formatTime(hour: Int, minute: Int): String {
            val isPm = hour >= 12
            val h = when {
                hour == 0 -> 12
                hour > 12 -> hour - 12
                else -> hour
            }
            val amPm = if (isPm) "PM" else "AM"
            return String.format(Locale.getDefault(), "%02d:%02d %s", h, minute, amPm)
        }
    }
}
