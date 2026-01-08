package com.xylophone.activity_app.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xylophone.databinding.ItemRecordingBinding
import com.xylophone.data.model.Recording

class RecordingAdapter(
    private var recordings: List<Recording>,
    private val onItemClick: (Recording) -> Unit,
    private val onDeleteClick: (Recording) -> Unit
) : RecyclerView.Adapter<RecordingAdapter.RecordingViewHolder>() {

    inner class RecordingViewHolder(private val binding: ItemRecordingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recording: Recording) {
            binding.apply {
                tvRecordingName.text = recording.name
                tvRecordingDuration.text = formatDuration(recording.duration)
                tvRecordingNotes.text = "${recording.notes.size} notes"

                root.setOnClickListener {
                    onItemClick(recording)
                }

                btnDelete.setOnClickListener {
                    onDeleteClick(recording)
                }
            }
        }

        private fun formatDuration(millis: Long): String {
            val seconds = (millis / 1000) % 60
            val minutes = (millis / 1000) / 60
            return String.format("%02d:%02d", minutes, seconds)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordingViewHolder {
        val binding = ItemRecordingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RecordingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordingViewHolder, position: Int) {
        holder.bind(recordings[position])
    }

    override fun getItemCount() = recordings.size

    fun updateRecordings(newRecordings: List<Recording>) {
        recordings = newRecordings
        notifyDataSetChanged()
    }
}
