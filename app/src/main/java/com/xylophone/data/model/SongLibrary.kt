package com.xylophone.data.model

object SongLibrary {

    fun getallSongs(): List<Song> {
        return listOf(
            // EASY SONGS
            Song(
                id = "mary_lamb",
                name = "Mary Had a Little Lamb",
                notes = listOf("Mi", "Re", "Do", "Re", "Mi", "Mi", "Mi", "Re", "Re", "Re", "Mi", "Sol", "Sol"),
                difficulty = Song.Difficulty.EASY,
                description = "Classic nursery rhyme"
            ),
            Song(
                id = "hot_cross_buns",
                name = "Hot Cross Buns",
                notes = listOf("Mi", "Re", "Do", "Mi", "Re", "Do", "Do", "Do", "Do", "Do", "Re", "Re", "Re", "Re", "Mi", "Re", "Do"),
                difficulty = Song.Difficulty.EASY,
                description = "Simple and easy song"
            ),
            Song(
                id = "happy_birthday",
                name = "Happy Birthday",
                notes = listOf("Do", "Do", "Re", "Do", "Fa", "Mi", "Do", "Do", "Re", "Do", "Sol", "Fa"),
                difficulty = Song.Difficulty.EASY,
                description = "Birthday celebration song"
            ),

            // MEDIUM SONGS
            Song(
                id = "twinkle_star",
                name = "Twinkle Twinkle Little Star",
                notes = listOf("Do", "Do", "Sol", "Sol", "La", "La", "Sol",
                              "Fa", "Fa", "Mi", "Mi", "Re", "Re", "Do"),
                difficulty = Song.Difficulty.MEDIUM,
                description = "Famous lullaby"
            ),
            Song(
                id = "jingle_bells",
                name = "Jingle Bells",
                notes = listOf("Mi", "Mi", "Mi", "Mi", "Mi", "Mi", "Mi", "Sol", "Do", "Re", "Mi"),
                difficulty = Song.Difficulty.MEDIUM,
                description = "Christmas classic"
            ),
            Song(
                id = "old_macdonald",
                name = "Old MacDonald",
                notes = listOf("Do", "Do", "Do", "Sol", "La", "La", "Sol",
                              "Mi", "Mi", "Re", "Re", "Do"),
                difficulty = Song.Difficulty.MEDIUM,
                description = "Farm animal song"
            ),

            // HARD SONGS
            Song(
                id = "ode_to_joy",
                name = "Ode to Joy",
                notes = listOf("Mi", "Mi", "Fa", "Sol", "Sol", "Fa", "Mi", "Re",
                              "Do", "Do", "Re", "Mi", "Mi", "Re", "Re"),
                difficulty = Song.Difficulty.HARD,
                description = "Beethoven's masterpiece"
            ),
            Song(
                id = "frere_jacques",
                name = "Frère Jacques",
                notes = listOf("Do", "Re", "Mi", "Do", "Do", "Re", "Mi", "Do",
                              "Mi", "Fa", "Sol", "Mi", "Fa", "Sol"),
                difficulty = Song.Difficulty.HARD,
                description = "French folk song"
            ),
            Song(
                id = "london_bridge",
                name = "London Bridge",
                notes = listOf("Sol", "La", "Sol", "Fa", "Mi", "Fa", "Sol",
                              "Re", "Mi", "Fa", "Mi", "Fa", "Sol"),
                difficulty = Song.Difficulty.HARD,
                description = "Traditional nursery rhyme"
            )
        )
    }

    fun getSongById(id: String): Song? {
        return getallSongs().find { it.id == id }
    }

    fun getSongsByDifficulty(difficulty: Song.Difficulty): List<Song> {
        return getallSongs().filter { it.difficulty == difficulty }
    }
}
