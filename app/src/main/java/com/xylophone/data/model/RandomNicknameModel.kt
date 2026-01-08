package com.xylophone.data.model

data class RandomNicknameModel(
    val nickname: String, // Original nickname without style
    val category: String,
    val styledNickname: String, // Nickname with Unicode style applied
    val styleType: StyleType? = null, // Style type applied
    val leftSymbol: String? = null, // Left emoji symbol
    val rightSymbol: String? = null // Right emoji symbol
)

