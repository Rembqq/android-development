package com.example.lab5

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import kotlin.math.atan2
import kotlin.math.roundToInt

class SensorView(context: Context) : View(context) {

    private var x = 0f
    private var y = 0f

    private val paintLine = Paint().apply {
        color = Color.GREEN
        strokeWidth = 8f
    }

    private val paintText = Paint().apply {
        color = Color.BLACK
        textSize = 50f
    }

    fun updateSensorValues(x: Float, y: Float) {
        this.x = x
        this.y = y
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        // Кут нахилу
        val angle = atan2(y, x) * (180 / Math.PI)

        // Лінія горизонту (повернута)
        canvas.save()
        canvas.rotate(-angle.toFloat(), centerX, centerY)
        canvas.drawLine(0f, centerY, width.toFloat(), centerY, paintLine)
        canvas.restore()

        // Текст з кутом
        canvas.drawText("Кут нахилу: ${angle.roundToInt()}°", 40f, 100f, paintText)
    }
}
