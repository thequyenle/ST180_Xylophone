package com.xylophone.learning.music.data.model

object SongLibrary {

    fun getallSongs(): List<Song> {
        return listOf(
            // EASY SONGS
            Song(
                id = "mary_lamb",
                name = "Mary Had a Little Lamb",
                notes = listOf("Mi", "Re", "Do", "Re", "Mi", "Mi", "Mi", "Re", "Re", "Re", "Mi", "Sol", "Sol"),
                description = "Classic nursery rhyme"
            ),
            Song(
                id = "hot_cross_buns",
                name = "Hot Cross Buns",
                notes = listOf("Mi", "Re", "Do", "Mi", "Re", "Do", "Do", "Do", "Do", "Do", "Re", "Re", "Re", "Re", "Mi", "Re", "Do"),
                description = "Simple and easy song"
            ),
            Song(
                id = "happy_birthday",
                name = "Happy Birthday",
                notes = listOf("Do", "Do", "Re", "Do", "Fa", "Mi", "Do", "Do", "Re", "Do", "Sol", "Fa"),
                description = "Birthday celebration song"
            ),

            // MEDIUM SONGS
            Song(
                id = "twinkle_star",
                name = "Twinkle Twinkle Little Star",
                notes = listOf("Do", "Do", "Sol", "Sol", "La", "La", "Sol",
                              "Fa", "Fa", "Mi", "Mi", "Re", "Re", "Do"),
                description = "Famous lullaby"
            ),
            Song(
                id = "jingle_bells",
                name = "Jingle Bells",
                notes = listOf("Mi", "Mi", "Mi", "Mi", "Mi", "Mi", "Mi", "Sol", "Do", "Re", "Mi"),
                description = "Christmas classic"
            ),
            Song(
                id = "old_macdonald",
                name = "Old MacDonald",
                notes = listOf("Do", "Do", "Do", "Sol", "La", "La", "Sol",
                              "Mi", "Mi", "Re", "Re", "Do"),
                description = "Farm animal song"
            ),

            // HARD SONGS
            Song(
                id = "ode_to_joy",
                name = "Ode to Joy",
                notes = listOf("Mi", "Mi", "Fa", "Sol", "Sol", "Fa", "Mi", "Re",
                              "Do", "Do", "Re", "Mi", "Mi", "Re", "Re"),
                description = "Beethoven's masterpiece"
            ),
            Song(
                id = "frere_jacques",
                name = "Frère Jacques",
                notes = listOf("Do", "Re", "Mi", "Do", "Do", "Re", "Mi", "Do",
                              "Mi", "Fa", "Sol", "Mi", "Fa", "Sol"),
                description = "French folk song"
            ),
            Song(
                id = "london_bridge",
                name = "London Bridge",
                notes = listOf("Sol", "La", "Sol", "Fa", "Mi", "Fa", "Sol",
                              "Re", "Mi", "Fa", "Mi", "Fa", "Sol"),
                description = "Traditional nursery rhyme"
            ),
            Song(
                id = "when_the_saints",
                name = "When the Saints Go Marching In",
                notes = listOf(
                    "Mi","Mi","Mi","Fa","Sol",
                    "Mi","Mi","Fa","Sol","La",
                    "Sol","Fa","Mi","Re","Do",
                    "Mi","Fa","Sol","Fa","Mi","Re","Do"
                ),
                description = "Famous traditional song (simplified to fit 8 notes)"
            ),

            Song(
                id = "oh_susanna",
                name = "Oh! Susanna (Traditional)",
                notes = listOf(
                    "Do","Do","Re","Mi","Sol","Sol","Mi","Re","Do",
                    "Mi","Sol","La","Sol","Mi","Re","Do"
                ),
                description = "Classic folk tune (simplified)"
            ),

            Song(
                id = "auld_lang_syne",
                name = "Auld Lang Syne",
                notes = listOf(
                    "Do","Fa","Fa","Mi","Fa","Sol",
                    "La","La","Sol","Fa","Mi","Do",
                    "Do","Mi","Re","Do","Re","Mi","Fa","Mi","Re","Do"
                ),
                description = "Very well-known traditional melody (simplified)"
            ),

            Song(
                id = "jolly_good_fellow",
                name = "For He's a Jolly Good Fellow",
                notes = listOf(
                    "Sol","Sol","Sol","Mi","Fa","Sol",
                    "Sol","Fa","Mi","Re","Do",
                    "Sol","Sol","Sol","Mi","Fa","Sol",
                    "La","Sol","Fa","Mi","Re","Do"
                ),
                description = "Traditional celebration song (simplified)"
            ),

            Song(
                id = "skip_to_my_lou",
                name = "Skip to My Lou (Traditional)",
                notes = listOf(
                    "Do","Do","Do","Re","Mi",
                    "Mi","Re","Do","Mi","Sol",
                    "Sol","Mi","Re","Do",
                    "Re","Mi","Re","Do"
                ),
                description = "Traditional children’s song (simplified)"
            ),

            Song(
                id = "alouette",
                name = "Alouette (Traditional)",
                notes = listOf(
                    "Do","Do","Re","Mi","Fa","Fa","Mi","Re","Do",
                    "Mi","Fa","Sol","Sol","Fa","Mi","Re","Do"
                ),
                description = "Traditional French folk song (simplified)"
            ),

            Song(
                id = "the_muffin_man",
                name = "The Muffin Man (Traditional)",
                notes = listOf(
                    "Sol","Sol","La","Sol","Mi","Do",
                    "Re","Mi","Re","Do",
                    "Sol","Sol","La","Sol","Mi","Do2",
                    "Do2","Sol","Mi","Do"
                ),
                description = "Traditional nursery rhyme (uses Do2)"
            ),

// ORIGINAL (ROYALTY-FREE) - safest for commercial use
            Song(
                id = "dino_dance_original",
                name = "Dino Dance (Original)",
                notes = listOf(
                    "Do","Mi","Sol","Mi","Do",
                    "Re","Mi","Fa","Mi","Re","Do",
                    "Sol","Fa","Mi","Re","Do"
                ),
                description = "Original kid-friendly tune (royalty-free)"
            ),

            Song(
                id = "balloon_bounce_original",
                name = "Balloon Bounce (Original)",
                notes = listOf(
                    "Sol","Mi","Re","Do","Re","Mi",
                    "Sol","La","Sol","Fa","Mi","Re","Do",
                    "Do","Re","Mi","Re","Do"
                ),
                description = "Original upbeat melody (royalty-free)"
            ),

            Song(
                id = "sunny_steps_original",
                name = "Sunny Steps (Original)",
                notes = listOf(
                    "Do","Re","Mi","Fa","Sol","La","Si","Do2",
                    "Si","La","Sol","Fa","Mi","Re","Do"
                ),
                description = "Original scale-based tune (royalty-free)"
            )
        )
    }

    fun getSongById(id: String): Song? {
        return getallSongs().find { it.id == id }
    }


}
