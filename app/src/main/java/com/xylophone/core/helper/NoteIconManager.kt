package com.xylophone.core.helper

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import com.xylophone.R
import com.xylophone.core.extensions.gone
import com.xylophone.core.extensions.visible
import kotlin.random.Random

/**
 * Manages musical note icon overlays for touch feedback
 * Generates 2-5 random icons that fly slowly in all directions
 * Child-friendly animation: slow (3-4 seconds), gentle, easy to see and enjoy
 */
class NoteIconManager(
    private val context: Context,
    private val overlayContainer: FrameLayout
) {
    // Track all active icons currently animating
    private val activeIcons = mutableListOf<ImageView>()

    // Recycled icons ready for reuse
    private val iconPool = mutableListOf<ImageView>()

    // Icon size (responsive to screen density)
    private val iconSizePx = (48 * context.resources.displayMetrics.density).toInt()

    // Handler for auto-removal
    private val handler = Handler(Looper.getMainLooper())

    /**
     * Show 2-5 random icons that burst from button center in all directions (360°)
     * Like fireworks spreading outward evenly
     */
    fun showIcon(pointerId: Int, button: ImageView) {
        // Check if we have too many active icons
        if (activeIcons.size >= MAX_ACTIVE_ICONS) {
            Log.d(TAG, "Too many active icons (${activeIcons.size}), skipping")
            return
        }

        // Randomly generate 2-5 icons
        val iconCount = Random.nextInt(2, 6)

        // Create random base angle for rotation variety
        val baseAngle = (System.nanoTime() % 360000).toFloat() / 1000f

        // Spread icons evenly in full 360° circle
        val angleSpread = 360f / iconCount

        Log.d(TAG, "Generating $iconCount icons bursting from center, baseAngle=$baseAngle, spread=$angleSpread")

        repeat(iconCount) { index ->
            // Each icon spread evenly around full circle with small random offset
            val randomOffset = (Random.nextFloat() * 20f - 10f)
            val angle = (baseAngle + (index * angleSpread) + randomOffset) % 360f

            Log.d(TAG, "Icon $index: angle=$angle (base=$baseAngle + index*spread=${index * angleSpread} + offset=$randomOffset)")

            createAndAnimateIcon(button, angle)
        }
    }

    /**
     * Hide icon for a specific pointer (legacy method - now does nothing since icons auto-remove)
     */
    fun hideIcon(pointerId: Int) {
        // Icons now auto-remove after 2 seconds, so this method does nothing
    }

    /**
     * Hide all icons (e.g., when touch is cancelled)
     */
    fun hideAllIcons() {
        // Copy list to avoid concurrent modification
        val iconsToRemove = activeIcons.toList()
        iconsToRemove.forEach { icon ->
            icon.animate().cancel()
            removeIcon(icon)
        }
    }

    /**
     * Create and animate a single flying icon at specified angle
     */
    private fun createAndAnimateIcon(button: ImageView, angle: Float) {
        // Get or create icon from pool
        val icon = if (iconPool.isNotEmpty()) {
            iconPool.removeAt(iconPool.size - 1)
        } else {
            createIcon()
        }

        // Add to active list
        activeIcons.add(icon)

        // Get button position relative to overlay container
        val buttonLocation = IntArray(2)
        val containerLocation = IntArray(2)
        button.getLocationOnScreen(buttonLocation)
        overlayContainer.getLocationOnScreen(containerLocation)

        val relativeX = buttonLocation[0] - containerLocation[0]
        val relativeY = buttonLocation[1] - containerLocation[1]

        // Starting position (center of button)
        val startX = relativeX + (button.width / 2f) - (iconSizePx / 2f)
        val startY = relativeY + (button.height / 2f) - (iconSizePx / 2f)

        // Use the provided angle
        val angleRadians = Math.toRadians(angle.toDouble())

        // Distance (dp -> px) so icons stay on screen
        val flyDistance = Random.nextInt(150, 251) * context.resources.displayMetrics.density

        // Calculate movement based on angle
        val deltaX = (flyDistance * Math.cos(angleRadians)).toFloat()
        val deltaY = (flyDistance * Math.sin(angleRadians)).toFloat()

        // IMPORTANT: cancel any previous animation & reset transforms (because we reuse from pool)
        icon.animate().cancel()
        icon.translationX = 0f
        icon.translationY = 0f
        icon.rotation = 0f
        icon.alpha = 1f
        icon.scaleX = 1f
        icon.scaleY = 1f

        // Set initial position
        icon.x = startX
        icon.y = startY
        icon.visible()
        icon.scaleX = 0.5f
        icon.scaleY = 0.5f
        icon.rotation = Random.nextFloat() * 360f

        Log.d(
            TAG,
            "Icon: angle=$angle°, distance=$flyDistance, deltaX=$deltaX, deltaY=$deltaY, start=($startX,$startY)"
        )

        // Slow flight for children (4-6 seconds)
        val flightDuration = Random.nextLong(4000, 6001)

        // KEY FIX: use translationXBy / translationYBy so it moves relative from start position
        icon.animate()
            .scaleX(1.5f)
            .scaleY(1.5f)
            .translationXBy(deltaX)
            .translationYBy(deltaY)
            .rotation(icon.rotation + Random.nextInt(-180, 181))
            .setDuration(flightDuration)
            .withEndAction { removeIcon(icon) }
            .start()

        // Fade out near the end
        val fadeStartTime = flightDuration - 1500
        handler.postDelayed({
            if (activeIcons.contains(icon)) {
                icon.animate()
                    .alpha(0f)
                    .setDuration(1500)
                    .start()
            }
        }, fadeStartTime)
    }

    /**
     * Remove icon and return to pool
     */
    private fun removeIcon(icon: ImageView) {
        activeIcons.remove(icon)
        icon.gone()

        // Reset all transformations for reuse
        icon.translationX = 0f
        icon.translationY = 0f
        icon.rotation = 0f
        icon.scaleX = 1f
        icon.scaleY = 1f

        if (iconPool.size < MAX_POOL_SIZE) {
            iconPool.add(icon)
        } else {
            overlayContainer.removeView(icon)
        }

        Log.d(TAG, "Icon removed, active count: ${activeIcons.size}")
    }

    /**
     * Create a new icon ImageView
     */
    private fun createIcon(): ImageView {
        return ImageView(context).apply {
            layoutParams = FrameLayout.LayoutParams(iconSizePx, iconSizePx)
            setImageResource(R.drawable.ic_note)
            scaleType = ImageView.ScaleType.FIT_CENTER
            elevation = 10f // Ensure it renders above buttons
            gone()
            overlayContainer.addView(this)
        }
    }

    companion object {
        private const val TAG = "NoteIconManager"
        private const val MAX_POOL_SIZE = 50 // Pool up to 50 icons for reuse
        private const val MAX_ACTIVE_ICONS = 30 // Limit total active icons to prevent performance issues
    }
}
