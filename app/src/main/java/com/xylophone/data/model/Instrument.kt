package com.xylophone.data.model

import com.xylophone.R

enum class Instrument(
    val displayName: String,
    val soundPrefix: String,
    val icon: Int,
    val volumeBoost: Float  // Volume multiplier để normalize sounds
) {
    PIANO("Piano", "note_", R.drawable.piano_selected, 1.0f),
    XYLOPHONE("Xylophone", "_xylo", R.drawable.xylophone_selected, 1.0f),  // Boost 20%
    GUITAR("Guitar", "_guitar", R.drawable.guitar_selected, 1.0f);  // Boost 30%

    fun getSoundResource(noteName: String): Int {
        return when (this) {
            PIANO -> {
                when (noteName.lowercase()) {
                    "do" -> R.raw.note_do
                    "re" -> R.raw.note_re
                    "mi" -> R.raw.note_mi
                    "fa" -> R.raw.note_fa
                    "sol" -> R.raw.note_sol
                    "la" -> R.raw.note_la
                    "si" -> R.raw.note_si
                    "do2" -> R.raw.note_do2
                    else -> 0
                }
            }
            XYLOPHONE -> {
                when (noteName.lowercase()) {
                    "do" -> R.raw.do_xylo
                    "re" -> R.raw.re_xylo
                    "mi" -> R.raw.mi_xylo
                    "fa" -> R.raw.fa_xylo
                    "sol" -> R.raw.sol_xylo
                    "la" -> R.raw.la_xylo
                    "si" -> R.raw.si_xylo
                    "do2" -> R.raw.do2_xylo
                    else -> 0
                }
            }
            GUITAR -> {
                when (noteName.lowercase()) {
                    "do" -> R.raw.do_guitar
                    "re" -> R.raw.re_guitar
                    "mi" -> R.raw.mi_guitar
                    "fa" -> R.raw.fa_guitar
                    "sol" -> R.raw.sol_guitar
                    "la" -> R.raw.la_guitar
                    "si" -> R.raw.si_guitar
                    "do2" -> R.raw.do2_guitar
                    else -> 0
                }
            }
        }
    }

    companion object {
        fun fromName(name: String): Instrument {
            return values().find { it.name == name } ?: PIANO
        }
    }
}
