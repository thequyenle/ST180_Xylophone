package com.xylophone.data.model

data class Song(
    val id: String,
    val name: String,
    val notes: List<String>,  // ["Do", "Re", "Mi", "Fa", "Sol", "La", "Si", "Do2"]
    val difficulty: Difficulty,
    val description: String = ""
) {
    enum class Difficulty(val displayName: String, val color: Int) {
        EASY("Easy", 0xFF4CAF50.toInt()),
        MEDIUM("Medium", 0xFFFF9800.toInt()),
        HARD("Hard", 0xFFF44336.toInt())
    }

    fun getTotalNotes(): Int = notes.size
}
