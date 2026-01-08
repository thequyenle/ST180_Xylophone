package com.xylophone.activity_app.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import androidx.core.graphics.toColorInt
import androidx.lifecycle.lifecycleScope
import com.xylophone.R
import com.xylophone.core.base.BaseActivity
import com.xylophone.core.extensions.rateApp
import com.xylophone.core.extensions.select
import com.xylophone.core.extensions.startIntentRightToLeft
import com.xylophone.core.extensions.visible
import com.xylophone.core.helper.LanguageHelper
import com.xylophone.core.helper.MediaHelper
import com.xylophone.core.utils.key.ValueKey
import com.xylophone.core.utils.state.RateState
import com.xylophone.databinding.ActivityHomeBinding
import com.xylophone.activity_app.SettingsActivity

import com.xylophone.core.extensions.gone
import com.xylophone.core.extensions.setOnSingleClick
import com.xylophone.core.extensions.strings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.system.exitProcess

class MainActivity : BaseActivity<ActivityHomeBinding>() {

    override fun setViewBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        deleteTempFolder()
        setupTextStroke()
    }

    private fun setupTextStroke() {
        val strokeColor = "#3E001C".toColorInt()
        val strokeWidth = 1f * resources.displayMetrics.density // Convert 1dp to pixels

        binding.tv1.setDoubleStroke(
            outerColor = strokeColor,
            outerWidth = strokeWidth,
            innerColor = Color.TRANSPARENT,
            innerWidth = 0f,
            join = Paint.Join.ROUND,
            miter = 10f
        )

        binding.tv1.setDoubleStroke(
            outerColor = strokeColor,
            outerWidth = strokeWidth,
            innerColor = Color.TRANSPARENT,
            innerWidth = 0f,
            join = Paint.Join.ROUND,
            miter = 10f
        )

        binding.tv2.setDoubleStroke(
            outerColor = strokeColor,
            outerWidth = strokeWidth,
            innerColor = Color.TRANSPARENT,
            innerWidth = 0f,
            join = Paint.Join.ROUND,
            miter = 10f
        )

        // Enable marquee effect for long text
        binding.tv1.isSelected = true
        binding.tv1.isSelected = true
        binding.tv2.isSelected = true
    }

    override fun viewListener() {
        binding.apply {
            actionBar.btnActionBarRight.setOnSingleClick { startIntentRightToLeft(SettingsActivity::class.java) }

            // Random Name button
            btnPlayHome.setOnSingleClick {
                startIntentRightToLeft(PlayActivity::class.java)
            }

            // My Name button
            btnMyRecordHome.setOnSingleClick {
                startIntentRightToLeft(MyRecordActivity::class.java)
            }


        }
    }

    override fun initText() {
        super.initText()
    }

    override fun initActionBar() {
        binding.actionBar.apply {
            imgHeadTitle.gone()
            btnActionBarRight.setImageResource(R.drawable.ic_settings)
            btnActionBarRight.visible()

        }
    }

    @SuppressLint("MissingSuperCall", "GestureBackNavigation")
    override fun onBackPressed() {
        if (!sharePreference.getIsRate(this) && sharePreference.getCountBack() % 2 != 0) {
            rateApp(sharePreference) { state ->
                when (state) {
                    RateState.LESS3 -> {
                        lifecycleScope.launch(Dispatchers.Main) {
                            delay(1000)
                            finishAffinity()
                        }
                    }

                    RateState.GREATER3 -> {
                        // Thoát app sau khi rate
                        lifecycleScope.launch(Dispatchers.Main) {
                            delay(1000)
                            finishAffinity()
                        }
                    }
                    RateState.CANCEL -> {
                        lifecycleScope.launch {
                            sharePreference.setCountBack(sharePreference.getCountBack() + 1)
                            withContext(Dispatchers.Main) {
                                delay(1000)
                                finishAffinity()
                            }
                        }
                    }
                }
            }
        } else {
            sharePreference.setCountBack(sharePreference.getCountBack() + 1)
            finishAffinity()
        }
    }

    private fun deleteTempFolder() {
        lifecycleScope.launch(Dispatchers.IO) {
            val dataTemp = MediaHelper.getImageInternal(this@MainActivity, ValueKey.DOWNLOAD_ALBUM_BACKGROUND)
            if (dataTemp.isNotEmpty()) {
                dataTemp.forEach {
                    val file = File(it)
                    file.delete()
                }
            }
        }
    }

    private fun updateText() {
        binding.apply {
            tv1.text = strings(R.string.random_name)
            tv2.text = strings(R.string.My_NickName)
            tv1.text = strings(R.string.my_name)

            // Re-enable marquee after text update
            tv1.isSelected = true
            tv1.isSelected = true
            tv2.isSelected = true
        }
    }

    override fun onRestart() {
        super.onRestart()
        LanguageHelper.setLocale(this)
        updateText()
    }
}