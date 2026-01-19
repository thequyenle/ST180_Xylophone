package com.xylophone.learning.music.core.utils

object UnicodeStyleConverter {

    // Unicode Mathematical Alphanumeric Symbols ranges
    private val normalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"

    // Bold (Mathematical Bold)
    private val boldChars = "𝐀𝐁𝐂𝐃𝐄𝐅𝐆𝐇𝐈𝐉𝐊𝐋𝐌𝐍𝐎𝐏𝐐𝐑𝐒𝐓𝐔𝐕𝐖𝐗𝐘𝐙𝐚𝐛𝐜𝐝𝐞𝐟𝐠𝐡𝐢𝐣𝐤𝐥𝐦𝐧𝐨𝐩𝐪𝐫𝐬𝐭𝐮𝐯𝐰𝐱𝐲𝐳𝟎𝟏𝟐𝟑𝟒𝟓𝟔𝟕𝟖𝟗"

    // Italic (Mathematical Italic)
    private val italicChars = "𝐴𝐵𝐶𝐷𝐸𝐹𝐺𝐻𝐼𝐽𝐾𝐿𝑀𝑁𝑂𝑃𝑄𝑅𝑆𝑇𝑈𝑉𝑊𝑋𝑌𝑍𝑎𝑏𝑐𝑑𝑒𝑓𝑔ℎ𝑖𝑗𝑘𝑙𝑚𝑛𝑜𝑝𝑞𝑟𝑠𝑡𝑢𝑣𝑤𝑥𝑦𝑧0123456789"

    // Bold Italic
    private val boldItalicChars = "𝑨𝑩𝑪𝑫𝑬𝑭𝑮𝑯𝑰𝑱𝑲𝑳𝑴𝑵𝑶𝑷𝑸𝑹𝑺𝑻𝑼𝑽𝑾𝑿𝒀𝒁𝒂𝒃𝒄𝒅𝒆𝒇𝒈𝒉𝒊𝒋𝒌𝒍𝒎𝒏𝒐𝒑𝒒𝒓𝒔𝒕𝒖𝒗𝒘𝒙𝒚𝒛0123456789"

    // Script (Mathematical Script)
    private val scriptChars = "𝒜𝐵𝒞𝒟𝐸𝐹𝒢𝐻𝐼𝒥𝒦𝐿𝑀𝒩𝒪𝒫𝒬𝑅𝒮𝒯𝒰𝒱𝒲𝒳𝒴𝒵𝒶𝒷𝒸𝒹𝑒𝒻𝑔𝒽𝒾𝒿𝓀𝓁𝓂𝓃𝑜𝓅𝓆𝓇𝓈𝓉𝓊𝓋𝓌𝓍𝓎𝓏0123456789"

    // Bold Script
    private val boldScriptChars = "𝓐𝓑𝓒𝓓𝓔𝓕𝓖𝓗𝓘𝓙𝓚𝓛𝓜𝓝𝓞𝓟𝓠𝓡𝓢𝓣𝓤𝓥𝓦𝓧𝓨𝓩𝓪𝓫𝓬𝓭𝓮𝓯𝓰𝓱𝓲𝓳𝓴𝓵𝓶𝓷𝓸𝓹𝓺𝓻𝓼𝓽𝓾𝓿𝔀𝔁𝔂𝔃0123456789"

    // Fraktur (Mathematical Fraktur)
    private val frakturChars = "𝔄𝔅ℭ𝔇𝔈𝔉𝔊ℌℑ𝔍𝔎𝔏𝔐𝔑𝔒𝔓𝔔ℜ𝔖𝔗𝔘𝔙𝔚𝔛𝔜ℨ𝔞𝔟𝔠𝔡𝔢𝔣𝔤𝔥𝔦𝔧𝔨𝔩𝔪𝔫𝔬𝔭𝔮𝔯𝔰𝔱𝔲𝔳𝔴𝔵𝔶𝔷0123456789"

    // Bold Fraktur
    private val boldFrakturChars = "𝕬𝕭𝕮𝕯𝕰𝕱𝕲𝕳𝕴𝕵𝕶𝕷𝕸𝕹𝕺𝕻𝕼𝕽𝕾𝕿𝖀𝖁𝖂𝖃𝖄𝖅𝖆𝖇𝖈𝖉𝖊𝖋𝖌𝖍𝖎𝖏𝖐𝖑𝖒𝖓𝖔𝖕𝖖𝖗𝖘𝖙𝖚𝖛𝖜𝖝𝖞𝖟0123456789"

    // Double-struck (Mathematical Double-struck)
    private val doubleStruckChars = "𝔸𝔹ℂ𝔻𝔼𝔽𝔾ℍ𝕀𝕁𝕂𝕃𝕄ℕ𝕆ℙℚℝ𝕊𝕋𝕌𝕍𝕎𝕏𝕐ℤ𝕒𝕓𝕔𝕕𝕖𝕗𝕘𝕙𝕚𝕛𝕜𝕝𝕞𝕟𝕠𝕡𝕢𝕣𝕤𝕥𝕦𝕧𝕨𝕩𝕪𝕫𝟘𝟙𝟚𝟛𝟜𝟝𝟞𝟟𝟠𝟡"

    // Sans-serif
    private val sansChars = "𝖠𝖡𝖢𝖣𝖤𝖥𝖦𝖧𝖨𝖩𝖪𝖫𝖬𝖭𝖮𝖯𝖰𝖱𝖲𝖳𝖴𝖵𝖶𝖷𝖸𝖹𝖺𝖻𝖼𝖽𝖾𝖿𝗀𝗁𝗂𝗃𝗄𝗅𝗆𝗇𝗈𝗉𝗊𝗋𝗌𝗍𝗎𝗏𝗐𝗑𝗒𝗓𝟢𝟣𝟤𝟥𝟦𝟧𝟨𝟩𝟪𝟫"

    // Sans-serif Bold
    private val sansBoldChars = "𝗔𝗕𝗖𝗗𝗘𝗙𝗚𝗛𝗜𝗝𝗞𝗟𝗠𝗡𝗢𝗣𝗤𝗥𝗦𝗧𝗨𝗩𝗪𝗫𝗬𝗭𝗮𝗯𝗰𝗱𝗲𝗳𝗴𝗵𝗶𝗷𝗸𝗹𝗺𝗻𝗼𝗽𝗾𝗿𝘀𝘁𝘂𝘃𝘄𝘅𝘆𝘇𝟬𝟭𝟮𝟯𝟰𝟱𝟲𝟳𝟴𝟵"

    // Sans-serif Italic
    private val sansItalicChars = "𝘈𝘉𝘊𝘋𝘌𝘍𝘎𝘏𝘐𝘑𝘒𝘓𝘔𝘕𝘖𝘗𝘘𝘙𝘚𝘛𝘜𝘝𝘞𝘟𝘠𝘡𝘢𝘣𝘤𝘥𝘦𝘧𝘨𝘩𝘪𝘫𝘬𝘭𝘮𝘯𝘰𝘱𝘲𝘳𝘴𝘵𝘶𝘷𝘸𝘹𝘺𝘻0123456789"

    // Sans-serif Bold Italic
    private val sansBoldItalicChars = "𝘼𝘽𝘾𝘿𝙀𝙁𝙂𝙃𝙄𝙅𝙆𝙇𝙈𝙉𝙊𝙋𝙌𝙍𝙎𝙏𝙐𝙑𝙒𝙓𝙔𝙕𝙖𝙗𝙘𝙙𝙚𝙛𝙜𝙝𝙞𝙟𝙠𝙡𝙢𝙣𝙤𝙥𝙦𝙧𝙨𝙩𝙪𝙫𝙬𝙭𝙮𝙯0123456789"

    // Monospace
    private val monospaceChars = "𝙰𝙱𝙲𝙳𝙴𝙵𝙶𝙷𝙸𝙹𝙺𝙻𝙼𝙽𝙾𝙿𝚀𝚁𝚂𝚃𝚄𝚅𝚆𝚇𝚈𝚉𝚊𝚋𝚌𝚍𝚎𝚏𝚐𝚑𝚒𝚓𝚔𝚕𝚖𝚗𝚘𝚙𝚚𝚛𝚜𝚝𝚞𝚟𝚠𝚡𝚢𝚣𝟶𝟷𝟸𝟹𝟺𝟻𝟼𝟽𝟾𝟿"

    // Circled
    private val circledChars = "ⒶⒷⒸⒹⒺⒻⒼⒽⒾⒿⓀⓁⓂⓃⓄⓅⓆⓇⓈⓉⓊⓋⓌⓍⓎⓏⒶⒷⒸⒹⒺⒻⒼⒽⒾⒿⓀⓁⓂⓃⓄⓅⓆⓇⓈⓉⓊⓋⓌⓍⓎⓏ⓪①②③④⑤⑥⑦⑧⑨"

    // Squared
    private val squaredChars = "🄰🄱🄲🄳🄴🄵🄶🄷🄸🄹🄺🄻🄼🄽🄾🄿🅀🅁🅂🅃🅄🅅🅆🅇🅈🅉🄰🄱🄲🄳🄴🄵🄶🄷🄸🄹🄺🄻🄼🄽🄾🄿🅀🅁🅂🅃🅄🅅🅆🅇🅈🅉0123456789"

    // Negative Circled
    private val negativeCircledChars = "🅐🅑🅒🅓🅔🅕🅖🅗🅘🅙🅚🅛🅜🅝🅞🅟🅠🅡🅢🅣🅤🅥🅦🅧🅨🅩🅐🅑🅒🅓🅔🅕🅖🅗🅘🅙🅚🅛🅜🅝🅞🅟🅠🅡🅢🅣🅤🅥🅦🅧🅨🅩⓿❶❷❸❹❺❻❼❽❾"

    // Negative Squared
    private val negativeSquaredChars = "🅰🅱🅲🅳🅴🅵🅶🅷🅸🅹🅺🅻🅼🅽🅾🅿🆀🆁🆂🆃🆄🆅🆆🆇🆈🆉🅰🅱🅲🅳🅴🅵🅶🅷🅸🅹🅺🅻🅼🅽🅾🅿🆀🆁🆂🆃🆄🆅🆆🆇🆈🆉0123456789"

    // Fullwidth Characters (U+FF00 block)
    private val fullwidthChars = "ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ０１２３４５６７８９"

