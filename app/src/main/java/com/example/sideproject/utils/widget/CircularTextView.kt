package com.example.sideproject.utils.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Color.parseColor
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.util.AttributeSet
import android.widget.TextView

class CircularTextView : TextView {

    private var _strokeWidth: Float = 0.toFloat()
    private var _strokeColor: Int = Color.BLACK
    private var _solidColor: Int = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun draw(canvas: Canvas) {

        val circlePaint = Paint()
        circlePaint.color = _solidColor
        circlePaint.flags = ANTI_ALIAS_FLAG

        val strokePaint = Paint()
        strokePaint.color = _strokeColor
        strokePaint.flags = ANTI_ALIAS_FLAG

        val h = this.height
        val w = this.width

        val diameter = (if (h > w) h else w).toFloat()
        val radius = diameter / 2

        this.height = diameter.toInt()
        this.width = diameter.toInt()

        canvas.drawCircle(diameter / 2, diameter / 2, radius, strokePaint)
        canvas.drawCircle(diameter / 2, diameter / 2, radius - _strokeWidth, circlePaint)

        super.draw(canvas)
    }

    fun setStrokeWidth(dp: Int) {
        val scale = context.resources.displayMetrics.density
        _strokeWidth = dp * scale
    }

    fun setStrokeColor(color: String) {
        _strokeColor = parseColor(color)
    }

    fun setSolidColor(color: String) {
        _solidColor = parseColor(color)
    }
}