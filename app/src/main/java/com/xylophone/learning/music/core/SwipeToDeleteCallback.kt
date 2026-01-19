package com.xylophone.learning.music.core

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.xylophone.learning.music.R

abstract class SwipeToDeleteCallback : ItemTouchHelper.SimpleCallback(
    0,
    ItemTouchHelper.LEFT
) {
    private val background = ColorDrawable(Color.TRANSPARENT)
    private var deleteIcon: Drawable? = null

    // Lưu vùng icon để detect tap
    private var deleteButtonBounds: android.graphics.Rect? = null

    // Giới hạn độ mở (px)
    private val buttonWidth = 60  // bạn chỉnh theo ý

    // Icon size (px) - adjust these values to resize the icon
    private val iconSize = 60  // Change this to make icon bigger or smaller

    // Track which position is currently swiped open
    private var swipedPosition: Int = RecyclerView.NO_POSITION

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ) = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Close previous swipe if there was one
        val previousPosition = swipedPosition
        val newPosition = viewHolder.bindingAdapterPosition

        if (previousPosition != RecyclerView.NO_POSITION && previousPosition != newPosition) {
            viewHolder.itemView.parent?.let { parent ->
                if (parent is RecyclerView) {
                    parent.adapter?.notifyItemChanged(previousPosition)
                }
            }
        }

        // Keep track of newly swiped position
        swipedPosition = newPosition
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        // Lower threshold so swipe triggers more easily
        return 0.3f
    }

    override fun getSwipeEscapeVelocity(defaultValue: Float): Float {
        return Float.MAX_VALUE
    }

    override fun getSwipeVelocityThreshold(defaultValue: Float): Float {
        return Float.MAX_VALUE
    }

    fun closeSwipe(recyclerView: RecyclerView) {
        if (swipedPosition != RecyclerView.NO_POSITION) {
            val temp = swipedPosition
            swipedPosition = RecyclerView.NO_POSITION
            recyclerView.adapter?.notifyItemChanged(temp)
        }
    }

    fun getSwipedPosition(): Int = swipedPosition

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView

        if (deleteIcon == null) {
            deleteIcon = ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_delete)
        }
        val icon = deleteIcon ?: return

        // Convert dp to pixels for proper scaling across devices
        val density = recyclerView.context.resources.displayMetrics.density
        val buttonWidthPx = (buttonWidth * density).toInt()
        val iconSizePx = (iconSize * density).toInt()

        // If this item is swiped, keep it at the swiped position
        val currentPos = viewHolder.bindingAdapterPosition
        val finalDx = if (currentPos == swipedPosition && !isCurrentlyActive) {
            -buttonWidthPx.toFloat()
        } else {
            dX.coerceIn(-buttonWidthPx.toFloat(), 0f)
        }

        if (finalDx < 0) {
            // Nền đỏ (cố định width = buttonWidth) ở bên phải
            val bgLeft = itemView.right - buttonWidthPx
            val bgRight = itemView.right
            background.setBounds(bgLeft, itemView.top, bgRight, itemView.bottom)
            background.draw(c)

            // Icon nằm bên phải, canh giữa
            val iconMargin = (itemView.height - iconSizePx) / 2
            val iconTop = itemView.top + iconMargin
            val iconBottom = iconTop + iconSizePx

            val iconRight = itemView.right - iconMargin
            val iconLeft = iconRight - iconSizePx

            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            icon.draw(c)

            // ✅ Store bounds in RecyclerView coordinates (where icon is actually drawn)
            deleteButtonBounds = android.graphics.Rect(iconLeft, iconTop, iconRight, iconBottom)
        } else {
            deleteButtonBounds = null
            background.setBounds(0, 0, 0, 0)
        }

        super.onChildDraw(c, recyclerView, viewHolder, finalDx, dY, actionState, isCurrentlyActive)
    }



    fun getDeleteButtonBounds(): android.graphics.Rect? = deleteButtonBounds
}