    // Small Caps (IPA Extensions)
    private val smallCapsMap = mapOf(
        'A' to 'ᴀ', 'B' to 'ʙ', 'C' to 'ᴄ', 'D' to 'ᴅ', 'E' to 'ᴇ',
        'F' to 'ғ', 'G' to 'ɢ', 'H' to 'ʜ', 'I' to 'ɪ', 'J' to 'ᴊ',
        'K' to 'ᴋ', 'L' to 'ʟ', 'M' to 'ᴍ', 'N' to 'ɴ', 'O' to 'ᴏ',
        'P' to 'ᴘ', 'Q' to 'ǫ', 'R' to 'ʀ', 'S' to 's', 'T' to 'ᴛ',
        'U' to 'ᴜ', 'V' to 'ᴠ', 'W' to 'ᴡ', 'X' to 'x', 'Y' to 'ʏ', 'Z' to 'ᴢ',
        'a' to 'ᴀ', 'b' to 'ʙ', 'c' to 'ᴄ', 'd' to 'ᴅ', 'e' to 'ᴇ',
        'f' to 'ғ', 'g' to 'ɢ', 'h' to 'ʜ', 'i' to 'ɪ', 'j' to 'ᴊ',
        'k' to 'ᴋ', 'l' to 'ʟ', 'm' to 'ᴍ', 'n' to 'ɴ', 'o' to 'ᴏ',
        'p' to 'ᴘ', 'q' to 'ǫ', 'r' to 'ʀ', 's' to 's', 't' to 'ᴛ',
        'u' to 'ᴜ', 'v' to 'ᴠ', 'w' to 'ᴡ', 'x' to 'x', 'y' to 'ʏ', 'z' to 'ᴢ'
    )

    // Superscript
    private val superscriptMap = mapOf(
        'A' to 'ᴬ', 'B' to 'ᴮ', 'D' to 'ᴰ', 'E' to 'ᴱ', 'G' to 'ᴳ',
        'H' to 'ᴴ', 'I' to 'ᴵ', 'J' to 'ᴶ', 'K' to 'ᴷ', 'L' to 'ᴸ',
        'M' to 'ᴹ', 'N' to 'ᴺ', 'O' to 'ᴼ', 'P' to 'ᴾ', 'R' to 'ᴿ',
        'T' to 'ᵀ', 'U' to 'ᵁ', 'V' to 'ⱽ', 'W' to 'ᵂ',
        'a' to 'ᵃ', 'b' to 'ᵇ', 'c' to 'ᶜ', 'd' to 'ᵈ', 'e' to 'ᵉ',
        'f' to 'ᶠ', 'g' to 'ᵍ', 'h' to 'ʰ', 'i' to 'ⁱ', 'j' to 'ʲ',
        'k' to 'ᵏ', 'l' to 'ˡ', 'm' to 'ᵐ', 'n' to 'ⁿ', 'o' to 'ᵒ',
        'p' to 'ᵖ', 'r' to 'ʳ', 's' to 'ˢ', 't' to 'ᵗ', 'u' to 'ᵘ',
        'v' to 'ᵛ', 'w' to 'ʷ', 'x' to 'ˣ', 'y' to 'ʸ', 'z' to 'ᶻ',
        '0' to '⁰', '1' to '¹', '2' to '²', '3' to '³', '4' to '⁴',
        '5' to '⁵', '6' to '⁶', '7' to '⁷', '8' to '⁸', '9' to '⁹'
    )

    // Subscript
    private val subscriptMap = mapOf(
        'a' to 'ₐ', 'e' to 'ₑ', 'h' to 'ₕ', 'i' to 'ᵢ', 'j' to 'ⱼ',
        'k' to 'ₖ', 'l' to 'ₗ', 'm' to 'ₘ', 'n' to 'ₙ', 'o' to 'ₒ',
        'p' to 'ₚ', 'r' to 'ᵣ', 's' to 'ₛ', 't' to 'ₜ', 'u' to 'ᵤ',
        'v' to 'ᵥ', 'x' to 'ₓ',
        '0' to '₀', '1' to '₁', '2' to '₂', '3' to '₃', '4' to '₄',
        '5' to '₅', '6' to '₆', '7' to '₇', '8' to '₈', '9' to '₉'
    )

    // Inverted (Upside down)
    private val invertedMap = mapOf(
        'A' to '∀', 'B' to 'ᙠ', 'C' to 'Ɔ', 'D' to 'ᗡ', 'E' to 'Ǝ',
        'F' to 'Ⅎ', 'G' to '⅁', 'H' to 'H', 'I' to 'I', 'J' to 'ſ',
        'K' to 'ﻼ', 'L' to '˥', 'M' to 'W', 'N' to 'N', 'O' to 'O',
        'P' to 'Ԁ', 'Q' to 'Ὸ', 'R' to 'ᴚ', 'S' to 'S', 'T' to '⊥',
        'U' to '∩', 'V' to 'Λ', 'W' to 'M', 'X' to 'X', 'Y' to '⅄', 'Z' to 'Z',
        'a' to 'ɐ', 'b' to 'q', 'c' to 'ɔ', 'd' to 'p', 'e' to 'ǝ',
        'f' to 'ɟ', 'g' to 'ƃ', 'h' to 'ɥ', 'i' to 'ᴉ', 'j' to 'ɾ',
        'k' to 'ʞ', 'l' to 'ʃ', 'm' to 'ɯ', 'n' to 'u', 'o' to 'o',
        'p' to 'd', 'q' to 'b', 'r' to 'ɹ', 's' to 's', 't' to 'ʇ',
        'u' to 'n', 'v' to 'ʌ', 'w' to 'ʍ', 'x' to 'x', 'y' to 'ʎ', 'z' to 'z',
        '0' to '0', '1' to 'Ɩ', '2' to 'ᄅ', '3' to 'Ɛ', '4' to 'ㄣ',
        '5' to 'ϛ', '6' to '9', '7' to 'ㄥ', '8' to '8', '9' to '6'
    )

    // Parenthesized
    private val parenthesizedChars = "⒜⒝⒞⒟⒠⒡⒢⒣⒤⒥⒦⒧⒨⒩⒪⒫⒬⒭⒮⒯⒰⒱⒲⒳⒴⒵⒜⒝⒞⒟⒠⒡⒢⒣⒤⒥⒦⒧⒨⒩⒪⒫⒬⒭⒮⒯⒰⒱⒲⒳⒴⒵0123456789"

    // Regional Indicator Symbols (Flag letters)
    private val regionalIndicatorSymbols = "🇦🇧🇨🇩🇪🇫🇬🇭🇮🇯🇰🇱🇲🇳🇴🇵🇶🇷🇸🇹🇺🇻🇼🇽🇾🇿🇦🇧🇨🇩🇪🇫🇬🇭🇮🇯🇰🇱🇲🇳🇴🇵🇶🇷🇸🇹🇺🇻🇼🇽🇾🇿0123456789"

    // Combining diacritical marks for effects
    private const val COMBINING_OVERLINE = '\u0305'
    private const val COMBINING_UNDERLINE = '\u0332'
    private const val COMBINING_STRIKETHROUGH = '\u0336'
    private const val COMBINING_SLASH = '\u0338'
    private const val COMBINING_DOT_ABOVE = '\u0307'
    private const val COMBINING_DOUBLE_UNDERLINE = '\u0333'
    private const val COMBINING_TILDE = '\u0303'
    private const val COMBINING_RING_ABOVE = '\u030A'
    private const val COMBINING_DIAERESIS = '\u0308'
    private const val COMBINING_ACUTE = '\u0301'
    private const val COMBINING_GRAVE = '\u0300'
    private const val COMBINING_CIRCUMFLEX = '\u0302'
    private const val COMBINING_CARON = '\u030C'
    private const val COMBINING_BREVE = '\u0306'
    private const val COMBINING_MACRON = '\u0304'
    private const val COMBINING_DOUBLE_ACUTE = '\u030B'
    private const val COMBINING_COMMA_ABOVE = '\u0313'
    private const val COMBINING_X_ABOVE = '\u033D'
    private const val COMBINING_VERTICAL_LINE_ABOVE = '\u030D'

    // Zalgo/Glitch combining marks
    private val zalgoMarksUp = listOf('\u030D', '\u030E', '\u0304', '\u0305', '\u033F', '\u0311', '\u0306', '\u0310', '\u0352', '\u0357', '\u0351', '\u0307', '\u0308', '\u030A', '\u0342', '\u0343', '\u0344', '\u034A', '\u034B', '\u034C', '\u0303', '\u0302', '\u030C', '\u0350', '\u0300', '\u0301', '\u030B', '\u030F', '\u0312', '\u0313', '\u0314', '\u033D', '\u0309', '\u0363', '\u0364', '\u0365', '\u0366', '\u0367', '\u0368', '\u0369', '\u036A', '\u036B', '\u036C', '\u036D', '\u036E', '\u036F', '\u033E', '\u035B', '\u0346', '\u031A')
    private val zalgoMarksDown = listOf('\u0316', '\u0317', '\u0318', '\u0319', '\u031C', '\u031D', '\u031E', '\u031F', '\u0320', '\u0324', '\u0325', '\u0326', '\u0329', '\u032A', '\u032B', '\u032C', '\u032D', '\u032E', '\u032F', '\u0330', '\u0331', '\u0332', '\u0333', '\u0339', '\u033A', '\u033B', '\u033C', '\u0345', '\u0347', '\u0348', '\u0349', '\u034D', '\u034E', '\u0353', '\u0354', '\u0355', '\u0356', '\u0359', '\u035A', '\u0323')

    fun toBold(text: String): String = convertStyle(text, boldChars)

    fun toItalic(text: String): String = convertStyle(text, italicChars)

    fun toBoldItalic(text: String): String = convertStyle(text, boldItalicChars)

    fun toScript(text: String): String = convertStyle(text, scriptChars)

    fun toBoldScript(text: String): String = convertStyle(text, boldScriptChars)

    fun toFraktur(text: String): String = convertStyle(text, frakturChars)

    fun toBoldFraktur(text: String): String = convertStyle(text, boldFrakturChars)

    fun toDoubleStruck(text: String): String = convertStyle(text, doubleStruckChars)

    fun toSans(text: String): String = convertStyle(text, sansChars)

    fun toSansBold(text: String): String = convertStyle(text, sansBoldChars)

    fun toSansItalic(text: String): String = convertStyle(text, sansItalicChars)

    fun toSansBoldItalic(text: String): String = convertStyle(text, sansBoldItalicChars)

    fun toMonospace(text: String): String = convertStyle(text, monospaceChars)

    fun toCircled(text: String): String = convertStyle(text, circledChars)

    fun toSquared(text: String): String = convertStyle(text, squaredChars)

    fun toNegativeCircled(text: String): String = convertStyle(text, negativeCircledChars)

    fun toNegativeSquared(text: String): String = convertStyle(text, negativeSquaredChars)

