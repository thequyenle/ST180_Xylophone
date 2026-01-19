package com.xylophone.learning.music.core.custom.layout

import android.widget.ImageView
import com.xylophone.learning.music.core.custom.imageview.StrokeImageView

interface EventRatioFrame {
    fun onImageClick(image: StrokeImageView, btnEdit: ImageView)
}