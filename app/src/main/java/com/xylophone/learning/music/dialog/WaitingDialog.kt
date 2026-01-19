package com.xylophone.learning.music.dialog

import android.app.Activity
import com.xylophone.learning.music.R
import com.xylophone.learning.music.core.base.BaseDialog
import com.xylophone.learning.music.core.extensions.setBackgroundConnerSmooth
import com.xylophone.learning.music.databinding.DialogLoadingBinding

class WaitingDialog(val context: Activity) :
    BaseDialog<DialogLoadingBinding>(context, maxWidth = true, maxHeight = true) {
    override val layoutId: Int = R.layout.dialog_loading
    override val isCancelOnTouchOutside: Boolean = false
    override val isCancelableByBack: Boolean = false

    override fun initView() {
    }

    override fun initAction() {}

    override fun onDismissListener() {}

}