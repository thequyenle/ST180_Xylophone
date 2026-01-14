package com.xylophone.activity_app.main

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.xylophone.R
import com.xylophone.core.base.BaseActivity
import com.xylophone.core.custom.text.DoubleStrokeTextView
import com.xylophone.core.extensions.gone
import com.xylophone.core.extensions.handleBackLeftToRight
import com.xylophone.core.extensions.setOnSingleClick
import com.xylophone.core.extensions.visible
import com.xylophone.data.model.Song
import com.xylophone.data.model.SongLibrary
import com.xylophone.databinding.ActivitySongListBinding

class SongListActivity : BaseActivity<ActivitySongListBinding>() {


    private fun dp(dp: Float): Float = dp * resources.displayMetrics.density

    private var selectedSong: Song? = null
    private lateinit var songAdapter: SongAdapter
    private var currentFilter: Song.Difficulty? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        Toast.makeText(this, "SongListActivity onCreate called", Toast.LENGTH_SHORT).show()
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "SongListActivity onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "SongListActivity onPause", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "SongListActivity onDestroy", Toast.LENGTH_SHORT).show()
    }

    override fun setViewBinding(): ActivitySongListBinding {
        return ActivitySongListBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        Toast.makeText(this, "SongListActivity starting", Toast.LENGTH_SHORT).show()
        try {
            setupRecyclerView()
            loadSongs()
            setupFilters()
            Toast.makeText(this, "SongListActivity initialized", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Error in initView: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    private fun setupRecyclerView() {
        songAdapter = SongAdapter(
            songs = emptyList(),
            onSongClick = { song ->
                selectedSong = song
            }
        )

        binding.recyclerViewSongs.apply {
            layoutManager = LinearLayoutManager(this@SongListActivity)
            adapter = songAdapter
        }
    }

    private fun loadSongs(difficulty: Song.Difficulty? = null) {
        val songs = if (difficulty == null) {
            SongLibrary.getallSongs()
        } else {
            SongLibrary.getSongsByDifficulty(difficulty)
        }

        if (songs.isEmpty()) {

            binding.recyclerViewSongs.isVisible = false
        } else {

            binding.recyclerViewSongs.isVisible = true
            songAdapter.updateSongs(songs)
        }
    }


    private fun setTopMargin(view: android.view.View, topDp: Int) {
        val lp = view.layoutParams as? LinearLayout.LayoutParams ?: return
        lp.topMargin = (topDp * resources.displayMetrics.density).toInt()
        view.layoutParams = lp
    }


    private fun setupFilters() {
        binding.apply {
            allSong.setOnSingleClick {
                selecTab(true)
            }

            myRecord.setOnSingleClick {
                selecTab(false)
            }

        }
        selecTab(true)
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
            actionBar.btnActionBarRight.setOnSingleClick {
                selectedSong?.let { song ->
                    val intent = Intent(this@SongListActivity, PlayActivity::class.java)
                    intent.putExtra("mode", "LEARNING_MODE")
                    intent.putExtra("song_id", song.id)
                    startActivity(intent)
                }
            }
        }
    }

    override fun initText() {
        // Text already set in layout
    }

    private fun selecTab(isSongTab: Boolean){
        if(isSongTab) {
            binding.tabMode.setBackgroundResource(R.drawable.tab_selected_song)
            applySelectedTab(binding.allSong)
            applyUnselectedTab(binding.myRecord)
            loadSongs()
        }else{
            binding.tabMode.setBackgroundResource(R.drawable.tab_selected_record)
            applySelectedTab(binding.myRecord)
            applyUnselectedTab(binding.allSong)

        }
    }

    private fun applySelectedTab(tab: DoubleStrokeTextView) {
        // text style
        tab.setTextColor(getColor(R.color.white))
        tab.textSize = 24f

        // weight
        (tab.layoutParams as? LinearLayout.LayoutParams)?.let {
            it.weight = 1.4f
            tab.layoutParams = it
        }

        setTopMargin(tab,0)

        // stroke
        tab.setDoubleStroke(
            outerColor = Color.parseColor("#E47E17"),
            outerWidth = dp(4f),                  // 4dp
            innerColor = Color.TRANSPARENT,
            innerWidth = 0f,
            join = Paint.Join.ROUND,
            miter = 10f
        )

        tab.requestLayout()
    }

    private fun applyUnselectedTab(tab: DoubleStrokeTextView) {

        tab.setTextColor(Color.parseColor("#808080"))
        tab.textSize = 16f

        (tab.layoutParams as? LinearLayout.LayoutParams)?.let {
            it.weight = 0.6f
            tab.layoutParams = it
        }

        setTopMargin(tab, 6) // unselected: marginTop 6dp như XML


        tab.setDoubleStroke(
            outerColor = Color.TRANSPARENT,
            outerWidth = 0f,
            innerColor = Color.TRANSPARENT,
            innerWidth = 0f,
            join = Paint.Join.ROUND,
            miter = 10f
        )

        tab.requestLayout()
    }


    override fun initActionBar() {
        binding.actionBar.apply {
            tvCenter.text = getString(R.string.choose_sound)
            tvCenter.visible()
            btnActionBarLeft.setImageResource(R.drawable.ic_back)
            btnActionBarLeft.visible()
            btnActionBarRightText.gone()
            btnActionBarRight.setImageResource(R.drawable.ic_done)
            btnActionBarRight.visible()
        }
    }
}
