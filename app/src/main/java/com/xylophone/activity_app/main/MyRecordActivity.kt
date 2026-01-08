package com.xylophone.activity_app.main

import android.view.LayoutInflater
import com.xylophone.R
import com.xylophone.core.base.BaseActivity
import com.xylophone.core.extensions.handleBackLeftToRight
import com.xylophone.core.extensions.setOnSingleClick
import com.xylophone.core.extensions.visible
import com.xylophone.databinding.ActivityMyRecordBinding

class MyRecordActivity : BaseActivity<ActivityMyRecordBinding>() {

    override fun setViewBinding(): ActivityMyRecordBinding {
        return ActivityMyRecordBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        // Khởi tạo view
    }

    override fun viewListener() {
        binding.apply {
            actionBar.btnActionBarLeft.setOnSingleClick {
                handleBackLeftToRight()
            }
        }
    }

    override fun initText() {

    }

    override fun initActionBar() {
        binding.actionBar.apply {
            tvCenter.text = "My Record"
            tvCenter.visible()
            btnActionBarLeft.setImageResource(R.drawable.ic_back)
            btnActionBarLeft.visible()
        }
    }
}
