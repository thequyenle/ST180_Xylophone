package com.xylophone.learning.music.activity_app.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xylophone.learning.music.R
import com.xylophone.learning.music.core.extensions.select
import com.xylophone.learning.music.databinding.ItemRecordingBinding
import com.xylophone.learning.music.data.model.Recording

class RecordingAdapter(
    private var recordings: MutableList<Recording>,
    private val onItemClick: (Recording?) -> Unit
) : RecyclerView.Adapter<RecordingAdapter.RecordingViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    fun getItemAt(position: Int) = recordings.getOrNull(position)
    fun getSelectedRecording(): Recording? {
        return if(selectedPosition in recordings.indices) recordings[selectedPosition] else null
    }

    inner class RecordingViewHolder(private val binding: ItemRecordingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recording: Recording, isSelected: Boolean) {
            binding.apply {
                tvRecordingName.text = recording.name
                tvRecordingName.select()
                root.setBackgroundResource(
                    if(isSelected) R.drawable.bg_btn_selected_recording
                    else R.drawable.bg_btn_unselected_recording
                )

                root.setOnClickListener {
                    val oldPos = selectedPosition
                    val newPos = bindingAdapterPosition
                    if(newPos == RecyclerView.NO_POSITION) return@setOnClickListener
                    selectedPosition = newPos
                    if(oldPos!= RecyclerView.NO_POSITION) notifyItemChanged(oldPos)
                    notifyItemChanged(newPos)
                    onItemClick(getSelectedRecording())
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
        holder.bind(recordings[position], isSelected = position==selectedPosition)
    }

    override fun getItemCount() = recordings.size

    fun updateRecordings(newRecordings: MutableList<Recording>) {
        recordings = newRecordings
        selectedPosition = RecyclerView.NO_POSITION
        notifyDataSetChanged()
    }
}