    fun toStrikethrough(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_STRIKETHROUGH
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toUnderline(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_UNDERLINE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toOverline(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_OVERLINE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toSlash(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_SLASH
                }
            }
            .toArray()
            .joinToString("")
    }

    // New styles
    fun toFullwidth(text: String): String = convertStyle(text, fullwidthChars)

    fun toSmallCaps(text: String): String {
        return text.map { smallCapsMap[it] ?: it }.joinToString("")
    }

    fun toSuperscript(text: String): String {
        return text.map { superscriptMap[it] ?: it }.joinToString("")
    }

    fun toSubscript(text: String): String {
        return text.map { subscriptMap[it] ?: it }.joinToString("")
    }

    fun toInverted(text: String): String {
        return text.map { invertedMap[it] ?: it }.joinToString("").reversed()
    }

    fun toParenthesized(text: String): String = convertStyle(text, parenthesizedChars)

    fun toRegionalIndicator(text: String): String = convertStyle(text, regionalIndicatorSymbols)

    fun toDotted(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_DOT_ABOVE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toDoubleUnderline(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_DOUBLE_UNDERLINE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toTilde(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_TILDE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toRingAbove(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_RING_ABOVE
                }
            }
            .toArray()
            .joinToString("")
    }

    // Combination styles
    fun toBoldUnderline(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toBold(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_UNDERLINE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toItalicUnderline(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toItalic(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_UNDERLINE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toBoldStrikethrough(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toBold(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_STRIKETHROUGH
                }
            }
            .toArray()
            .joinToString("")
    }

    // More combining diacritics
    fun toDiaeresis(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_DIAERESIS
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toAcute(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_ACUTE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toGrave(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_GRAVE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toCircumflex(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_CIRCUMFLEX
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toCaron(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_CARON
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toBreve(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_BREVE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toMacron(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_MACRON
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toDoubleAcute(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_DOUBLE_ACUTE
                }
            }
            .toArray()
            .joinToString("")
    }

    // More mathematical combinations
    fun toScriptUnderline(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toScript(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_UNDERLINE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toFrakturUnderline(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toFraktur(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_UNDERLINE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toMonospaceUnderline(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toMonospace(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_UNDERLINE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toSansBoldUnderline(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toSansBold(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_UNDERLINE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toDoubleStruckUnderline(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toDoubleStruck(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_UNDERLINE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toBoldDotted(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toBold(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_DOT_ABOVE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toItalicDotted(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toItalic(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_DOT_ABOVE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toBoldTilde(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toBold(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_TILDE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toItalicStrikethrough(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toItalic(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_STRIKETHROUGH
                }
            }
            .toArray()
            .joinToString("")
    }

    // Zalgo/Glitch text variations
    fun toZalgoLight(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    val marks = (0..1).map { zalgoMarksUp.random() }.joinToString("")
                    String(intArrayOf(codePoint), 0, 1) + marks
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toZalgoMedium(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    val marksUp = (0..2).map { zalgoMarksUp.random() }.joinToString("")
                    val marksDown = (0..1).map { zalgoMarksDown.random() }.joinToString("")
                    String(intArrayOf(codePoint), 0, 1) + marksUp + marksDown
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toZalgoHeavy(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    val marksUp = (0..3).map { zalgoMarksUp.random() }.joinToString("")
                    val marksDown = (0..3).map { zalgoMarksDown.random() }.joinToString("")
                    String(intArrayOf(codePoint), 0, 1) + marksUp + marksDown
                }
            }
            .toArray()
            .joinToString("")
    }

    // Decorative wrappers
    fun toSquareBrackets(text: String): String = "「$text」"
    fun toDoubleBrackets(text: String): String = "『$text』"
    fun toCurlyBrackets(text: String): String = "【$text】"
    fun toWhiteBrackets(text: String): String = "〖$text〗"
    fun toTortoiseBrackets(text: String): String = "〘$text〙"
    fun toAngleBrackets(text: String): String = "⟨$text⟩"
    fun toDoubleAngleBrackets(text: String): String = "《$text》"
    fun toCornerBrackets(text: String): String = "⌈$text⌉"
    fun toFloorBrackets(text: String): String = "⌊$text⌋"
    fun toParentheses(text: String): String = "($text)"
    fun toSquareParentheses(text: String): String = "[$text]"
    fun toCurlyParentheses(text: String): String = "{$text}"
    fun toArrowBrackets(text: String): String = "⸤$text⸥"
    fun toQuotationMarks(text: String): String = "\"$text\""
    fun toSingleQuotes(text: String): String = "'$text'"

    // Star/Heart decorations
    fun toStars(text: String): String = "✨ $text ✨"
    fun toHearts(text: String): String = "💖 $text 💖"
    fun toSparkles(text: String): String = "⭐ $text ⭐"
    fun toCrown(text: String): String = "👑 $text 👑"
    fun toFlowers(text: String): String = "🌸 $text 🌸"

    // Arrow decorations
    fun toArrowsLeft(text: String): String = "← $text"
    fun toArrowsRight(text: String): String = "$text →"
    fun toArrowsBoth(text: String): String = "← $text →"
    fun toDoubleArrows(text: String): String = "⇐ $text ⇒"
    fun toTriangleArrows(text: String): String = "◄ $text ►"

    // Box drawing
    fun toBoxSingle(text: String): String = "┌─$text─┐"
    fun toBoxDouble(text: String): String = "╔═$text═╗"
    fun toBoxRounded(text: String): String = "╭─$text─╮"
    fun toBoxHeavy(text: String): String = "┏━$text━┓"

    // Block backgrounds
    fun toBlockLight(text: String): String {
        return text.map { if (it.isWhitespace()) " " else "░" }.joinToString("") + " $text"
    }

    fun toBlockMedium(text: String): String {
        return text.map { if (it.isWhitespace()) " " else "▒" }.joinToString("") + " $text"
    }

    fun toBlockHeavy(text: String): String {
        return text.map { if (it.isWhitespace()) " " else "▓" }.joinToString("") + " $text"
    }

    fun toBlockFull(text: String): String {
        return text.map { if (it.isWhitespace()) " " else "█" }.joinToString("") + " $text"
    }

    // More style combinations with new combining marks
    fun toBoldCircumflex(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toBold(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_CIRCUMFLEX
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toItalicCaron(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toItalic(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_CARON
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toScriptTilde(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toScript(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_TILDE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toMonospaceStrikethrough(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toMonospace(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_STRIKETHROUGH
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toSansBoldStrikethrough(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toSansBold(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_STRIKETHROUGH
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toFrakturDotted(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toFraktur(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_DOT_ABOVE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toDoubleStruckStrikethrough(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toDoubleStruck(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_STRIKETHROUGH
                }
            }
            .toArray()
            .joinToString("")
    }

    private fun convertStyle(text: String, styledChars: String): String {
        // Use codePoints() to handle surrogate pairs correctly (Mathematical Unicode > U+FFFF)
        val normalCodePoints = normalChars.codePoints().toArray()
        val styledCodePoints = styledChars.codePoints().toArray()

        return text.codePoints()
            .map { codePoint ->
                val index = normalCodePoints.indexOf(codePoint)
                if (index != -1 && index < styledCodePoints.size) {
                    styledCodePoints[index]
                } else {
                    codePoint
                }
            }
            .toArray()
            .let { String(it, 0, it.size) }
    }

    // ==================== CHARACTER SUBSTITUTION / LOOKALIKE STYLES ====================

    // Cyrillic Lookalike - thay bằng ký tự Cyrillic trông giống Latin
    private val cyrillicLookalike = mapOf(
        'A' to 'А', 'B' to 'В', 'C' to 'С', 'E' to 'Е', 'H' to 'Н', 'I' to 'І', 'J' to 'Ј',
        'K' to 'К', 'M' to 'М', 'N' to 'И', 'O' to 'О', 'P' to 'Р', 'S' to 'Ѕ', 'T' to 'Т',
        'X' to 'Х', 'Y' to 'Ү', 'Z' to 'Ζ',
        'a' to 'а', 'c' to 'с', 'e' to 'є', 'o' to 'о', 'p' to 'р', 'x' to 'х', 'y' to 'у',
        'b' to 'ь', 'd' to 'ԁ', 'h' to 'һ', 'i' to 'і', 'j' to 'ј', 'k' to 'к', 's' to 'ѕ',
        't' to 'т', 'v' to 'ѵ', 'w' to 'ԝ'
    )

    fun toCyrillicLookalike(text: String): String {
        return text.map { char -> cyrillicLookalike[char] ?: char }.joinToString("")
    }

    // Greek Lookalike - thay bằng ký tự Greek trông giống Latin
    private val greekLookalike = mapOf(
        'A' to 'Α', 'B' to 'Β', 'E' to 'Ε', 'H' to 'Η', 'I' to 'Ι', 'K' to 'Κ', 'M' to 'Μ',
        'N' to 'Ν', 'O' to 'Ο', 'P' to 'Ρ', 'T' to 'Τ', 'X' to 'Χ', 'Y' to 'Υ', 'Z' to 'Ζ',
        'a' to 'α', 'b' to 'β', 'e' to 'ε', 'i' to 'ι', 'o' to 'ο', 'p' to 'ρ', 'v' to 'ν',
        'x' to 'χ', 'y' to 'υ', 'u' to 'υ', 'n' to 'η', 'w' to 'ω'
    )

    fun toGreekLookalike(text: String): String {
        return text.map { char -> greekLookalike[char] ?: char }.joinToString("")
    }

    // Asian Mix - kết hợp Thai, Hebrew, Arabic
    private val asianMixLookalike = mapOf(
        'A' to 'ል', 'B' to 'ß', 'C' to 'ር', 'D' to 'ძ', 'E' to 'ﻉ', 'F' to 'Ŧ', 'G' to 'ɢ',
        'H' to 'ዘ', 'I' to 'ł', 'J' to 'ɾ', 'K' to 'ҡ', 'L' to 'ረ', 'M' to 'ጠ', 'N' to 'ก',
        'O' to 'ට', 'P' to 'ק', 'Q' to 'ợ', 'R' to 'ŗ', 'S' to 'ន', 'T' to 'ե', 'U' to 'ሁ',
        'V' to 'ν', 'W' to 'ω', 'X' to 'א', 'Y' to 'ע', 'Z' to 'ʑ',
        'a' to 'ค', 'b' to '๒', 'c' to 'ς', 'd' to '๔', 'e' to 'є', 'f' to 'Ŧ', 'g' to 'ﻮ',
        'h' to 'ђ', 'i' to 'เ', 'j' to 'ן', 'k' to 'к', 'l' to 'ɭ', 'm' to '๓', 'n' to 'ภ',
        'o' to 'ѻ', 'p' to 'ק', 'q' to 'ợ', 'r' to 'г', 's' to 'ร', 't' to 'Շ', 'u' to 'ย',
        'v' to 'ש', 'w' to 'ฬ', 'x' to 'א', 'y' to 'ץ', 'z' to 'չ'
    )

    fun toAsianMix(text: String): String {
        return text.map { char -> asianMixLookalike[char] ?: char }.joinToString("")
    }

    // Math Symbols Lookalike
    private val mathSymbolsLookalike = mapOf(
        'A' to '∆', 'B' to 'ℬ', 'C' to 'ℂ', 'D' to 'ⅅ', 'E' to '∃', 'F' to 'ℱ', 'G' to '⅁',
        'H' to 'ℍ', 'I' to '∫', 'J' to '⨍', 'K' to 'Ķ', 'L' to 'ℒ', 'M' to 'ℳ', 'N' to 'ℕ',
        'O' to '⊙', 'P' to 'ℙ', 'Q' to 'ℚ', 'R' to 'ℝ', 'S' to '∫', 'T' to '⊤', 'U' to '∪',
        'V' to '∨', 'W' to '∬', 'X' to '⨯', 'Y' to 'Ɣ', 'Z' to 'ℤ',
        'a' to 'α', 'b' to 'ß', 'c' to '⊂', 'd' to 'ð', 'e' to '∈', 'f' to 'ƒ', 'g' to 'ℊ',
        'h' to 'ℏ', 'i' to 'ι', 'j' to 'ʝ', 'k' to 'ĸ', 'l' to 'ℓ', 'm' to 'ɱ', 'n' to 'η',
        'o' to '⊕', 'p' to 'ρ', 'q' to 'ʠ', 'r' to 'ℝ', 's' to '∫', 't' to 'τ', 'u' to '∪',
        'v' to 'ν', 'w' to 'ω', 'x' to '×', 'y' to 'ɣ', 'z' to 'ʐ'
    )

    fun toMathSymbols(text: String): String {
        return text.map { char -> mathSymbolsLookalike[char] ?: char }.joinToString("")
    }

    // Weird/Cursive Mix - kết hợp nhiều bộ ký tự kỳ lạ
    private val weirdMixLookalike = mapOf(
        'A' to 'ꋫ', 'B' to 'ꃃ', 'C' to 'ꏸ', 'D' to 'ꁕ', 'E' to 'ꍟ', 'F' to 'ꄘ', 'G' to 'ꁍ',
        'H' to 'ꑛ', 'I' to 'ꂑ', 'J' to 'ꀭ', 'K' to 'ꀗ', 'L' to 'ꂑ', 'M' to 'ꁒ', 'N' to 'ꁹ',
        'O' to 'ꆂ', 'P' to 'ꉣ', 'Q' to 'ꁸ', 'R' to 'ꋪ', 'S' to 'ꌚ', 'T' to 'ꓔ', 'U' to 'ꐇ',
        'V' to 'ꏝ', 'W' to 'ꅐ', 'X' to 'ꇓ', 'Y' to 'ꐟ', 'Z' to 'ꁴ',
        'a' to 'ꋬ', 'b' to 'ꃳ', 'c' to 'ꉔ', 'd' to 'ꀸ', 'e' to 'ꏂ', 'f' to 'ꄞ', 'g' to 'ꍌ',
        'h' to 'ꁝ', 'i' to '꒐', 'j' to 'ꀭ', 'k' to 'ꀘ', 'l' to '꒒', 'm' to 'ꂵ', 'n' to 'ꋊ',
        'o' to 'ꂦ', 'p' to 'ꉣ', 'q' to 'ꋩ', 'r' to 'ꋪ', 's' to 'ꌚ', 't' to 'ꓔ', 'u' to 'ꐇ',
        'v' to 'ꅏ', 'w' to 'ꅐ', 'x' to 'ꉧ', 'y' to 'ꌩ', 'z' to 'ꁴ'
    )

    fun toWeirdMix(text: String): String {
        return text.map { char -> weirdMixLookalike[char] ?: char }.joinToString("")
    }

    // Squared Negative (White on Black) - using String because emojis are multi-byte
    private val squaredNegativeLookalike = mapOf(
        'A' to "🅰", 'B' to "🅱", 'C' to "🅲", 'D' to "🅳", 'E' to "🅴", 'F' to "🅵", 'G' to "🅶",
        'H' to "🅷", 'I' to "🅸", 'J' to "🅹", 'K' to "🅺", 'L' to "🅻", 'M' to "🅼", 'N' to "🅽",
        'O' to "🅾", 'P' to "🅿", 'Q' to "🆀", 'R' to "🆁", 'S' to "🆂", 'T' to "🆃", 'U' to "🆄",
        'V' to "🆅", 'W' to "🆆", 'X' to "🆇", 'Y' to "🆈", 'Z' to "🆉"
    )

    fun toSquaredNegativeLookalike(text: String): String {
        return text.map { char -> squaredNegativeLookalike[char.uppercaseChar()] ?: char.toString() }.joinToString("")
    }

    // Currency Symbols Mix
    private val currencyMixLookalike = mapOf(
        'A' to 'Ⱥ', 'B' to '฿', 'C' to '₵', 'D' to 'Đ', 'E' to '€', 'F' to 'Ƒ', 'G' to 'Ǥ',
        'H' to 'Ħ', 'I' to 'ł', 'J' to 'J', 'K' to '₭', 'L' to '£', 'M' to '₥', 'N' to '₦',
        'O' to 'Ø', 'P' to '₱', 'Q' to 'Q', 'R' to '₹', 'S' to '$', 'T' to '₮', 'U' to 'Ʉ',
        'V' to '✓', 'W' to '₩', 'X' to '×', 'Y' to '¥', 'Z' to 'Ƶ',
        'a' to 'ą', 'b' to 'ҍ', 'c' to '¢', 'd' to 'ď', 'e' to 'ę', 'f' to 'ƒ', 'g' to 'ց',
        'h' to 'ħ', 'i' to 'ï', 'j' to 'ʝ', 'k' to 'ҟ', 'l' to 'ℓ', 'm' to 'ʍ', 'n' to 'ղ',
        'o' to 'ơ', 'p' to 'ք', 'q' to 'զ', 'r' to 'ɾ', 's' to 'ʂ', 't' to 'է', 'u' to 'մ',
        'v' to 'ѵ', 'w' to 'ա', 'x' to 'ҳ', 'y' to 'վ', 'z' to 'Հ'
    )

    fun toCurrencyMix(text: String): String {
        return text.map { char -> currencyMixLookalike[char] ?: char }.joinToString("")
    }

    // ==================== MIXED / RANDOM STYLES ====================

    // Random Mix Light - mỗi chữ cái được apply 1 trong 5 styles ngẫu nhiên (thực sự random)
    fun toMixedLight(text: String): String {
        val styles = listOf(
            this::toBold,
            this::toItalic,
            this::toScript,
            this::toMonospace,
            this::toSans
        )
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    val style = styles.random()
                    style(String(intArrayOf(codePoint), 0, 1))
                }
            }
            .toArray()
            .joinToString("")
    }

    // Random Mix Medium - mỗi chữ cái được apply 1 trong 10 styles ngẫu nhiên (thực sự random)
    fun toMixedMedium(text: String): String {
        val styles = listOf(
            this::toBold,
            this::toItalic,
            this::toBoldItalic,
            this::toScript,
            this::toBoldScript,
            this::toFraktur,
            this::toMonospace,
            this::toSans,
            this::toSansBold,
            this::toCircled
        )
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    val style = styles.random()
                    style(String(intArrayOf(codePoint), 0, 1))
                }
            }
            .toArray()
            .joinToString("")
    }

    // Random Mix Heavy - mỗi chữ cái được apply 1 trong nhiều styles khác nhau (thực sự random)
    fun toMixedHeavy(text: String): String {
        val styles = listOf(
            this::toBold,
            this::toItalic,
            this::toBoldItalic,
            this::toScript,
            this::toBoldScript,
            this::toFraktur,
            this::toBoldFraktur,
            this::toDoubleStruck,
            this::toSans,
            this::toSansBold,
            this::toSansItalic,
            this::toSansBoldItalic,
            this::toMonospace,
            this::toCircled,
            this::toSquared,
            this::toFullwidth
        )
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    val style = styles.random()
                    style(String(intArrayOf(codePoint), 0, 1))
                }
            }
            .toArray()
            .joinToString("")
    }

    // Alternating Case - chữ hoa chữ thường xen kẽ (sPoNgEbOb style)
    fun toAlternatingCase(text: String): String {
        var shouldBeUpper = false
        return text.map { char ->
            if (char.isLetter()) {
                shouldBeUpper = !shouldBeUpper
                if (shouldBeUpper) char.uppercaseChar() else char.lowercaseChar()
            } else {
                char
            }
        }.joinToString("")
    }

    // Random Case - mỗi chữ cái random hoa hoặc thường
    fun toRandomCase(text: String): String {
        return text.map { char ->
            if (char.isLetter()) {
                if (kotlin.random.Random.nextBoolean()) char.uppercaseChar() else char.lowercaseChar()
            } else {
                char
            }
        }.joinToString("")
    }

    // Mixed Substitution - kết hợp nhiều loại character substitution
    fun toMixedSubstitution(text: String): String {
        val substitutions = listOf(
            cyrillicLookalike,
            greekLookalike,
            mathSymbolsLookalike
        )
        return text.map { char ->
            val sub = substitutions.random()
            sub[char] ?: char
        }.joinToString("")
    }

    // Alternating Styles - xen kẽ giữa 2 styles (ví dụ: bold và italic)
    fun toAlternatingStyles(text: String): String {
        var useBold = true
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    useBold = !useBold
                    val charStr = String(intArrayOf(codePoint), 0, 1)
                    if (useBold) toBold(charStr) else toItalic(charStr)
                }
            }
            .toArray()
            .joinToString("")
    }

    // Wave Style - tạo hiệu ứng sóng với superscript và subscript
    fun toWaveStyle(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        var index = 0
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    val result = when (index % 3) {
                        0 -> {
                            // Try to find in superscript map
                            val char = String(intArrayOf(codePoint), 0, 1).firstOrNull()
                            char?.let { superscriptMap[it]?.toString() } ?: String(intArrayOf(codePoint), 0, 1)
                        }
                        1 -> String(intArrayOf(codePoint), 0, 1)
                        else -> {
                            // Try to find in subscript map
                            val char = String(intArrayOf(codePoint), 0, 1).firstOrNull()
                            char?.let { subscriptMap[it]?.toString() } ?: String(intArrayOf(codePoint), 0, 1)
                        }
                    }
                    index++
                    result
                }
            }
            .toArray()
            .joinToString("")
    }

    // Bubble Text - kết hợp circled với các decorations
    fun toBubbleText(text: String): String {
        return "◯ ${toCircled(text)} ◯"
    }

    // Fancy Mix - kết hợp nhiều decorations
    fun toFancyMix(text: String): String {
        return "✧･ﾟ: *✧･ﾟ:* ${toScript(text)} *:･ﾟ✧*:･ﾟ✧"
    }

    // Aesthetic Style - style aesthetic với decorations
    fun toAesthetic(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    " "
                } else {
                    String(intArrayOf(codePoint), 0, 1) + "　" // Thêm fullwidth space
                }
            }
            .toArray()
            .joinToString("")
            .trim()
    }

    // Vaporwave Style - fullwidth + aesthetic
    fun toVaporwave(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toFullwidth(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    " "
                } else {
                    String(intArrayOf(codePoint), 0, 1) + "　"
                }
            }
            .toArray()
            .joinToString("")
            .trim()
    }

    // Glitch Mix - kết hợp nhiều styles tạo hiệu ứng glitch
    fun toGlitchMix(text: String): String {
        val styles = listOf(
            this::toBold,
            this::toItalic,
            this::toStrikethrough,
            this::toUnderline,
            this::toSlash
        )
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    val style = styles.random()
                    style(String(intArrayOf(codePoint), 0, 1))
                }
            }
            .toArray()
            .joinToString("")
    }

    // Crazy Mix - kết hợp character substitution với combining marks
    fun toCrazyMix(text: String): String {
        val allLookalikes = listOf(
            cyrillicLookalike,
            greekLookalike,
            asianMixLookalike,
            mathSymbolsLookalike,
            weirdMixLookalike,
            currencyMixLookalike
        )
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    // Try to convert codePoint to Char for map lookup (only works for BMP)
                    val char = if (codePoint <= 0xFFFF) codePoint.toChar() else null
                    val lookalike = allLookalikes.random()
                    val substituted = char?.let { lookalike[it] } ?: codePoint.toChar()
                    // Thêm random combining mark
                    if (kotlin.random.Random.nextBoolean()) {
                        val mark = if (kotlin.random.Random.nextBoolean()) {
                            zalgoMarksUp.random()
                        } else {
                            zalgoMarksDown.random()
                        }
                        "$substituted$mark"
                    } else {
                        substituted.toString()
                    }
                }
            }
            .toArray()
            .joinToString("")
    }

    // ==================== NEW UNICODE STYLES (41) ====================

    // 1. Armenian Script
    private val armenianMap = mapOf(
        'A' to "Ա", 'B' to "Բ", 'C' to "Ծ", 'D' to "Դ", 'E' to "Ե", 'F' to "Ֆ", 'G' to "Գ",
        'H' to "Հ", 'I' to "Ի", 'J' to "Ջ", 'K' to "Կ", 'L' to "Լ", 'M' to "Մ", 'N' to "Ն",
        'O' to "Ո", 'P' to "Պ", 'Q' to "Ք", 'R' to "Ռ", 'S' to "Ս", 'T' to "Տ", 'U' to "Ո",
        'V' to "Վ", 'W' to "Ւ", 'X' to "Խ", 'Y' to "Ը", 'Z' to "Զ",
        'a' to "ա", 'b' to "բ", 'c' to "ծ", 'd' to "դ", 'e' to "ե", 'f' to "ֆ", 'g' to "գ",
        'h' to "հ", 'i' to "ի", 'j' to "ջ", 'k' to "կ", 'l' to "լ", 'm' to "մ", 'n' to "ն",
        'o' to "ո", 'p' to "պ", 'q' to "ք", 'r' to "ր", 's' to "ս", 't' to "տ", 'u' to "ու",
        'v' to "վ", 'w' to "ւ", 'x' to "խ", 'y' to "ը", 'z' to "զ"
    )

    fun toArmenian(text: String): String {
        return text.map { armenianMap[it] ?: it.toString() }.joinToString("")
    }

    // 2. Thai-Lao Mix
    private val thaiLaoMap = mapOf(
        'A' to 'Ꭿ', 'B' to 'ɮ', 'C' to 'ር', 'D' to 'Ꮄ', 'E' to 'Ꭼ', 'F' to 'Ꭶ', 'G' to 'Ꮹ',
        'H' to 'Ꮒ', 'I' to 'Ꭵ', 'J' to 'Ꮰ', 'K' to 'Ꮶ', 'L' to 'Ꮭ', 'M' to 'Ꮇ', 'N' to 'Ꮑ',
        'O' to 'Ꭷ', 'P' to 'Ꭾ', 'Q' to 'Ꭴ', 'R' to 'Ꮢ', 'S' to 'Ꮥ', 'T' to 'Ꮦ', 'U' to 'Ꮜ',
        'V' to 'Ꮙ', 'W' to 'Ꮃ', 'X' to 'ጀ', 'Y' to 'Ꭹ', 'Z' to 'Ꮓ',
        'a' to 'ค', 'b' to '๒', 'c' to 'ς', 'd' to '๔', 'e' to 'є', 'f' to 'Ŧ', 'g' to 'ﻮ',
        'h' to 'ђ', 'i' to 'เ', 'j' to 'ן', 'k' to 'к', 'l' to 'ɭ', 'm' to '๓', 'n' to 'ภ',
        'o' to '๏', 'p' to 'ק', 'q' to 'ợ', 'r' to 'г', 's' to 'ร', 't' to 'Շ', 'u' to 'ย',
        'v' to 'ש', 'w' to 'ฬ', 'x' to 'א', 'y' to 'ץ', 'z' to 'չ'
    )

    fun toThaiLaoMix(text: String): String {
        return text.map { thaiLaoMap[it] ?: it }.joinToString("")
    }

    // 3. Japanese Mix (Katakana + Hiragana)
    private val japaneseMixMap = mapOf(
        'A' to "ﾑ", 'B' to "ﾌ", 'C' to "ち", 'D' to "ﾉ", 'E' to "ヨ", 'F' to "ｷ", 'G' to "ム",
        'H' to "ん", 'I' to "ﾉ", 'J' to "ﾌ", 'K' to "ズ", 'L' to "ﾚ", 'M' to "ﾶ", 'N' to "ﾑ",
        'O' to "の", 'P' to "ｱ", 'Q' to "q͆", 'R' to "ƦﾉƦ", 'S' to "ら", 'T' to "ｲ", 'U' to "ひ",
        'V' to "ﾘ", 'W' to "ᐯ", 'X' to "ﾒ", 'Y' to "ﾘ", 'Z' to "乙",
        'a' to "ﾑ", 'b' to "ﾌ", 'c' to "c", 'd' to "d", 'e' to "乇", 'f' to "ｷ", 'g' to "g",
        'h' to "ん", 'i' to "ﾉ", 'j' to "ﾌ", 'k' to "ズ", 'l' to "ﾚ", 'm' to "ﾶ", 'n' to "刀",
        'o' to "の", 'p' to "ｱ", 'q' to "q͆", 'r' to "я", 's' to "ら", 't' to "ｲ", 'u' to "ひ",
        'v' to "ﾘ", 'w' to "ᐯ", 'x' to "ﾒ", 'y' to "ﾘ", 'z' to "乙"
    )

    fun toJapaneseMix(text: String): String {
        return text.map { japaneseMixMap[it] ?: it.toString() }.joinToString("")
    }

    // 4. Medieval Latin
    private val medievalLatinMap = mapOf(
        'A' to 'ꜳ', 'B' to 'ꞵ', 'C' to 'ꞓ', 'D' to 'ꝺ', 'E' to 'ꬲ', 'F' to 'ꝼ', 'G' to 'ꝿ',
        'H' to 'ꜧ', 'I' to 'ꝭ', 'J' to 'ꝫ', 'K' to 'ꝁ', 'L' to 'ꝇ', 'M' to 'ꝳ', 'N' to 'ꞑ',
        'O' to 'ꝍ', 'P' to 'ꝑ', 'Q' to 'ꝗ', 'R' to 'ꝛ', 'S' to 'ꞩ', 'T' to 'ꞇ', 'U' to 'ꝶ',
        'V' to 'ꝟ', 'W' to 'ꝡ', 'X' to 'ꭓ', 'Y' to 'ꝩ', 'Z' to 'ꝣ',
        'a' to 'ꜳ', 'b' to 'ꞵ', 'c' to 'ꞓ', 'd' to 'ꝺ', 'e' to 'ꬲ', 'f' to 'ꝼ', 'g' to 'ꝿ',
        'h' to 'ꜧ', 'i' to 'ꝭ', 'j' to 'ꝫ', 'k' to 'ꝁ', 'l' to 'ꝇ', 'm' to 'ꝳ', 'n' to 'ꞑ',
        'o' to 'ꝍ', 'p' to 'ꝑ', 'q' to 'ꝗ', 'r' to 'ꝛ', 's' to 'ꞩ', 't' to 'ꞇ', 'u' to 'ꝶ',
        'v' to 'ꝟ', 'w' to 'ꝡ', 'x' to 'ꭓ', 'y' to 'ꝩ', 'z' to 'ꝣ'
    )

    fun toMedievalLatin(text: String): String {
        return text.map { medievalLatinMap[it] ?: it }.joinToString("")
    }

    // 5. Bopomofo CJK
    private val bopomofoCJKMap = mapOf(
        'A' to 'ㄚ', 'B' to 'ㄅ', 'C' to 'ㄈ', 'D' to 'ㄉ', 'E' to 'ㄜ', 'F' to 'ㄈ', 'G' to 'ㄍ',
        'H' to 'ㄏ', 'I' to 'ㄧ', 'J' to 'ㄐ', 'K' to 'ㄎ', 'L' to 'ㄌ', 'M' to 'ㄇ', 'N' to 'ㄋ',
        'O' to 'ㄛ', 'P' to 'ㄆ', 'Q' to 'ㄑ', 'R' to 'ㄖ', 'S' to 'ㄙ', 'T' to 'ㄊ', 'U' to 'ㄨ',
        'V' to 'ㄩ', 'W' to 'ㄨ', 'X' to 'ㄒ', 'Y' to 'ㄧ', 'Z' to 'ㄗ',
        'a' to 'ㄚ', 'b' to 'ㄅ', 'c' to '匚', 'd' to 'ᗪ', 'e' to '乇', 'f' to 'ㄈ', 'g' to 'Ꮆ',
        'h' to '卄', 'i' to 'ㄧ', 'j' to 'ﾌ', 'k' to 'Ҝ', 'l' to 'ㄥ', 'm' to '爪', 'n' to '几',
        'o' to 'ㄖ', 'p' to '卩', 'q' to 'Ɋ', 'r' to '尺', 's' to '丂', 't' to 'ㄒ', 'u' to 'ㄩ',
        'v' to 'ᐯ', 'w' to '山', 'x' to '乂', 'y' to 'ㄚ', 'z' to '乙'
    )

    fun toBopomofoCJK(text: String): String {
        return text.map { bopomofoCJKMap[it] ?: it }.joinToString("")
    }

    // 6. Yi Syllables
    private val yiSyllablesMap = mapOf(
        'A' to 'ꋫ', 'B' to 'ꃃ', 'C' to 'ꏸ', 'D' to 'ꁕ', 'E' to 'ꍟ', 'F' to 'ꄘ', 'G' to 'ꁍ',
        'H' to 'ꑛ', 'I' to 'ꂑ', 'J' to 'ꀭ', 'K' to 'ꀗ', 'L' to 'ꂑ', 'M' to 'ꁒ', 'N' to 'ꁹ',
        'O' to 'ꆂ', 'P' to 'ꉣ', 'Q' to 'ꁸ', 'R' to 'ꋪ', 'S' to 'ꌚ', 'T' to 'ꋖ', 'U' to 'ꐇ',
        'V' to 'ꏝ', 'W' to 'ꅐ', 'X' to 'ꇓ', 'Y' to 'ꐟ', 'Z' to 'ꁴ',
        'a' to 'ꋬ', 'b' to 'ꃳ', 'c' to 'ꉔ', 'd' to 'ꀸ', 'e' to 'ꏂ', 'f' to 'ꄞ', 'g' to 'ꍌ',
        'h' to 'ꁝ', 'i' to '꒐', 'j' to 'ꀭ', 'k' to 'ꀘ', 'l' to '꒒', 'm' to 'ꂵ', 'n' to 'ꋊ',
        'o' to 'ꂦ', 'p' to 'ꉣ', 'q' to 'ꋩ', 'r' to 'ꋪ', 's' to 'ꌚ', 't' to 'ꓔ', 'u' to 'ꐇ',
        'v' to 'ꅏ', 'w' to 'ꅐ', 'x' to 'ꉧ', 'y' to 'ꌩ', 'z' to 'ꁴ'
    )

    fun toYiSyllables(text: String): String {
        return text.map { yiSyllablesMap[it] ?: it }.joinToString("")
    }

    // 7. Canadian Aboriginal
    private val canadianAboriginalMap = mapOf(
        'A' to 'ᗩ', 'B' to 'ᗷ', 'C' to 'ᑕ', 'D' to 'ᗪ', 'E' to 'ᗴ', 'F' to 'ᖴ', 'G' to 'Ǥ',
        'H' to 'ᕼ', 'I' to 'I', 'J' to 'ᒍ', 'K' to 'K', 'L' to 'ᒪ', 'M' to 'ᗰ', 'N' to 'ᑎ',
        'O' to 'O', 'P' to 'ᑭ', 'Q' to 'ᑫ', 'R' to 'ᖇ', 'S' to 'ᔕ', 'T' to 'T', 'U' to 'ᑌ',
        'V' to 'ᐯ', 'W' to 'ᗯ', 'X' to '᙭', 'Y' to 'Y', 'Z' to 'ᘔ',
        'a' to 'ᗩ', 'b' to 'ᗷ', 'c' to 'ᑕ', 'd' to 'ᗪ', 'e' to 'ᗴ', 'f' to 'ᖴ', 'g' to 'Ǥ',
        'h' to 'ᕼ', 'i' to 'I', 'j' to 'ᒍ', 'k' to 'K', 'l' to 'ᒪ', 'm' to 'ᗰ', 'n' to 'ᑎ',
        'o' to 'O', 'p' to 'ᑭ', 'q' to 'ᑫ', 'r' to 'ᖇ', 's' to 'ᔕ', 't' to 'T', 'u' to 'ᑌ',
        'v' to 'ᐯ', 'w' to 'ᗯ', 'x' to '᙭', 'y' to 'Y', 'z' to 'ᘔ'
    )

    fun toCanadianAboriginal(text: String): String {
        return text.map { canadianAboriginalMap[it] ?: it }.joinToString("")
    }

    // 8. Cherokee
    private val cherokeeMap = mapOf(
        'A' to 'Ꭿ', 'B' to 'Ᏸ', 'C' to 'Ꮸ', 'D' to 'Ꭰ', 'E' to 'Ꭼ', 'F' to 'Ꭶ', 'G' to 'Ꮹ',
        'H' to 'Ꮋ', 'I' to 'Ꭵ', 'J' to 'Ꮰ', 'K' to 'Ꮶ', 'L' to 'Ꮮ', 'M' to 'Ꮇ', 'N' to 'Ꮑ',
        'O' to 'Ꮎ', 'P' to 'Ꮲ', 'Q' to 'Ꭴ', 'R' to 'Ꮢ', 'S' to 'Ꮪ', 'T' to 'Ꮦ', 'U' to 'Ꮜ',
        'V' to 'Ꮙ', 'W' to 'Ꮗ', 'X' to 'Ꮴ', 'Y' to 'Ꮍ', 'Z' to 'Ꮓ',
        'a' to 'ꭿ', 'b' to 'ᏸ', 'c' to 'ꮸ', 'd' to 'ꭰ', 'e' to 'ꭼ', 'f' to 'ꭶ', 'g' to 'ꮹ',
        'h' to 'ꮋ', 'i' to 'ꭵ', 'j' to 'ꮰ', 'k' to 'ꮶ', 'l' to 'ꮮ', 'm' to 'ꮇ', 'n' to 'ꮑ',
        'o' to 'ꮎ', 'p' to 'ꮲ', 'q' to 'ꭴ', 'r' to 'ꮢ', 's' to 'ꮪ', 't' to 'ꮦ', 'u' to 'ꮜ',
        'v' to 'ꮙ', 'w' to 'ꮗ', 'x' to 'ꮴ', 'y' to 'ꮍ', 'z' to 'ꮓ'
    )

    fun toCherokee(text: String): String {
        return text.map { cherokeeMap[it] ?: it }.joinToString("")
    }

    // 9. Lisu
    private val lisuMap = mapOf(
        'A' to 'ꓮ', 'B' to 'ꓐ', 'C' to 'ꓚ', 'D' to 'ꓓ', 'E' to 'ꓰ', 'F' to 'ꓝ', 'G' to 'ꓖ',
        'H' to 'ꓧ', 'I' to 'ꓲ', 'J' to 'ꓙ', 'K' to 'ꓗ', 'L' to 'ꓡ', 'M' to 'ꓟ', 'N' to 'ꓠ',
        'O' to 'ꓳ', 'P' to 'ꓑ', 'Q' to 'ꆰ', 'R' to 'ꓣ', 'S' to 'ꓢ', 'T' to 'ꓔ', 'U' to 'ꓴ',
        'V' to 'ꓦ', 'W' to 'ꓪ', 'X' to 'ꓫ', 'Y' to 'ꓬ', 'Z' to 'ꓜ',
        'a' to 'ꓮ', 'b' to 'ꓐ', 'c' to 'ꓚ', 'd' to 'ꓓ', 'e' to 'ꓰ', 'f' to 'ꓝ', 'g' to 'ꓖ',
        'h' to 'ꓧ', 'i' to 'ꓲ', 'j' to 'ꓙ', 'k' to 'ꓗ', 'l' to 'ꓡ', 'm' to 'ꓟ', 'n' to 'ꓠ',
        'o' to 'ꓳ', 'p' to 'ꓑ', 'q' to 'ꆰ', 'r' to 'ꓣ', 's' to 'ꓢ', 't' to 'ꓔ', 'u' to 'ꓴ',
        'v' to 'ꓦ', 'w' to 'ꓪ', 'x' to 'ꓫ', 'y' to 'ꓬ', 'z' to 'ꓜ'
    )

    fun toLisu(text: String): String {
        return text.map { lisuMap[it] ?: it }.joinToString("")
    }

    // 10. Hebrew-Greek Mix
    private val hebrewGreekMap = mapOf(
        'A' to "Α", 'B' to "ß", 'C' to "ς", 'D' to "Ď", 'E' to "Ξ", 'F' to "ךּ", 'G' to "Ǥ",
        'H' to "Ħ", 'I' to "ł", 'J' to "ĵ", 'K' to "Ҡ", 'L' to "ĺ", 'M' to "ʍ", 'N' to "ה",
        'O' to "Θ", 'P' to "ק", 'Q' to "Ҩ", 'R' to "ŗ", 'S' to "Ş", 'T' to "Ҭ", 'U' to "Ա",
        'V' to "ν", 'W' to "ω", 'X' to "א", 'Y' to "צּ", 'Z' to "Ζ",
        'a' to "α", 'b' to "ъ", 'c' to "ς", 'd' to "ď", 'e' to "ε", 'f' to "ךּ", 'g' to "ǥ",
        'h' to "ħ", 'i' to "ï", 'j' to "ĵ", 'k' to "ҡ", 'l' to "ĺ", 'm' to "מ", 'n' to "η",
        'o' to "θ", 'p' to "ρ", 'q' to "զ", 'r' to "ŗ", 's' to "ş", 't' to "է", 'u' to "υ",
        'v' to "ν", 'w' to "ω", 'x' to "χ", 'y' to "ÿ", 'z' to "ζ"
    )

    fun toHebrewGreekMix(text: String): String {
        return text.map { hebrewGreekMap[it] ?: it.toString() }.joinToString("")
    }

    // 11. Gujarati Mix
    private val gujaratiMixMap = mapOf(
        'A' to 'અ', 'B' to 'બ', 'C' to 'ર', 'D' to 'દ', 'E' to 'ε', 'F' to 'ક', 'G' to 'ગ',
        'H' to 'હ', 'I' to 'ી', 'J' to 'જ', 'K' to 'ક', 'L' to 'લ', 'M' to 'μ', 'N' to 'ય',
        'O' to 'ο', 'P' to 'પ', 'Q' to 'ق', 'R' to 'ર', 'S' to 'સ', 'T' to 'ત', 'U' to 'υ',
        'V' to 'ν', 'W' to 'ω', 'X' to 'ખ', 'Y' to 'ϓ', 'Z' to 'ઝ',
        'a' to 'અ', 'b' to 'બ', 'c' to 'ર', 'd' to 'દ', 'e' to '૯', 'f' to 'ક', 'g' to 'ગ',
        'h' to 'હ', 'i' to 'ી', 'j' to 'જ', 'k' to 'ક', 'l' to 'લ', 'm' to 'μ', 'n' to 'ય',
        'o' to '૧', 'p' to 'પ', 'q' to 'ق', 'r' to 'ર', 's' to 'સ', 't' to 'ત', 'u' to 'υ',
        'v' to 'ν', 'w' to 'ω', 'x' to 'ખ', 'y' to 'ϓ', 'z' to 'ઝ'
    )

    fun toGujaratiMix(text: String): String {
        return text.map { gujaratiMixMap[it] ?: it }.joinToString("")
    }

    // 12. Thai-Armenian Mix
    private val thaiArmenianMap = mapOf(
        'A' to 'Ꭿ', 'B' to 'ß', 'C' to 'ር', 'D' to 'Ꭰ', 'E' to 'Ꭼ', 'F' to 'Ŧ', 'G' to 'Ꮹ',
        'H' to 'Ꮋ', 'I' to 'ł', 'J' to 'Ꮰ', 'K' to 'Ꮶ', 'L' to 'Ꮮ', 'M' to 'Ꮇ', 'N' to 'Ꮑ',
        'O' to 'Ꮎ', 'P' to 'Ꮲ', 'Q' to 'ợ', 'R' to 'Ꮢ', 'S' to 'Ꮪ', 'T' to 'Շ', 'U' to 'Ա',
        'V' to 'Ꮙ', 'W' to 'Ꮗ', 'X' to 'Ꮴ', 'Y' to 'Ꮍ', 'Z' to 'Ꮓ',
        'a' to 'ค', 'b' to '๒', 'c' to 'ς', 'd' to 'ძ', 'e' to 'є', 'f' to 'Ŧ', 'g' to 'ﻮ',
        'h' to 'ђ', 'i' to 'เ', 'j' to 'ן', 'k' to 'к', 'l' to 'ɭ', 'm' to '๓', 'n' to 'ภ',
        'o' to 'ѻ', 'p' to 'ק', 'q' to 'ợ', 'r' to 'г', 's' to 'ร', 't' to 'Շ', 'u' to 'ย',
        'v' to 'ש', 'w' to 'ฬ', 'x' to 'א', 'y' to 'ץ', 'z' to 'չ'
    )

    fun toThaiArmenianMix(text: String): String {
        return text.map { thaiArmenianMap[it] ?: it }.joinToString("")
    }

    // 13. Cyrillic-Armenian Mix
    private val cyrillicArmenianMap = mapOf(
        'A' to 'Ա', 'B' to 'В', 'C' to 'С', 'D' to 'Ď', 'E' to 'Є', 'F' to 'Ŧ', 'G' to 'Ǥ',
        'H' to 'Ң', 'I' to 'Ї', 'J' to 'Ј', 'K' to 'Ќ', 'L' to 'Ŀ', 'M' to 'Μ', 'N' to 'ה',
        'O' to 'Ő', 'P' to 'Ք', 'Q' to 'Ҩ', 'R' to 'Ŗ', 'S' to 'Ѕ', 'T' to 'Ҭ', 'U' to 'Ա',
        'V' to 'V', 'W' to 'Ш', 'X' to 'Ҳ', 'Y' to 'Ұ', 'Z' to 'Ζ',
        'a' to 'ա', 'b' to 'ъ', 'c' to 'ς', 'd' to 'ď', 'e' to 'є', 'f' to 'ƒ', 'g' to 'ǥ',
        'h' to 'һ', 'i' to 'ï', 'j' to 'ј', 'k' to 'ĸ', 'l' to 'ℓ', 'm' to 'մ', 'n' to 'ղ',
        'o' to 'ơ', 'p' to 'ք', 'q' to 'զ', 'r' to 'ɾ', 's' to 'ʂ', 't' to 'է', 'u' to 'մ',
        'v' to 'ѵ', 'w' to 'ա', 'x' to 'ҳ', 'y' to 'վ', 'z' to 'Հ'
    )

    fun toCyrillicArmenianMix(text: String): String {
        return text.map { cyrillicArmenianMap[it] ?: it }.joinToString("")
    }

    // 14. Greek-Cyrillic Mix
    private val greekCyrillicMap = mapOf(
        'A' to 'Ѧ', 'B' to 'В', 'C' to 'Ҫ', 'D' to 'Ď', 'E' to 'Є', 'F' to 'Ғ', 'G' to 'Ǥ',
        'H' to 'Ӈ', 'I' to 'Ї', 'J' to 'Ј', 'K' to 'Ќ', 'L' to 'Ŀ', 'M' to 'М', 'N' to 'И',
        'O' to 'Ό', 'P' to 'Ρ', 'Q' to 'Ҩ', 'R' to 'Я', 'S' to 'Ѕ', 'T' to 'Ҭ', 'U' to 'Ա',
        'V' to 'V', 'W' to 'Ш', 'X' to 'Ӿ', 'Y' to 'Ұ', 'Z' to 'Ζ',
        'a' to 'α', 'b' to 'ъ', 'c' to 'ς', 'd' to 'ď', 'e' to 'є', 'f' to 'ƒ', 'g' to 'ǥ',
        'h' to 'һ', 'i' to 'ï', 'j' to 'ј', 'k' to 'ĸ', 'l' to 'ℓ', 'm' to 'м', 'n' to 'и',
        'o' to 'ο', 'p' to 'ρ', 'q' to 'զ', 'r' to 'я', 's' to 'ѕ', 't' to 'т', 'u' to 'υ',
        'v' to 'ѵ', 'w' to 'ш', 'x' to 'χ', 'y' to 'ү', 'z' to 'ζ'
    )

    fun toGreekCyrillicMix(text: String): String {
        return text.map { greekCyrillicMap[it] ?: it }.joinToString("")
    }

    // 15. Latin-Greek-Vietnamese Mix
    private val latinGreekVietnameseMap = mapOf(
        'A' to 'Ả', 'B' to 'ß', 'C' to 'Č', 'D' to 'Đ', 'E' to 'Ế', 'F' to 'Ŧ', 'G' to 'Ǥ',
        'H' to 'Ħ', 'I' to 'Ï', 'J' to 'Ĵ', 'K' to 'Ķ', 'L' to 'Ł', 'M' to 'М', 'N' to 'Ň',
        'O' to 'Ω', 'P' to 'Ρ', 'Q' to 'Ǫ', 'R' to 'Ŗ', 'S' to 'Ş', 'T' to 'Ŧ', 'U' to 'Ữ',
        'V' to 'V', 'W' to 'Ŵ', 'X' to 'Χ', 'Y' to '¥', 'Z' to 'Ζ',
        'a' to 'ả', 'b' to 'ъ', 'c' to 'č', 'd' to 'đ', 'e' to 'ế', 'f' to 'ƒ', 'g' to 'ǥ',
        'h' to 'ħ', 'i' to 'ï', 'j' to 'ĵ', 'k' to 'ķ', 'l' to 'ł', 'm' to 'м', 'n' to 'ň',
        'o' to 'ơ', 'p' to 'ρ', 'q' to 'ǫ', 'r' to 'ŗ', 's' to 'ş', 't' to 'ŧ', 'u' to 'ữ',
        'v' to 'ν', 'w' to 'ŵ', 'x' to 'χ', 'y' to 'ý', 'z' to 'ζ'
    )

    fun toLatinGreekVietnameseMix(text: String): String {
        return text.map { latinGreekVietnameseMap[it] ?: it }.joinToString("")
    }

    // 16-18. IPA & Phonetic Styles
    private val ipaPhoneticMap = mapOf(
        'A' to 'ᴀ', 'B' to 'ʙ', 'C' to 'ᴄ', 'D' to 'ᴅ', 'E' to 'ᴇ', 'F' to 'ꜰ', 'G' to 'ɢ',
        'H' to 'ʜ', 'I' to 'ɪ', 'J' to 'ᴊ', 'K' to 'ᴋ', 'L' to 'ʟ', 'M' to 'ᴍ', 'N' to 'ɴ',
        'O' to 'ᴏ', 'P' to 'ᴘ', 'Q' to 'ǫ', 'R' to 'ʀ', 'S' to 's', 'T' to 'ᴛ', 'U' to 'ᴜ',
        'V' to 'ᴠ', 'W' to 'ᴡ', 'X' to 'x', 'Y' to 'ʏ', 'Z' to 'ᴢ',
        'a' to 'ᴀ', 'b' to 'ʙ', 'c' to 'ᴄ', 'd' to 'ᴅ', 'e' to 'ɛ', 'f' to 'ꜰ', 'g' to 'ɢ',
        'h' to 'ɦ', 'i' to 'ɪ', 'j' to 'ᴊ', 'k' to 'ᴋ', 'l' to 'ʟ', 'm' to 'ᴍ', 'n' to 'ɴ',
        'o' to 'ᴏ', 'p' to 'ᴘ', 'q' to 'զ', 'r' to 'ʀ', 's' to 's', 't' to 'ᴛ', 'u' to 'ʊ',
        'v' to 'ᴠ', 'w' to 'ᴡ', 'x' to 'x', 'y' to 'ʏ', 'z' to 'ᴢ'
    )

    fun toIPAPhonetic(text: String): String {
        return text.map { ipaPhoneticMap[it] ?: it }.joinToString("")
    }

    private val ipaExtendedMap = mapOf(
        'A' to 'ᴀ', 'B' to 'ʙ', 'C' to 'ᴄ', 'D' to 'ᴅ', 'E' to 'ɛ', 'F' to 'ꜰ', 'G' to 'ɢ',
        'H' to 'ɦ', 'I' to 'ɪ', 'J' to 'ᴊ', 'K' to 'ᴋ', 'L' to 'ʟ', 'M' to 'ᴍ', 'N' to 'ɴ',
        'O' to 'ɔ', 'P' to 'ᴘ', 'Q' to 'ǫ', 'R' to 'ʀ', 'S' to 's', 'T' to 'ᴛ', 'U' to 'ʊ',
        'V' to 'ᴠ', 'W' to 'ᴡ', 'X' to 'x', 'Y' to 'ʏ', 'Z' to 'ᴢ',
        'a' to 'ᴀ', 'b' to 'ь', 'c' to 'ᴄ', 'd' to 'ԁ', 'e' to 'є', 'f' to 'ꜰ', 'g' to 'ɢ',
        'h' to 'ɦ', 'i' to 'і', 'j' to 'ј', 'k' to 'ᴋ', 'l' to 'ʟ', 'm' to 'ᴍ', 'n' to 'ɴ',
        'o' to 'ᴏ', 'p' to 'ᴘ', 'q' to 'զ', 'r' to 'ʀ', 's' to 's', 't' to 'ᴛ', 'u' to 'ʊ',
        'v' to 'ᴠ', 'w' to 'ᴡ', 'x' to 'x', 'y' to 'ʏ', 'z' to 'ᴢ'
    )

    fun toIPAExtended(text: String): String {
        return text.map { ipaExtendedMap[it] ?: it }.joinToString("")
    }

    private val phoneticExtensionsMap = mapOf(
        'A' to 'ᴀ', 'B' to 'ʙ', 'C' to 'ᴄ', 'D' to 'ᴅ', 'E' to 'ᴇ', 'F' to 'ꜰ', 'G' to 'ɢ',
        'H' to 'ʜ', 'I' to 'ɪ', 'J' to 'ᴊ', 'K' to 'ᴋ', 'L' to 'ʟ', 'M' to 'ᴍ', 'N' to 'ɴ',
        'O' to 'ᴏ', 'P' to 'ᴘ', 'Q' to 'ǫ', 'R' to 'ʀ', 'S' to 'ꜱ', 'T' to 'ᴛ', 'U' to 'ᴜ',
        'V' to 'ᴠ', 'W' to 'ᴡ', 'X' to 'x', 'Y' to 'ʏ', 'Z' to 'ᴢ',
        'a' to 'ᴀ', 'b' to 'ʙ', 'c' to 'ᴄ', 'd' to 'ᴅ', 'e' to 'ᴇ', 'f' to 'ꜰ', 'g' to 'ɢ',
        'h' to 'ʜ', 'i' to 'ɪ', 'j' to 'ᴊ', 'k' to 'ᴋ', 'l' to 'ʟ', 'm' to 'ᴍ', 'n' to 'ɴ',
        'o' to 'ᴏ', 'p' to 'ᴘ', 'q' to 'զ', 'r' to 'ʀ', 's' to 'ꜱ', 't' to 'ᴛ', 'u' to 'ᴜ',
        'v' to 'ᴠ', 'w' to 'ᴡ', 'x' to 'x', 'y' to 'ʏ', 'z' to 'ᴢ'
    )

    fun toPhoneticExtensions(text: String): String {
        return text.map { phoneticExtensionsMap[it] ?: it }.joinToString("")
    }

    // 19-21. Advanced Greek Variants
    private val greekExtendedMap = mapOf(
        'A' to 'Ά', 'B' to 'Β', 'C' to 'Ϛ', 'D' to 'Ď', 'E' to 'Έ', 'F' to 'Ϝ', 'G' to 'Γ',
        'H' to 'Ή', 'I' to 'Ί', 'J' to 'Ϳ', 'K' to 'Κ', 'L' to 'Λ', 'M' to 'Μ', 'N' to 'Ν',
        'O' to 'Ό', 'P' to 'Ρ', 'Q' to 'Ϙ', 'R' to 'Ϸ', 'S' to 'Σ', 'T' to 'Τ', 'U' to 'Ύ',
        'V' to 'V', 'W' to 'Ω', 'X' to 'Χ', 'Y' to 'Ϋ', 'Z' to 'Ζ',
        'a' to 'ά', 'b' to 'β', 'c' to 'ς', 'd' to 'δ', 'e' to 'έ', 'f' to 'ϝ', 'g' to 'γ',
        'h' to 'ή', 'i' to 'ί', 'j' to 'ϳ', 'k' to 'κ', 'l' to 'λ', 'm' to 'μ', 'n' to 'ν',
        'o' to 'ό', 'p' to 'ρ', 'q' to 'ϙ', 'r' to 'ϸ', 's' to 'σ', 't' to 'τ', 'u' to 'ύ',
        'v' to 'ν', 'w' to 'ω', 'x' to 'χ', 'y' to 'ϋ', 'z' to 'ζ'
    )

    fun toGreekExtended(text: String): String {
        return text.map { greekExtendedMap[it] ?: it }.joinToString("")
    }

    private val greekCopticMixMap = mapOf(
        'A' to 'Ⲁ', 'B' to 'Ⲃ', 'C' to 'Ϲ', 'D' to 'Ⲇ', 'E' to 'Ⲉ', 'F' to 'Ϥ', 'G' to 'Ⲅ',
        'H' to 'Ⲏ', 'I' to 'Ⲓ', 'J' to 'Ϫ', 'K' to 'Ⲕ', 'L' to 'Ⲗ', 'M' to 'Ⲙ', 'N' to 'Ⲛ',
        'O' to 'Ⲟ', 'P' to 'Ⲣ', 'Q' to 'Ϥ', 'R' to 'Ⲣ', 'S' to 'Ⲥ', 'T' to 'Ⲧ', 'U' to 'Ⲩ',
        'V' to 'Ⲫ', 'W' to 'Ⲱ', 'X' to 'Ⲭ', 'Y' to 'Ⲯ', 'Z' to 'Ⲍ',
        'a' to 'ⲁ', 'b' to 'ⲃ', 'c' to 'ϲ', 'd' to 'ⲇ', 'e' to 'ⲉ', 'f' to 'ϥ', 'g' to 'ⲅ',
        'h' to 'ⲏ', 'i' to 'ⲓ', 'j' to 'ϫ', 'k' to 'ⲕ', 'l' to 'ⲗ', 'm' to 'ⲙ', 'n' to 'ⲛ',
        'o' to 'ⲟ', 'p' to 'ⲣ', 'q' to 'ϥ', 'r' to 'ⲣ', 's' to 'ⲥ', 't' to 'ⲧ', 'u' to 'ⲩ',
        'v' to 'ⲫ', 'w' to 'ⲱ', 'x' to 'ⲭ', 'y' to 'ⲯ', 'z' to 'ⲍ'
    )

    fun toGreekCopticMix(text: String): String {
        return text.map { greekCopticMixMap[it] ?: it }.joinToString("")
    }

    private val greekArchaicMap = mapOf(
        'A' to 'Ἀ', 'B' to 'Β', 'C' to 'Ϛ', 'D' to 'Δ', 'E' to 'Ἐ', 'F' to 'Ϝ', 'G' to 'Γ',
        'H' to 'Ἠ', 'I' to 'Ἰ', 'J' to 'Ϳ', 'K' to 'Κ', 'L' to 'Λ', 'M' to 'Μ', 'N' to 'Ν',
        'O' to 'Ὀ', 'P' to 'Π', 'Q' to 'Ϙ', 'R' to 'Ρ', 'S' to 'Σ', 'T' to 'Τ', 'U' to 'Ὑ',
        'V' to 'V', 'W' to 'Ω', 'X' to 'Χ', 'Y' to 'Ὑ', 'Z' to 'Ζ',
        'a' to 'ἀ', 'b' to 'β', 'c' to 'ϛ', 'd' to 'δ', 'e' to 'ἐ', 'f' to 'ϝ', 'g' to 'γ',
        'h' to 'ἠ', 'i' to 'ἰ', 'j' to 'ϳ', 'k' to 'κ', 'l' to 'λ', 'm' to 'μ', 'n' to 'ν',
        'o' to 'ὀ', 'p' to 'π', 'q' to 'ϙ', 'r' to 'ρ', 's' to 'ς', 't' to 'τ', 'u' to 'ὑ',
        'v' to 'ν', 'w' to 'ω', 'x' to 'χ', 'y' to 'ὑ', 'z' to 'ζ'
    )

    fun toGreekArchaic(text: String): String {
        return text.map { greekArchaicMap[it] ?: it }.joinToString("")
    }

    // 22-27. Symbol Separators
    private const val SEPARATOR_N_ARY_SYMBOL = '⨳'
    private const val SEPARATOR_APL_SYMBOL = '⊶'
    private const val SEPARATOR_STAR_SYMBOL = '⭐'
    private const val SEPARATOR_DOT_SYMBOL = '•'
    private const val SEPARATOR_DIAMOND_SYMBOL = '◇'
    private const val SEPARATOR_CIRCLE_SYMBOL = '○'

    fun toSeparatorNAry(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { String(intArrayOf(it), 0, 1) }
            .toArray()
            .joinToString(SEPARATOR_N_ARY_SYMBOL.toString())
    }

    fun toSeparatorAPL(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { String(intArrayOf(it), 0, 1) }
            .toArray()
            .joinToString(SEPARATOR_APL_SYMBOL.toString())
    }

    fun toSeparatorStar(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { String(intArrayOf(it), 0, 1) }
            .toArray()
            .joinToString(SEPARATOR_STAR_SYMBOL.toString())
    }

    fun toSeparatorDot(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { String(intArrayOf(it), 0, 1) }
            .toArray()
            .joinToString(SEPARATOR_DOT_SYMBOL.toString())
    }

    fun toSeparatorDiamond(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { String(intArrayOf(it), 0, 1) }
            .toArray()
            .joinToString(SEPARATOR_DIAMOND_SYMBOL.toString())
    }

    fun toSeparatorCircle(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { String(intArrayOf(it), 0, 1) }
            .toArray()
            .joinToString(SEPARATOR_CIRCLE_SYMBOL.toString())
    }

    // 28-35. Complex Combining Marks
    private const val COMBINING_INVERTED_BRIDGE_MARK = '\u035B'
    private const val COMBINING_CANDRABINDU_MARK = '\u0310'
    private const val COMBINING_ZIGZAG_MARK = '\u035B'
    private const val COMBINING_ARROW_MARK = '\u0362'

    fun toCombiningInvertedBridge(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_INVERTED_BRIDGE_MARK
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toCombiningCandrabindu(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_CANDRABINDU_MARK
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toCombiningZigzag(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_ZIGZAG_MARK
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toCombiningArrow(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_ARROW_MARK
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toDoubleStruckMultiDiacritics(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toDoubleStruck(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_DIAERESIS + COMBINING_BREVE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toDiacriticsRandomHeavy(text: String): String {
        val allDiacritics = listOf(
            COMBINING_DIAERESIS, COMBINING_ACUTE, COMBINING_GRAVE,
            COMBINING_CIRCUMFLEX, COMBINING_CARON, COMBINING_BREVE,
            COMBINING_MACRON, COMBINING_TILDE, COMBINING_RING_ABOVE
        )
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    val marks = (0..2).map { allDiacritics.random() }.joinToString("")
                    String(intArrayOf(codePoint), 0, 1) + marks
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toSuperscriptDiacriticsMix(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toSuperscript(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_ACUTE + COMBINING_DOT_ABOVE
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toSubscriptDiacriticsMix(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return toSubscript(text).codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    String(intArrayOf(codePoint), 0, 1) + COMBINING_UNDERLINE + COMBINING_DOT_ABOVE
                }
            }
            .toArray()
            .joinToString("")
    }

    // 36-37. Mirrored & Flipped Styles
    fun toMirroredReversed(text: String): String {
        val mirroredMap = mapOf(
            'A' to 'Ɒ', 'B' to 'ᙠ', 'C' to 'Ↄ', 'D' to 'ᗡ', 'E' to 'Ǝ', 'F' to 'ꟻ', 'G' to 'Ꭾ',
            'H' to 'H', 'I' to 'I', 'J' to 'Ⴑ', 'K' to 'ꟻ', 'L' to '⅃', 'M' to 'M', 'N' to 'И',
            'O' to 'O', 'P' to 'Ꮲ', 'Q' to 'Ỻ', 'R' to 'Я', 'S' to 'Ꙅ', 'T' to 'T', 'U' to 'U',
            'V' to 'V', 'W' to 'W', 'X' to 'X', 'Y' to 'Y', 'Z' to 'Z',
            'a' to 'ɒ', 'b' to 'd', 'c' to 'ɔ', 'd' to 'b', 'e' to 'ɘ', 'f' to 'ʇ', 'g' to 'ǫ',
            'h' to 'ʜ', 'i' to 'i', 'j' to 'ɾ', 'k' to 'ʞ', 'l' to '|', 'm' to 'm', 'n' to 'ᴎ',
            'o' to 'o', 'p' to 'q', 'q' to 'p', 'r' to 'ɿ', 's' to 'ꙅ', 't' to 'ƚ', 'u' to 'u',
            'v' to 'v', 'w' to 'w', 'x' to 'x', 'y' to 'ʏ', 'z' to 'z'
        )
        return text.map { mirroredMap[it] ?: it }.joinToString("").reversed()
    }

    fun toUpsideDownMirrored(text: String): String {
        return toInverted(text).map { invertedMap[it] ?: it }.joinToString("").reversed()
    }

    // 38-41. Heavy Effects
    fun toZalgoArrows(text: String): String {
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    val marksUp = (0..3).map { zalgoMarksUp.random() }.joinToString("")
                    val marksDown = (0..3).map { zalgoMarksDown.random() }.joinToString("")
                    String(intArrayOf(codePoint), 0, 1) + marksUp + marksDown + "￫"
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toGlitchHeavyMarks(text: String): String {
        val heavyMarks = listOf(
            '\u0334', '\u0335', '\u0336', '\u0337', '\u0338',
            '\u20E3', '\u20D0', '\u20D1', '\u20D2', '\u20D3'
        )
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    val marks = (0..4).map { heavyMarks.random() }.joinToString("")
                    String(intArrayOf(codePoint), 0, 1) + marks
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toChaoticMix(text: String): String {
        val allMaps = listOf(
            cyrillicLookalike, greekLookalike, asianMixLookalike,
            armenianMap, thaiLaoMap, japaneseMixMap, cherokeeMap
        )
        val allCombining = zalgoMarksUp + zalgoMarksDown
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    // Try to convert codePoint to Char for map lookup (only works for BMP)
                    val char = if (codePoint <= 0xFFFF) codePoint.toChar() else null
                    val map = allMaps.random()
                    val converted = char?.let { map[it] } ?: codePoint.toChar()
                    val marks = (0..3).map { allCombining.random() }.joinToString("")
                    "$converted$marks"
                }
            }
            .toArray()
            .joinToString("")
    }

    fun toExtremeCombining(text: String): String {
        val extremeMarks = zalgoMarksUp + zalgoMarksDown + listOf(
            COMBINING_DIAERESIS, COMBINING_ACUTE, COMBINING_GRAVE,
            COMBINING_CIRCUMFLEX, COMBINING_CARON, COMBINING_TILDE
        )
        // Use codePoints() to handle surrogate pairs correctly
        return text.codePoints()
            .mapToObj { codePoint ->
                if (Character.isWhitespace(codePoint)) {
                    String(intArrayOf(codePoint), 0, 1)
                } else {
                    val marks = (0..5).map { extremeMarks.random() }.joinToString("")
                    String(intArrayOf(codePoint), 0, 1) + marks
                }
            }
            .toArray()
            .joinToString("")
    }
}
