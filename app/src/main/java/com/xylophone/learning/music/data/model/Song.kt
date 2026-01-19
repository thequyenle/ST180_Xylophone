package com.xylophone.learning.music.data.model

data class Song(
    val id: String,
    val name: String,
    val notes: List<String>,  // ["Do", "Re", "Mi", "Fa", "Sol", "La", "Si", "Do2"]
    val description: String = ""
)
