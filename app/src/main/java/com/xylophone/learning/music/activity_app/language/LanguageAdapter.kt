package com.xylophone.learning.music.activity_app.language

import android.annotation.SuppressLint
import android.content.Context
import com.xylophone.learning.music.R
import com.xylophone.learning.music.core.base.BaseAdapter
import com.xylophone.learning.music.core.extensions.gone
import com.xylophone.learning.music.core.extensions.loadImageGlide
import com.xylophone.learning.music.core.extensions.setOnSingleClick
import com.xylophone.learning.music.core.extensions.visible
import com.xylophone.learning.music.data.model.LanguageModel
import com.xylophone.learning.music.databinding.ItemLanguageBinding

class LanguageAdapter(val context: Context) : BaseAdapter<LanguageModel, ItemLanguageBinding>(
    ItemLanguageBinding::inflate
) {
    var onItemClick: ((String) -> Unit) = {}
    override fun onBind(
        binding: ItemLanguageBinding, item: LanguageModel, position: Int
    ) {
        binding.apply {
            loadImageGlide(root, item.flag, imvFlag, false)
            tvLang.text = item.name

            if (item.activate) {
                langSelect.setBackgroundResource(R.drawable.bg_language_focus)

            } else {
                langSelect.setBackgroundResource(R.drawable.bg_language)


            }

            root.setOnSingleClick {
                onItemClick.invoke(item.code)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitItem(position: Int) {
        items.forEach { it.activate = false }
        items[position].activate = true
        notifyDataSetChanged()
    }
}