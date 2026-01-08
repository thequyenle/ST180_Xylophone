package com.xylophone.activity_app.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.ImageView
import com.xylophone.R
import com.xylophone.core.base.BaseActivity
import com.xylophone.core.helper.SoundHelper
import com.xylophone.databinding.ActivityPlayBinding

class PlayActivity : BaseActivity<ActivityPlayBinding>() {

    // Track button đã phát cho từng ngón tay (pointer)
    private val lastPlayedButtonMap = mutableMapOf<Int, ImageView?>()
    private val buttonSoundMap = mutableMapOf<ImageView, Int>()

    override fun setViewBinding(): ActivityPlayBinding {
        return ActivityPlayBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        loadSounds()
        setupButtonMap()
        setupSwipeListener()
    }

    private fun loadSounds() {
        // Load các file âm thanh cho từng nốt nhạc
        val soundResources = listOf(
            R.raw.note_do,
            R.raw.note_re,
            R.raw.note_mi,
            R.raw.note_fa,
            R.raw.note_sol,
            R.raw.note_la,
            R.raw.note_si,
            R.raw.note_do2
        )

        soundResources.forEach { resId ->
            try {
                if (!SoundHelper.isSoundNotNull(resId)) {
                    SoundHelper.loadSound(this, resId)
                }
            } catch (e: Exception) {
                // Bỏ qua nếu file âm thanh không tồn tại
                e.printStackTrace()
            }
        }
    }

    private fun setupButtonMap() {
        binding.apply {
            buttonSoundMap[btnDo] = R.raw.note_do
            buttonSoundMap[btnRe] = R.raw.note_re
            buttonSoundMap[btnMi] = R.raw.note_mi
            buttonSoundMap[btnFa] = R.raw.note_fa
            buttonSoundMap[btnSol] = R.raw.note_sol
            buttonSoundMap[btnLa] = R.raw.note_la
            buttonSoundMap[btnSi] = R.raw.note_si
            buttonSoundMap[btnDo2] = R.raw.note_do2
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupSwipeListener() {
        // Tạo touch listener hỗ trợ multi-touch
        val touchListener = { view: android.view.View, event: MotionEvent ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    // Ngón tay đầu tiên chạm xuống
                    val pointerId = event.getPointerId(0)
                    handleTouchEvent(event.rawX, event.rawY, pointerId)
                    true
                }
                MotionEvent.ACTION_POINTER_DOWN -> {
                    // Ngón tay thứ 2, 3, 4... chạm xuống
                    val pointerIndex = event.actionIndex
                    val pointerId = event.getPointerId(pointerIndex)
                    // Tính raw coordinates cho pointer này
                    val x = event.getX(pointerIndex) + (event.rawX - event.x)
                    val y = event.getY(pointerIndex) + (event.rawY - event.y)
                    handleTouchEvent(x, y, pointerId)
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    // Xử lý tất cả các ngón tay đang di chuyển
                    for (i in 0 until event.pointerCount) {
                        val pointerId = event.getPointerId(i)
                        // Tính raw coordinates cho mỗi pointer
                        val x = event.getX(i) + (event.rawX - event.x)
                        val y = event.getY(i) + (event.rawY - event.y)
                        handleTouchEvent(x, y, pointerId)
                    }
                    true
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    // Một ngón tay được nhấc lên (còn ngón khác)
                    val pointerIndex = event.actionIndex
                    val pointerId = event.getPointerId(pointerIndex)
                    lastPlayedButtonMap.remove(pointerId)
                    true
                }
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {
                    // Ngón tay cuối cùng nhấc lên hoặc bị cancel
                    lastPlayedButtonMap.clear()
                    false // Cho phép click event hoạt động
                }
                else -> false
            }
        }

        // Gắn touch listener cho từng button
        binding.apply {
            btnDo.setOnTouchListener(touchListener)
            btnRe.setOnTouchListener(touchListener)
            btnMi.setOnTouchListener(touchListener)
            btnFa.setOnTouchListener(touchListener)
            btnSol.setOnTouchListener(touchListener)
            btnLa.setOnTouchListener(touchListener)
            btnSi.setOnTouchListener(touchListener)
            btnDo2.setOnTouchListener(touchListener)

            // Cũng gắn cho container để bắt swipe giữa các nút
            frameMusic.setOnTouchListener(touchListener)
        }
    }

    private fun handleTouchEvent(x: Float, y: Float, pointerId: Int) {
        // Tìm button đang được chạm
        var foundButton: ImageView? = null
        var foundSoundId: Int? = null

        buttonSoundMap.forEach { (button, soundId) ->
            if (isTouchingButton(button, x, y)) {
                foundButton = button
                foundSoundId = soundId
                return@forEach
            }
        }

        // Nếu tìm thấy button và button khác với button trước đó
        if (foundButton != null && foundSoundId != null) {
            if (lastPlayedButtonMap[pointerId] != foundButton) {
                // Chỉ phát khi chuyển sang button mới
                playNoteSound(foundSoundId!!)
                lastPlayedButtonMap[pointerId] = foundButton
            }
            // Nếu cùng button → không phát lại (giữ nguyên)
        }
        // Nếu không chạm vào button nào → không xóa lastPlayedButtonMap
        // Chỉ xóa khi nhấc tay lên (ACTION_UP/POINTER_UP)
    }

    private fun isTouchingButton(button: ImageView, x: Float, y: Float): Boolean {
        val location = IntArray(2)
        button.getLocationOnScreen(location)
        val buttonX = location[0]
        val buttonY = location[1]
        val buttonWidth = button.width
        val buttonHeight = button.height

        return x >= buttonX && x <= buttonX + buttonWidth &&
                y >= buttonY && y <= buttonY + buttonHeight
    }

    override fun viewListener() {
        // Touch listener đã xử lý cả click và swipe
        // Không cần thêm click listener riêng
    }

    private fun playNoteSound(resId: Int) {
        try {
            SoundHelper.playSound(resId)
        } catch (e: Exception) {
            // Bỏ qua nếu âm thanh không phát được
            e.printStackTrace()
        }
    }

    override fun initText() {
        // Không cần text đặc biệt cho màn hình này
    }

    override fun initActionBar() {
        // Action bar đã được thiết kế trong layout
        // Không cần thêm logic
    }

    override fun onDestroy() {
        super.onDestroy()
        // SoundHelper sẽ được release khi app đóng
    }
}
