package com.xylophone.learning.music.activity_app.intro

import android.content.Context
import com.xylophone.learning.music.core.base.BaseAdapter
import com.xylophone.learning.music.core.extensions.loadImageGlide
import com.xylophone.learning.music.core.extensions.select
import com.xylophone.learning.music.core.extensions.setTextContent
import com.xylophone.learning.music.core.extensions.strings
import com.xylophone.learning.music.data.model.IntroModel
import com.xylophone.learning.music.databinding.ItemIntroBinding

class IntroAdapter(val context: Context) : BaseAdapter<IntroModel, ItemIntroBinding>(
    ItemIntroBinding::inflate
) {
    override fun onBind(binding: ItemIntroBinding, item: IntroModel, position: Int) {
        binding.apply {
            loadImageGlide(root, item.image, imvImage, false)
            tvContent.text = context.strings(item.content)
            tvContent.select()
        }
    }
}