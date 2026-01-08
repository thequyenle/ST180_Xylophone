package com.xylophone.activity_app.main

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.core.view.isVisible
import com.xylophone.R
import com.xylophone.core.base.BaseActivity
import com.xylophone.core.extensions.setOnSingleClick
import com.xylophone.core.helper.RecordingManager
import com.xylophone.core.helper.SoundHelper
import com.xylophone.databinding.ActivityPlayBinding

class PlayActivity : BaseActivity<ActivityPlayBinding>() {

    // Track button đã phát cho từng ngón tay (pointer)
    private val lastPlayedButtonMap = mutableMapOf<Int, ImageView?>()
    private val buttonSoundMap = mutableMapOf<ImageView, Int>()
    private val buttonNameMap = mutableMapOf<ImageView, String>()

    // Recording
    private val timerHandler = Handler(Looper.getMainLooper())
    private var timerRunnable: Runnable? = null

    // Playback
    private var isPlaybackMode = false
    private var recordingId: String? = null

    override fun setViewBinding(): ActivityPlayBinding {
        return ActivityPlayBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        loadSounds()
        setupButtonMap()
        setupSwipeListener()
        setupRecordingUI()
        checkPlaybackMode()
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

            buttonNameMap[btnDo] = "Do"
            buttonNameMap[btnRe] = "Re"
            buttonNameMap[btnMi] = "Mi"
            buttonNameMap[btnFa] = "Fa"
            buttonNameMap[btnSol] = "Sol"
            buttonNameMap[btnLa] = "La"
            buttonNameMap[btnSi] = "Si"
            buttonNameMap[btnDo2] = "Do2"
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
                animateButton(foundButton!!)
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
        binding.apply {
            btnRecord.setOnSingleClick {
                startRecording()
            }

            btnStop.setOnSingleClick {
                stopRecording()
            }

            btnMusic.setOnSingleClick {
                // Placeholder cho chức năng music
            }
        }
    }

    private fun playNoteSound(resId: Int) {
        try {
            SoundHelper.playSound(resId)

            // Nếu đang recording, ghi lại nốt nhạc
            if (RecordingManager.isRecording() && !isPlaybackMode) {
                val button = buttonSoundMap.entries.find { it.value == resId }?.key
                val noteName = button?.let { buttonNameMap[it] } ?: "Unknown"
                RecordingManager.recordNote(resId, noteName)
            }
        } catch (e: Exception) {
            // Bỏ qua nếu âm thanh không phát được
            e.printStackTrace()
        }
    }

    // Animate button khi phát âm thanh
    private fun animateButton(button: ImageView) {
        try {
            // Load và start pulse animation
            val pulseAnim = AnimationUtils.loadAnimation(this, R.anim.button_pulse)
            button.startAnimation(pulseAnim)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Setup UI cho recording
    private fun setupRecordingUI() {
        binding.apply {
            btnStop.isVisible = false
            btnCount.isVisible = false
            btnRecord.isVisible = true
            btnMusic.isVisible = true
            tvCount.text = "00:00"
        }
    }

    // Kiểm tra có phải playback mode không
    private fun checkPlaybackMode() {
        recordingId = intent.getStringExtra("recording_id")
        if (recordingId != null) {
            isPlaybackMode = true
            playbackRecording()
        }
    }

    // Bắt đầu recording
    private fun startRecording() {
        RecordingManager.startRecording()

        binding.apply {
            btnRecord.isVisible = false
            btnMusic.isVisible = false
            btnStop.isVisible = true
            btnCount.isVisible = true
        }

        startTimer()
    }

    // Dừng recording
    private fun stopRecording() {
        val recording = RecordingManager.stopRecording(this, "My Recording")
        stopTimer()

        binding.apply {
            btnRecord.isVisible = true
            btnMusic.isVisible = true
            btnStop.isVisible = false
            btnCount.isVisible = false
            tvCount.text = "00:00"
        }

        // Có thể show toast hoặc dialog
        recording?.let {
            // Recording đã được lưu
        }
    }

    // Start timer
    private fun startTimer() {
        timerRunnable = object : Runnable {
            override fun run() {
                val duration = RecordingManager.getCurrentDuration()
                binding.tvCount.text = formatDuration(duration)
                timerHandler.postDelayed(this, 100)
            }
        }
        timerHandler.post(timerRunnable!!)
    }

    // Stop timer
    private fun stopTimer() {
        timerRunnable?.let {
            timerHandler.removeCallbacks(it)
        }
        timerRunnable = null
    }

    // Format duration to mm:ss
    private fun formatDuration(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / 1000) / 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    // Playback recording
    private fun playbackRecording() {
        val recording = recordingId?.let { RecordingManager.getRecording(this, it) } ?: return

        binding.apply {
            btnRecord.isVisible = false
            btnMusic.isVisible = false
            btnStop.isVisible = false
            btnCount.isVisible = true
        }

        // Play notes với timing
        val handler = Handler(Looper.getMainLooper())
        recording.notes.forEach { note ->
            handler.postDelayed({
                SoundHelper.playSound(note.noteId)

                // Tìm button tương ứng và chạy animation
                val button = buttonSoundMap.entries.find { it.value == note.noteId }?.key
                button?.let { animateButton(it) }

                // Update count
                binding.tvCount.text = formatDuration(note.timestamp)
            }, note.timestamp)
        }

        // Khi xong, reset UI
        handler.postDelayed({
            setupRecordingUI()
            isPlaybackMode = false
        }, recording.duration + 500)
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
