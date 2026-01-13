package com.xylophone.activity_app.main

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.xylophone.R
import com.xylophone.core.base.BaseActivity
import com.xylophone.core.extensions.handleBackLeftToRight
import com.xylophone.core.extensions.setOnSingleClick
import com.xylophone.core.extensions.visible
import com.xylophone.data.model.Song
import com.xylophone.data.model.SongLibrary
import com.xylophone.databinding.ActivitySongListBinding

class SongListActivity : BaseActivity<ActivitySongListBinding>() {

    private lateinit var songAdapter: SongAdapter
    private var currentFilter: Song.Difficulty? = null

    override fun setViewBinding(): ActivitySongListBinding {
        return ActivitySongListBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        setupRecyclerView()
        loadSongs()
        setupFilters()
    }

    private fun setupRecyclerView() {
        songAdapter = SongAdapter(
            songs = emptyList(),
            onSongClick = { song ->
                // Mở PlayActivity với learning mode
                val intent = Intent(this, PlayActivity::class.java)
                intent.putExtra("mode", "LEARNING_MODE")
                intent.putExtra("song_id", song.id)
                startActivity(intent)
            }
        )

        binding.recyclerViewSongs.apply {
            layoutManager = LinearLayoutManager(this@SongListActivity)
            adapter = songAdapter
        }
    }

    private fun loadSongs(difficulty: Song.Difficulty? = null) {
        val songs = if (difficulty == null) {
            SongLibrary.getAllSongs()
        } else {
            SongLibrary.getSongsByDifficulty(difficulty)
        }

        if (songs.isEmpty()) {
            binding.tvEmptyState.isVisible = true
            binding.recyclerViewSongs.isVisible = false
        } else {
            binding.tvEmptyState.isVisible = false
            binding.recyclerViewSongs.isVisible = true
            songAdapter.updateSongs(songs)
        }
    }

    private fun setupFilters() {
        binding.apply {
            btnFilterAll.setOnSingleClick {
                currentFilter = null
                loadSongs()
                updateFilterUI()
            }


        }
    }

    private fun updateFilterUI() {
        binding.apply {
            // Reset all filters

            // Highlight selected filter

        }
    }

    override fun viewListener() {
        binding.apply {
            actionBar.btnActionBarLeft.setOnSingleClick {
                handleBackLeftToRight()
            }
        }
    }

    override fun initText() {
        // Text already set in layout
    }

    override fun initActionBar() {
        binding.actionBar.apply {
            tvCenter.text = "Learn Songs"
            tvCenter.visible()
            btnActionBarLeft.setImageResource(R.drawable.ic_back)
            btnActionBarLeft.visible()
        }
    }
}
