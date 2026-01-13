package com.xylophone.core.helper

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xylophone.data.model.RecordedNote
import com.xylophone.data.model.Recording
import java.util.UUID

object RecordingManager {
    private const val PREFS_NAME = "recordings_prefs"
    private const val KEY_RECORDINGS = "recordings"

    private var isRecording = false
    private var recordingStartTime = 0L
    private val currentNotes = mutableListOf<RecordedNote>()
    private var currentInstrument = "PIANO"  // Track instrument đang dùng khi record
    private var pendingRecording: Recording? = null  // Recording chưa được lưu (đợi user đặt tên)
    private val gson = Gson()

    // Bắt đầu recording
    fun startRecording(instrumentName: String = "PIANO") {
        isRecording = true
        recordingStartTime = System.currentTimeMillis()
        currentNotes.clear()
        currentInstrument = instrumentName  // Lưu instrument đang dùng
    }

    // Dừng recording (CHƯA lưu - chờ user đặt tên)
    fun stopRecording(): Recording? {
        if (!isRecording) return null

        isRecording = false
        val duration = System.currentTimeMillis() - recordingStartTime

        // Tạo recording tạm thời (pending)
        pendingRecording = Recording(
            id = UUID.randomUUID().toString(),
            name = "Untitled",  // Tên tạm, sẽ được thay đổi khi user nhập
            duration = duration,
            notes = currentNotes.toList(),
            createdAt = System.currentTimeMillis(),
            instrumentName = currentInstrument
        )

        currentNotes.clear()
        return pendingRecording
    }

    // Lưu pending recording với tên custom
    fun savePendingRecording(context: Context, name: String): Recording? {
        val recording = pendingRecording ?: return null

        // Tạo recording mới với tên đã nhập
        val namedRecording = recording.copy(name = name)

        saveRecording(context, namedRecording)
        pendingRecording = null  // Clear pending
        return namedRecording
    }

    // Hủy pending recording (user cancel dialog)
    fun discardPendingRecording() {
        pendingRecording = null
    }

    // Get pending recording
    fun getPendingRecording(): Recording? = pendingRecording

    // Ghi lại một nốt nhạc
    fun recordNote(noteId: Int, noteName: String) {
        if (!isRecording) return

        val timestamp = System.currentTimeMillis() - recordingStartTime
        currentNotes.add(RecordedNote(timestamp, noteId, noteName))
    }

    // Kiểm tra có đang recording không
    fun isRecording() = isRecording

    // Lấy thời gian recording hiện tại
    fun getCurrentDuration(): Long {
        return if (isRecording) {
            System.currentTimeMillis() - recordingStartTime
        } else 0L
    }

    // Lưu recording vào SharedPreferences
    private fun saveRecording(context: Context, recording: Recording) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val recordings = getAllRecordings(context).toMutableList()
        recordings.add(recording)

        val json = gson.toJson(recordings)
        prefs.edit().putString(KEY_RECORDINGS, json).apply()
    }

    // Lấy tất cả recordings
    fun getAllRecordings(context: Context): List<Recording> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_RECORDINGS, null) ?: return emptyList()

        return try {
            val type = object : TypeToken<List<Recording>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Lấy số lượng recordings
    private fun getRecordingCount(context: Context): Int {
        return getAllRecordings(context).size
    }

    // Xóa recording
    fun deleteRecording(context: Context, recordingId: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val recordings = getAllRecordings(context).filter { it.id != recordingId }

        val json = gson.toJson(recordings)
        prefs.edit().putString(KEY_RECORDINGS, json).apply()
    }

    // Lấy recording theo ID
    fun getRecording(context: Context, recordingId: String): Recording? {
        return getAllRecordings(context).find { it.id == recordingId }
    }
}
