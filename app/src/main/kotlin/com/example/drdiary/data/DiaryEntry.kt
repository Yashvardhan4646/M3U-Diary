package com.example.drdiary.data

import kotlinx.serialization.Serializable

@Serializable
enum class Mood(val displayName: String, val emoji: String, val description: String) {
    HAPPY("Happy", "😊", "Joyful, optimistic & energized"),
    CALM("Calm", "😌", "Peaceful, centered & relaxed"),
    REFLECTIVE("Reflective", "🤔", "Thoughtful, deep & mindful"),
    ENERGETIC("Energetic", "⚡", "Productive, motivated & eager")
}

@Serializable
enum class EntryIcon(val key: String) {
    INBOX("inbox"),
    STAR("star"),
    ARCHIVE("archive"),
    BOOK("book"),
    HEART("heart")
}

@Serializable
data class DiaryEntry(
    val id: String,
    val title: String,
    val content: String,
    val dateMillis: Long,
    val dateFormatted: String,
    val timeFormatted: String = "12:00 PM",
    val mood: Mood = Mood.REFLECTIVE,
    val isFavorite: Boolean = false,
    val icon: EntryIcon = EntryIcon.INBOX,
    val tags: List<String> = emptyList(),
    val imageUris: List<String> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
)
