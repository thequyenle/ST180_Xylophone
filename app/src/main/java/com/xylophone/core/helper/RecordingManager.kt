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
    private val gson = Gson()

    // Bắt đầu recording
    fun startRecording() {
        isRecording = true
        recordingStartTime = System.currentTimeMillis()
        currentNotes.clear()
    }

    // Dừng recording và lưu
    fun stopRecording(context: Context, name: String = "Recording"): Recording? {
        if (!isRecording) return null

        isRecording = false
        val duration = System.currentTimeMillis() - recordingStartTime

        val recording = Recording(
            id = UUID.randomUUID().toString(),
            name = "$name ${getRecordingCount(context) + 1}",
            duration = duration,
            notes = currentNotes.toList(),
            createdAt = System.currentTimeMillis()
        )

        saveRecording(context, recording)
        currentNotes.clear()
        return recording
    }

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
