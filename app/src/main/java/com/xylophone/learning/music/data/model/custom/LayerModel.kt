package com.xylophone.learning.music.data.model.custom

import com.xylophone.learning.music.data.model.custom.ColorModel

data class LayerModel(
    val image: String,
    val isMoreColors: Boolean = false,
    var listColor: ArrayList<ColorModel> = arrayListOf()
)