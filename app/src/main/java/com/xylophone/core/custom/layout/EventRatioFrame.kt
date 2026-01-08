package com.xylophone.core.custom.layout

import android.widget.ImageView
import com.xylophone.core.custom.imageview.StrokeImageView

interface EventRatioFrame {
    fun onImageClick(image: StrokeImageView, btnEdit: ImageView)
}