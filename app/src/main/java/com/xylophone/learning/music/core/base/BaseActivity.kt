package com.xylophone.learning.music.core.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity

import androidx.viewbinding.ViewBinding
import com.xylophone.learning.music.R
import com.xylophone.learning.music.core.extensions.handleBackLeftToRight
import com.xylophone.learning.music.core.extensions.hideNavigation
import com.xylophone.learning.music.core.helper.LanguageHelper
import com.xylophone.learning.music.core.helper.SharePreferenceHelper
import com.xylophone.learning.music.core.helper.SoundHelper
import com.xylophone.learning.music.dialog.WaitingDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    private var _binding: T? = null
    protected val binding get() = _binding!!

    protected abstract fun setViewBinding(): T

    protected abstract fun initView()

    protected abstract fun viewListener()

    open fun dataObservable() {}

    open fun initText() {}

    protected abstract fun initActionBar()

    open fun initAds() {}

    protected val loadingDialog: WaitingDialog by lazy {
        WaitingDialog(this)
    }
    protected val sharePreference: SharePreferenceHelper by lazy {
        SharePreferenceHelper(this)
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LanguageHelper.wrapContext(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = setViewBinding()
        setContentView(binding.root)
        setUpUI()

    }

    private fun setUpUI() {
        initView()
        if (!SoundHelper.isSoundNotNull(R.raw.touch)) {
            SoundHelper.loadSound(this, R.raw.touch)
        }
        initAds()
        dataObservable()
        viewListener()
        initText()
        initActionBar()
    }

    override fun onResume() {
        super.onResume()
        hideNavigation()
    }

    @SuppressLint("MissingSuperCall", "GestureBackNavigation")
    override fun onBackPressed() {
        handleBackLeftToRight()
    }

    suspend fun showLoading() {
        withContext(Dispatchers.Main) {
            if (loadingDialog.isShowing.not()) {
                loadingDialog.show()
            }
        }
    }


    suspend fun dismissLoading(isBlack: Boolean = false) {
        withContext(Dispatchers.Main) {
            if (loadingDialog.isShowing) {
                loadingDialog.dismiss()
                hideNavigation(isBlack)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
