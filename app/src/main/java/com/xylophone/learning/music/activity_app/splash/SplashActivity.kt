package com.xylophone.learning.music.activity_app.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.xylophone.learning.music.core.base.BaseActivity
import com.xylophone.learning.music.core.extensions.initNetworkMonitor
import com.xylophone.learning.music.databinding.ActivitySplashBinding
import com.xylophone.learning.music.activity_app.intro.IntroActivity
import com.xylophone.learning.music.activity_app.language.LanguageActivity
import com.xylophone.learning.music.activity_app.main.DataViewModel
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    var intentActivity: Intent? = null
    private val dataViewModel: DataViewModel by viewModels()
    private val splashDuration = 3000L // 3 seconds

    override fun setViewBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        if (!isTaskRoot &&
            intent.hasCategory(Intent.CATEGORY_LAUNCHER) &&
            intent.action != null &&
            intent.action.equals(Intent.ACTION_MAIN)) {
            finish(); return
        }

        intentActivity = if (sharePreference.getIsFirstLang()) {
            Intent(this, LanguageActivity::class.java)
        } else {
            Intent(this, IntroActivity::class.java)
        }
        initNetworkMonitor()

        // Start loading data
        dataViewModel.ensureData(this)

        // Wait exactly 3 seconds before navigating
        binding.root.postDelayed({
            if (!isFinishing) {
                startActivity(intentActivity)
                finishAffinity()
            }
        }, splashDuration)
    }

    override fun dataObservable() {
        // Data will be loaded in background, but we won't navigate until 3 seconds pass
    }

    override fun viewListener() {
    }

    override fun initText() {}

    override fun initActionBar() {}

    @SuppressLint("GestureBackNavigation", "MissingSuperCall")
    override fun onBackPressed() {}
}