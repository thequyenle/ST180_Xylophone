package com.xylophone.learning.music.core.custom.text

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import com.xylophone.learning.music.R

class StrokeGradientTextView : AppCompatTextView {

    private var strokeWidthPx = 0f
    private var strokeColor: Int = Color.WHITE

    private var startColor: Int = Color.TRANSPARENT
    private var endColor: Int = Color.TRANSPARENT
    private var useGradient: Boolean = true

    private var strokeJoin: Paint.Join = Paint.Join.ROUND
    private var strokeMiter = 10f

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    private fun init(attrs: AttributeSet?) {
        if (attrs == null) return

        context.withStyledAttributes(attrs, R.styleable.StrokeTextView) {
            // Reuse styleable StrokeTextView của bạn luôn (đỡ tạo mới)
            strokeWidthPx = getDimension(R.styleable.StrokeTextView_strokeWidth, 0f)
            strokeColor = getColor(R.styleable.StrokeTextView_strokeColor, Color.WHITE)

            useGradient = getBoolean(R.styleable.StrokeTextView_useGradient, true)
            startColor = getColor(R.styleable.StrokeTextView_gradientStartColor, currentTextColor)
            endColor = getColor(R.styleable.StrokeTextView_gradientEndColor, currentTextColor)

            strokeMiter = getDimension(R.styleable.StrokeTextView_strokeMiter, 10f)
            strokeJoin = when (getInt(R.styleable.StrokeTextView_strokeJoinStyle, 2)) {
                0 -> Paint.Join.MITER
                1 -> Paint.Join.BEVEL
                else -> Paint.Join.ROUND
            }
        }

        paint.isAntiAlias = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateShader(h)
    }

    private fun updateShader(h: Int) {
        if (!useGradient || h <= 0) {
            paint.shader = null
            return
        }
        paint.shader = LinearGradient(
            0f, 0f, 0f, h.toFloat(),
            intArrayOf(startColor, endColor),
            null,
            Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas) {
        val p = paint

        val shaderBackup = p.shader
        val colorBackup = currentTextColor
        val styleBackup = p.style
        val joinBackup = p.strokeJoin
        val miterBackup = p.strokeMiter
        val strokeWidthBackup = p.strokeWidth

        // 1) Stroke trắng (không shader)
        if (strokeWidthPx > 0f) {
            p.shader = null
            p.style = Paint.Style.STROKE
            p.strokeJoin = strokeJoin
            p.strokeMiter = strokeMiter
            p.strokeWidth = strokeWidthPx
            setTextColor(strokeColor)
            super.onDraw(canvas)
        }

        // 2) Fill gradient
        p.shader = shaderBackup // shader gradient đã set ở onSizeChanged
        p.style = Paint.Style.FILL
        setTextColor(colorBackup) // color này không quan trọng khi có shader, nhưng restore cho sạch
        super.onDraw(canvas)

        // Restore
        p.shader = shaderBackup
        p.style = styleBackup
        p.strokeJoin = joinBackup
        p.strokeMiter = miterBackup
        p.strokeWidth = strokeWidthBackup
        setTextColor(colorBackup)
    }

    // Nếu muốn set bằng code:
    fun setStroke(widthPx: Float, color: Int) {
        strokeWidthPx = widthPx
        strokeColor = color
        invalidate()
    }

    fun setGradient(start: Int, end: Int) {
        startColor = start
        endColor = end
        updateShader(height)
        invalidate()
    }
}
