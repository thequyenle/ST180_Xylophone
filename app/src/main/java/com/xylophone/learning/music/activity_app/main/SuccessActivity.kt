package com.xylophone.learning.music.activity_app.main

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.xylophone.learning.music.R
import com.xylophone.learning.music.core.base.BaseActivity
import com.xylophone.learning.music.core.extensions.handleBackLeftToRight
import com.xylophone.learning.music.core.extensions.select
import com.xylophone.learning.music.core.extensions.setOnSingleClick
import com.xylophone.learning.music.databinding.ActivitySuccessBinding

class SuccessActivity : BaseActivity<ActivitySuccessBinding>() {

    private var songId: String? = null

    override fun setViewBinding(): ActivitySuccessBinding {
        return ActivitySuccessBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        binding.play.select()
        // Get song ID from intent if passed
        WindowCompat.setDecorFitsSystemWindows(window, false)

        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.statusBars())
            systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        songId = intent.getStringExtra("song_id")
    }

    override fun viewListener() {
        binding.apply {
            // Play Again button - restart the same song
            btnBgPlay.setOnSingleClick {
                // Go back to PlayActivity with the same song to play again
                val intent = Intent(this@SuccessActivity, PlayActivity::class.java)
                songId?.let {
                    intent.putExtra("song_id", it)
                }
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            }
        }
    }

    override fun initText() {
        // Text already set in layout
    }

    override fun initActionBar() {
        // No action bar for success screen
    }

    override fun onBackPressed() {
        super.onBackPressed()
        handleBackLeftToRight()
    }
}
