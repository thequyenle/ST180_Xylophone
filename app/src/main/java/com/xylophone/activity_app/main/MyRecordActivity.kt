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
import com.xylophone.core.helper.RecordingManager
import com.xylophone.databinding.ActivityMyRecordBinding

class MyRecordActivity : BaseActivity<ActivityMyRecordBinding>() {

    private lateinit var recordingAdapter: RecordingAdapter

    override fun setViewBinding(): ActivityMyRecordBinding {
        return ActivityMyRecordBinding.inflate(LayoutInflater.from(this))
    }

    override fun initView() {
        setupRecyclerView()
        loadRecordings()
    }

    private fun setupRecyclerView() {
        recordingAdapter = RecordingAdapter(
            recordings = emptyList(),
            onItemClick = { recording ->
                // Mở PlayActivity với recording ID để playback
                val intent = Intent(this, PlayActivity::class.java)
                intent.putExtra("recording_id", recording?.id)
                startActivity(intent)
            },

        )

        binding.recyclerViewRecordings.apply {
            layoutManager = LinearLayoutManager(this@MyRecordActivity)
            adapter = recordingAdapter
        }
    }

    private fun loadRecordings() {
        val recordings = RecordingManager.getAllRecordings(this)

        if (recordings.isEmpty()) {
            binding.tvMessage.isVisible = true
            binding.recyclerViewRecordings.isVisible = false
        } else {
            binding.tvMessage.isVisible = false
            binding.recyclerViewRecordings.isVisible = true
            recordingAdapter.updateRecordings(recordings)
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
            tvCenter.text = "My Record"
            tvCenter.visible()
            btnActionBarLeft.setImageResource(R.drawable.ic_back)
            btnActionBarLeft.visible()
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload recordings khi quay lại activity
        loadRecordings()
    }
}
