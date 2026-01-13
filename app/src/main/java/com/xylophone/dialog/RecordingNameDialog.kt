package com.xylophone.dialog

import android.app.Activity
import android.view.inputmethod.EditorInfo
import com.xylophone.R
import com.xylophone.core.base.BaseDialog
import com.xylophone.core.extensions.gone
import com.xylophone.core.extensions.hideNavigation
import com.xylophone.core.extensions.setOnSingleClick
import com.xylophone.core.extensions.strings
import com.xylophone.data.model.Instrument
import com.xylophone.data.model.Recording
import com.xylophone.databinding.DialogRecordingNameBinding

/**
 * Custom dialog để nhập tên cho recording sau khi ghi xong
 *
 * @param context Activity context
 * @param recording Recording object chứa thông tin recording (duration, instrument, notes)
 */
class RecordingNameDialog(
    val context: Activity,
    private val recording: Recording
) : BaseDialog<DialogRecordingNameBinding>(context, maxWidth = true, maxHeight = true) {

    override val layoutId: Int = R.layout.dialog_recording_name
    override val isCancelOnTouchOutside: Boolean = false  // Không cho dismiss khi tap ngoài
    override val isCancelableByBack: Boolean = false      // Không cho dismiss khi bấm back

    // Callbacks
    var onSaveClick: ((String) -> Unit) = {}     // Callback khi user bấm Save
    var onCancelClick: (() -> Unit) = {}         // Callback khi user bấm Cancel

    override fun initView() {
        setupRecordingInfo()
        setupButtons()
        setupEditText()
        context.hideNavigation()
    }

    /**
     * Setup thông tin recording (duration, instrument)
     */
    private fun setupRecordingInfo() {
        binding.apply {
            // Format duration thành mm:ss

            // Set default name với số thứ tự
            etRecordingName.setText("My Recording")
            etRecordingName.setSelection(etRecordingName.text.length)  // Đặt cursor ở cuối
        }
    }

    /**
     * Setup buttons (Cancel / Save)
     */
    private fun setupButtons() {
        binding.flBottom.apply {
            // Ẩn icons, chỉ hiển thị text

            // Set text cho buttons
            tvBottomLeft.text = context.strings(R.string.cancel)
            tvBottomRight.text = context.strings(R.string.save)
        }
    }

    /**
     * Setup EditText behavior
     */
    private fun setupEditText() {
        binding.etRecordingName.apply {
            // Khi user bấm Done trên keyboard → trigger Save
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handleSave()
                    true
                } else {
                    false
                }
            }

            // Request focus và show keyboard
            requestFocus()
        }
    }

    override fun initAction() {
        binding.apply {
            // Cancel button
            flBottom.btnBottomLeft.setOnSingleClick {
                onCancelClick.invoke()
                dismiss()
            }

            // Save button
            flBottom.btnBottomRight.setOnSingleClick {
                handleSave()
            }

            // Không cho dismiss khi tap vào background
            flOutSide.setOnSingleClick {
                // Do nothing - force user to choose Cancel or Save
            }
        }
    }

    /**
     * Xử lý logic Save:
     * 1. Validate tên không rỗng
     * 2. Gọi callback với tên đã nhập
     * 3. Dismiss dialog
     */
    private fun handleSave() {
        val name = binding.etRecordingName.text.toString().trim()

        if (name.isEmpty()) {
            // Nếu tên rỗng → set default name
            val defaultName = "My Recording"
            onSaveClick.invoke(defaultName)
        } else {
            onSaveClick.invoke(name)
        }

        dismiss()
    }

    /**
     * Format duration từ milliseconds sang mm:ss
     */
    private fun formatDuration(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / 1000) / 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onDismissListener() {
        // Cleanup nếu cần
    }
}
