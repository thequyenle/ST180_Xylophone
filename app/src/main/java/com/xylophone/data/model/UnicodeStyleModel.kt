package com.xylophone.data.model

data class UnicodeStyleModel(
    val displayName: String,
    val styleType: StyleType,
    val preview: String // Preview text với style applied
)

enum class StyleType {
    // Mathematical Alphanumeric (13)
    BOLD,
    ITALIC,
    BOLD_ITALIC,
    SCRIPT,
    BOLD_SCRIPT,
    FRAKTUR,
    BOLD_FRAKTUR,
    DOUBLE_STRUCK,
    SANS,
    SANS_BOLD,
    SANS_ITALIC,
    SANS_BOLD_ITALIC,
    MONOSPACE,

    // Enclosed Characters (5)
    CIRCLED,
    SQUARED,
    NEGATIVE_CIRCLED,
    NEGATIVE_SQUARED,
    PARENTHESIZED,

    // Special Unicode Blocks (5)
    FULLWIDTH,
    SMALL_CAPS,
    SUPERSCRIPT,
    SUBSCRIPT,
    INVERTED,

    // Basic Combining Characters (8)
    STRIKETHROUGH,
    UNDERLINE,
    OVERLINE,
    SLASH,
    DOTTED,
    DOUBLE_UNDERLINE,
    TILDE,
    RING_ABOVE,

    // More Combining Diacritics (8)
    DIAERESIS,
    ACUTE,
    GRAVE,
    CIRCUMFLEX,
    CARON,
    BREVE,
    MACRON,
    DOUBLE_ACUTE,

    // Mathematical Style + Underline (5)
    SCRIPT_UNDERLINE,
    FRAKTUR_UNDERLINE,
    MONOSPACE_UNDERLINE,
    SANS_BOLD_UNDERLINE,
    DOUBLE_STRUCK_UNDERLINE,

    // Bold/Italic Combinations (6)
    BOLD_UNDERLINE,
    ITALIC_UNDERLINE,
    BOLD_STRIKETHROUGH,
    BOLD_DOTTED,
    ITALIC_DOTTED,
    BOLD_TILDE,

    // Italic/Other Combinations (1)
    ITALIC_STRIKETHROUGH,

    // More Mathematical + Combining (7)
    BOLD_CIRCUMFLEX,
    ITALIC_CARON,
    SCRIPT_TILDE,
    MONOSPACE_STRIKETHROUGH,
    SANS_BOLD_STRIKETHROUGH,
    FRAKTUR_DOTTED,
    DOUBLE_STRUCK_STRIKETHROUGH,

    // Zalgo/Glitch (3)
    ZALGO_LIGHT,
    ZALGO_MEDIUM,
    ZALGO_HEAVY,

    // Decorative Brackets (15)
    SQUARE_BRACKETS,
    DOUBLE_BRACKETS,
    CURLY_BRACKETS,
    WHITE_BRACKETS,
    TORTOISE_BRACKETS,
    ANGLE_BRACKETS,
    DOUBLE_ANGLE_BRACKETS,
    CORNER_BRACKETS,
    FLOOR_BRACKETS,
    PARENTHESES,
    SQUARE_PARENTHESES,
    CURLY_PARENTHESES,
    ARROW_BRACKETS,
    QUOTATION_MARKS,
    SINGLE_QUOTES,

    // Emoji Decorations (5)
    STARS,
    HEARTS,
    SPARKLES,
    CROWN,
    FLOWERS,

    // Arrow Decorations (5)
    ARROWS_LEFT,
    ARROWS_RIGHT,
    ARROWS_BOTH,
    DOUBLE_ARROWS,
    TRIANGLE_ARROWS,

    // Box Drawing (4)
    BOX_SINGLE,
    BOX_DOUBLE,
    BOX_ROUNDED,
    BOX_HEAVY,

    // Block Backgrounds (4)
    BLOCK_LIGHT,
    BLOCK_MEDIUM,
    BLOCK_HEAVY,
    BLOCK_FULL,

    // Character Substitution / Lookalike (7)
    CYRILLIC_LOOKALIKE,
    GREEK_LOOKALIKE,
    ASIAN_MIX,
    MATH_SYMBOLS,
    WEIRD_MIX,
    SQUARED_NEGATIVE_LOOKALIKE,
    CURRENCY_MIX,

    // Mixed / Random Styles (13)
    MIXED_LIGHT,
    MIXED_MEDIUM,
    MIXED_HEAVY,
    ALTERNATING_CASE,
    RANDOM_CASE,
    MIXED_SUBSTITUTION,
    ALTERNATING_STYLES,
    WAVE_STYLE,
    BUBBLE_TEXT,
    FANCY_MIX,
    AESTHETIC,
    VAPORWAVE,
    GLITCH_MIX,
    CRAZY_MIX,

    // Mixed Script Styles (15)
    ARMENIAN,
    THAI_LAO_MIX,
    JAPANESE_MIX,
    MEDIEVAL_LATIN,
    BOPOMOFO_CJK,
    YI_SYLLABLES,
    CANADIAN_ABORIGINAL,
    CHEROKEE,
    LISU,
    HEBREW_GREEK_MIX,
    GUJARATI_MIX,
    THAI_ARMENIAN_MIX,
    CYRILLIC_ARMENIAN_MIX,
    GREEK_CYRILLIC_MIX,
    LATIN_GREEK_VIETNAMESE_MIX,

    // IPA & Phonetic Styles (3)
    IPA_PHONETIC,
    IPA_EXTENDED,
    PHONETIC_EXTENSIONS,

    // Advanced Greek Variants (3)
    GREEK_EXTENDED,
    GREEK_COPTIC_MIX,
    GREEK_ARCHAIC,

    // Symbol Separators (6)
    SEPARATOR_N_ARY,
    SEPARATOR_APL,
    SEPARATOR_STAR,
    SEPARATOR_DOT,
    SEPARATOR_DIAMOND,
    SEPARATOR_CIRCLE,

    // Complex Combining Marks (8)
    COMBINING_INVERTED_BRIDGE,
    COMBINING_CANDRABINDU,
    COMBINING_ZIGZAG,
    COMBINING_ARROW,
    DOUBLE_STRUCK_MULTI_DIACRITICS,
    DIACRITICS_RANDOM_HEAVY,
    SUPERSCRIPT_DIACRITICS_MIX,
    SUBSCRIPT_DIACRITICS_MIX,

    // Mirrored & Flipped Styles (2)
    MIRRORED_REVERSED,
    UPSIDE_DOWN_MIRRORED,

    // Heavy Effects (3)
    ZALGO_ARROWS,
    GLITCH_HEAVY_MARKS,
    CHAOTIC_MIX
}
