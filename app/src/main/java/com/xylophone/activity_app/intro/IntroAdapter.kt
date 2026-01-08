package com.xylophone.activity_app.intro

import android.content.Context
import com.xylophone.core.base.BaseAdapter
import com.xylophone.core.extensions.loadImageGlide
import com.xylophone.core.extensions.select
import com.xylophone.core.extensions.setTextContent
import com.xylophone.core.extensions.strings
import com.xylophone.data.model.IntroModel
import com.xylophone.databinding.ItemIntroBinding

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