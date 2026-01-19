package com.xylophone.learning.music.data.model

data class RecordedNote(
    val timestamp: Long,  // Thời điểm phát âm thanh (milliseconds)
    val noteId: Int,      // ID của nốt nhạc (R.raw.note_do, etc)
    val noteName: String  // Tên nốt (Do, Re, Mi, etc)
)
