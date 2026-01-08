package com.xylophone.activity_app.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xylophone.data.model.Song
import com.xylophone.databinding.ItemSongBinding

class SongAdapter(
    private var songs: List<Song>,
    private val onSongClick: (Song) -> Unit
) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    inner class SongViewHolder(private val binding: ItemSongBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song) {
            binding.apply {
                tvSongName.text = song.name
                tvSongDescription.text = song.description
                tvNoteCount.text = "${song.getTotalNotes()} notes"
                tvDifficulty.text = song.difficulty.displayName
                tvDifficulty.setBackgroundColor(song.difficulty.color)

                root.setOnClickListener {
                    onSongClick(song)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val binding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songs[position])
    }

    override fun getItemCount(): Int = songs.size

    fun updateSongs(newSongs: List<Song>) {
        songs = newSongs
        notifyDataSetChanged()
    }
}
