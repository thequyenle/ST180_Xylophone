package com.xylophone.activity_app.main

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import com.xylophone.R
import com.xylophone.core.base.BaseActivity
import com.xylophone.core.extensions.animateScaleEffect
import com.xylophone.core.extensions.setOnSingleClick
import com.xylophone.core.extensions.shakeViewEffect
import com.xylophone.core.helper.RecordingManager
import com.xylophone.core.helper.SharePreferenceHelper
import com.xylophone.core.helper.SoundHelper
import com.xylophone.data.model.Instrument
import com.xylophone.data.model.Song
import com.xylophone.data.model.SongLibrary
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

    // Learning Mode
    private var isLearningMode = false
    private var currentSong: Song? = null
    private var currentNoteIndex = 0
    private var highlightedButton: ImageView? = null

    // Instrument Selection
    private lateinit var preferenceHelper: SharePreferenceHelper
    private var currentInstrument: Instrument = Instrument.PIANO

    override fun setViewBinding(): ActivityPlayBinding {
        return ActivityPlayBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        // Initialize preference helper
        preferenceHelper = SharePreferenceHelper(this)

        // Load saved instrument
        val savedInstrument = preferenceHelper.getSelectedInstrument()
        currentInstrument = Instrument.fromName(savedInstrument)

        // Setup instrument selectors
        setupInstrumentSelectors()
        updateInstrumentUI()

        loadSounds()
        setupButtonMap()
        setupSwipeListener()
        setupRecordingUI()
        checkPlaybackMode()
        checkLearningMode()
    }

    private fun loadSounds() {
        // Load các file âm thanh cho instrument hiện tại
        val noteNames = listOf("Do", "Re", "Mi", "Fa", "Sol", "La", "Si", "Do2")

        noteNames.forEach { noteName ->
            val resId = currentInstrument.getSoundResource(noteName)
            if (resId != 0) {
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
    }

    private fun setupButtonMap() {
        binding.apply {
            // Map sounds based on current instrument
            buttonSoundMap[btnDo] = currentInstrument.getSoundResource("Do")
            buttonSoundMap[btnRe] = currentInstrument.getSoundResource("Re")
            buttonSoundMap[btnMi] = currentInstrument.getSoundResource("Mi")
            buttonSoundMap[btnFa] = currentInstrument.getSoundResource("Fa")
            buttonSoundMap[btnSol] = currentInstrument.getSoundResource("Sol")
            buttonSoundMap[btnLa] = currentInstrument.getSoundResource("La")
            buttonSoundMap[btnSi] = currentInstrument.getSoundResource("Si")
            buttonSoundMap[btnDo2] = currentInstrument.getSoundResource("Do2")

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
                // Learning Mode: validate correct note
                if (isLearningMode) {
                    handleLearningModeTouch(foundButton!!)
                } else {
                    // Normal Mode: chỉ phát khi chuyển sang button mới
                    playNoteSound(foundSoundId!!)
                    animateButton(foundButton!!)
                }
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
                // Mở SongListActivity
                val intent = android.content.Intent(this@PlayActivity, SongListActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun playNoteSound(resId: Int) {
        try {
            // Play sound với volume boost từ instrument hiện tại
            SoundHelper.playSound(resId, currentInstrument.volumeBoost)

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
                // Play sound với volume boost
                SoundHelper.playSound(note.noteId, currentInstrument.volumeBoost)

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

    // ==================== LEARNING MODE FUNCTIONS ====================

    // Kiểm tra có phải learning mode không
    private fun checkLearningMode() {
        val mode = intent.getStringExtra("mode")
        val songId = intent.getStringExtra("song_id")

        if (mode == "LEARNING_MODE" && songId != null) {
            isLearningMode = true
            currentSong = SongLibrary.getSongById(songId)
            currentNoteIndex = 0

            // Setup UI cho learning mode
            binding.apply {
                btnRecord.isVisible = false
                btnMusic.isVisible = false
                btnStop.isVisible = false
                btnCount.isVisible = true
            }

            // Hiển thị progress
            updateLearningProgress()
            // Highlight nốt đầu tiên
            highlightCurrentNote()
        }
    }

    // Xử lý touch event trong learning mode
    private fun handleLearningModeTouch(clickedButton: ImageView) {
        val song = currentSong ?: return

        // Check bounds để tránh crash
        if (currentNoteIndex >= song.notes.size) {
            return // Bài hát đã hoàn thành, ignore click
        }

        val clickedNoteName = buttonNameMap[clickedButton]
        val correctNoteName = song.notes[currentNoteIndex]

        if (clickedNoteName == correctNoteName) {
            // ✅ ĐÚNG
            val soundId = buttonSoundMap[clickedButton]
            soundId?.let { playNoteSound(it) }

            // Success animation
            clickedButton.animateScaleEffect(0.8f, 150)
            animateButton(clickedButton)

            // Move to next note
            Handler(Looper.getMainLooper()).postDelayed({
                moveToNextNote()
            }, 300)
        } else {
            // ❌ SAI - Shake animation và không phát âm
            clickedButton.shakeViewEffect(duration = 50, repeatCount = 3, shakeDistance = 10f)
            // Có thể thêm sound effect "wrong" ở đây nếu muốn
        }
    }

    // Highlight nốt hiện tại
    private fun highlightCurrentNote() {
        val song = currentSong ?: return
        if (currentNoteIndex >= song.notes.size) return

        // Reset highlight của button trước
        highlightedButton?.alpha = 1.0f
        highlightedButton?.scaleX = 1.0f
        highlightedButton?.scaleY = 1.0f

        // Tìm button cần highlight
        val correctNoteName = song.notes[currentNoteIndex]
        val buttonToHighlight = buttonNameMap.entries.find { it.value == correctNoteName }?.key

        buttonToHighlight?.let { button ->
            highlightedButton = button
            // Phóng to và làm nổi bật
            button.scaleX = 1.2f
            button.scaleY = 1.2f
            button.alpha = 1.0f

            // Pulse animation liên tục
            startPulseAnimation(button)
        }
    }

    // Animation pulse liên tục cho button được highlight
    private fun startPulseAnimation(button: ImageView) {
        val pulseAnim = AnimationUtils.loadAnimation(this, R.anim.button_pulse)
        pulseAnim.repeatCount = android.view.animation.Animation.INFINITE
        button.startAnimation(pulseAnim)
    }

    // Chuyển sang nốt tiếp theo
    private fun moveToNextNote() {
        val song = currentSong ?: return
        currentNoteIndex++

        if (currentNoteIndex >= song.notes.size) {
            // Hoàn thành bài hát
            showCompletionDialog()
        } else {
            // Highlight nốt tiếp theo
            updateLearningProgress()
            highlightCurrentNote()
        }
    }

    // Cập nhật progress
    private fun updateLearningProgress() {
        val song = currentSong ?: return
        binding.tvCount.text = "${currentNoteIndex + 1} / ${song.notes.size}"
    }

    // Hiển thị dialog hoàn thành
    private fun showCompletionDialog() {
        // Reset highlight
        highlightedButton?.apply {
            alpha = 1.0f
            scaleX = 1.0f
            scaleY = 1.0f
            clearAnimation()
        }

        Toast.makeText(this, "Congratulations! You completed the song!", Toast.LENGTH_LONG).show()

        // Delay rồi quay về
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
        }, 2000)
    }

    // ==================== END LEARNING MODE FUNCTIONS ====================

    // ==================== INSTRUMENT SWITCHING FUNCTIONS ====================

    // Setup instrument selector click listeners
    private fun setupInstrumentSelectors() {
        binding.apply {
            img1.setOnSingleClick {
                switchInstrument(Instrument.XYLOPHONE)
            }

            img2.setOnSingleClick {
                switchInstrument(Instrument.PIANO)
            }

            img3.setOnSingleClick {
                switchInstrument(Instrument.GUITAR)
            }
        }
    }

    // Switch to a new instrument
    private fun switchInstrument(newInstrument: Instrument) {
        if (currentInstrument == newInstrument) return

        currentInstrument = newInstrument

        // Save preference
        preferenceHelper.setSelectedInstrument(newInstrument.name)

        // Update UI
        updateInstrumentUI()

        // Reload sounds and button map
        SoundHelper.releaseAllSounds()
        loadSounds()
        setupButtonMap()

        // Show feedback
        Toast.makeText(this, "Switched to ${newInstrument.displayName}", Toast.LENGTH_SHORT).show()
    }

    // Update UI to show selected instrument
    private fun updateInstrumentUI() {
        binding.apply {
            // Reset all to unselected state (opacity)
            img1.alpha = 0.5f
            img2.alpha = 0.5f
            img3.alpha = 0.5f

            // Highlight selected instrument
            when (currentInstrument) {
                Instrument.XYLOPHONE -> img1.alpha = 1.0f
                Instrument.PIANO -> img2.alpha = 1.0f
                Instrument.GUITAR -> img3.alpha = 1.0f
            }
        }
    }

    // ==================== END INSTRUMENT SWITCHING FUNCTIONS ====================

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
