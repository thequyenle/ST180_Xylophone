package com.xylophone.activity_app.language

import android.annotation.SuppressLint
import android.content.Context
import com.xylophone.R
import com.xylophone.core.base.BaseAdapter
import com.xylophone.core.extensions.gone
import com.xylophone.core.extensions.loadImageGlide
import com.xylophone.core.extensions.setOnSingleClick
import com.xylophone.core.extensions.visible
import com.xylophone.data.model.LanguageModel
import com.xylophone.databinding.ItemLanguageBinding

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
                loadImageGlide(root, R.drawable.ic_tick_lang, btnRadio, false)
                imvFocus.visible()
                tvLang.setTextColor(context.getColor(R.color.white))  // ← Màu chữ khi CHỌN
            } else {
                loadImageGlide(root, R.drawable.ic_not_tick_lang, btnRadio, false)
                imvFocus.gone()
                tvLang.setTextColor(context.getColor(R.color.dark_purple))   // ← Màu chữ khi KHÔNG CHỌN

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