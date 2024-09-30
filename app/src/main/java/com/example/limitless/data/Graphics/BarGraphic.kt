package com.example.limitless.data.Graphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.collections.ArrayList
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.round

class BarGraphic @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var width: Float = 0f
    private var height: Float = 0f
    private var goal: Float = 0f
    private var barHeight: Float = 0f
    private var progress: Float = 0f
    private var paint = Paint()
    private var textPaint = Paint()
    private var thickness: Float = 10f
    private var level: Int = 0
    private var type: Int = 0
    private var barData = Array(7) { 0f }
    private val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    private var maxGoal = 0f
    private var minGoal = 0f

    private val labelPaint = Paint().apply { color = 0xFF000000.toInt()
        textSize = 40f
        isFakeBoldText = true
    }
    private val barPaint = Paint().apply { color = (0xFFAEEDA4).toInt() }
    private val gridPaint = Paint().apply { color = 0xFF000000.toInt() }

    init {
        paint.color = Color.GREEN
        paint.style = Paint.Style.FILL
        //paint.strokeWidth = thickness // Set stroke width (adjust as needed)
        textPaint.style = Paint.Style.FILL
        textPaint.strokeWidth = 0f
        textPaint.textSize = 50f
        textPaint.color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (type == 0) {

            drawExpBar(canvas)
        } else if (type == 1) {
            drawBarChart(canvas)
        }
    }

    private fun drawBarChart(canvas: Canvas) {
        val paddingLeft = 200f
        val paddingBottom = 300f
        val paddingTop = 50f

        val barWidth = (width - paddingLeft) / (barData.size * 2)
        val chartHeight = height - (paddingBottom + paddingTop)
        val chartBottom = height - paddingBottom

        val (minY, maxY) = calcScale(barData, 5)

        // Draw Y-axis
        canvas.drawLine(paddingLeft, paddingTop, paddingLeft, chartBottom, gridPaint)


        // Draw grid lines and Y-axis labels
        val intervalCount = 5
        val range = maxY - minY

        val interval = (range / intervalCount.toFloat()).toInt()
        val adjustedInterval = ceil(interval.toDouble()).toInt()

        // Draw grid lines and Y-axis labels
        for (i in 0..intervalCount) {
            val labelValue = minY + adjustedInterval * i
            val y = chartBottom - (labelValue - minY) / range.toFloat() * chartHeight

            canvas.drawLine(paddingLeft, y, width, y, gridPaint)
            canvas.drawText(labelValue.toString(), paddingLeft - 70, y + 10, textPaint)
        }

        // Draw rotated chart heading
        val headingText = "Hours"
        canvas.save()
        canvas.rotate(-90f, 20f, (chartHeight / 2 + paddingTop))
        canvas.drawText(headingText, 0f, (chartHeight / 2 + paddingTop) + 80, labelPaint)
        canvas.restore()

        // Draw bars and rotated bar labels
        for ((index, value) in barData.withIndex()) {
            val barHeight = ((value - minY) / (maxY - minY) * chartHeight)
            val left = paddingLeft + index * (2*barWidth) + barWidth/2
            val right = left + barWidth
            val top = chartBottom - barHeight

            // Draw the bar
            canvas.drawRect(left, top, right, chartBottom, barPaint)

            // Draw the rotated day label below the bar
            canvas.save()
            canvas.rotate(-90f, left + barWidth / 4, chartBottom + 50)
            canvas.drawText(daysOfWeek[index], (left + barWidth / 4) - 60, (chartBottom + 50) + 25, textPaint)
            canvas.restore()
        }


        var goalPaint = Paint().apply {
            color = (0xFF986451).toInt()
            strokeWidth = 5f
            textSize = 25f
        }


        // Draw the goal lines
        if(minGoal != 0f){
            val minGoalY = chartBottom - ((minGoal - minY) / (maxY - minY) * chartHeight)
            canvas.drawLine(paddingLeft, minGoalY, width, minGoalY, goalPaint)
            canvas.drawText("min", width, minGoalY, goalPaint)
        }

        if(maxGoal != 0f){
            val maxGoalY = chartBottom - ((maxGoal - minY) / (maxY - minY) * chartHeight)

            goalPaint = Paint().apply {
                color = (0xFF009688).toInt()
                strokeWidth = 5f
                textSize = 25f
            }

            canvas.drawLine(paddingLeft, maxGoalY, width, maxGoalY, goalPaint)
            canvas.drawText("max", width, maxGoalY, goalPaint)
        }
    }

    fun setBarHeight(value : Float){
        this.barHeight = value
        invalidate()
    }

    fun setSize(width: Float, height: Float) {
        this.width = width
        this.height = height
        invalidate()
    }

    fun setGoal(goal: Float) {
        this.goal = goal
        invalidate()
    }

    fun setProgress(progress: Float) {
        this.progress = progress
        invalidate()
    }

    fun setBarData(data: Array<Float>){
        this.barData = data
        invalidate()
    }

    fun setType(type: String){
        if(type.lowercase() == "exp"){
            this.type = 0
        }else if(type.lowercase() == "bar"){
            this.type = 1
        }else if (type.lowercase() == "progress"){
            this.type = 2
        }
    }


    fun setThickness(thickness: Float) {
        this.thickness = thickness
        paint.strokeWidth = thickness
        invalidate()
    }

    fun setLevel(level: Int){
        this.level = level
        invalidate()
    }

    private fun calcScale(data: Array<Float>, numIntervals: Int = 5): Pair<Int, Int> {
        val minValue = (data.minOrNull() ?: 0f)
        val maxValue = (data.maxOrNull() ?: 0f)
        val range = maxValue - minValue
        val padding = range * 0.2 // 20% padding

        val initialMin = floor(minValue - padding).coerceAtLeast(0.0)
        val initialMax = ceil(maxValue + padding).coerceAtMost(24.0)



        val intervalSize = (range / numIntervals)

        val roundedMin = (floor(initialMin / intervalSize) * intervalSize).toInt()
        val roundedMax = (ceil(initialMax / intervalSize) * intervalSize).toInt()

        return roundedMin to roundedMax
    }

    private fun drawExpBar(canvas: Canvas){
        val endX = (progress/goal)*width
        val topY = (height/2) + barHeight
        val bottomY = (height/2) - barHeight


        paint.color = Color.LTGRAY
        canvas.drawRoundRect(endX - 20f, topY, width - 20f, bottomY, 15f, 15f, paint)

        paint.color = Color.BLUE
        canvas.drawRoundRect(20f, topY, endX, bottomY,15f, 15f, paint)

        if(level != 0) {
            canvas.drawText("Level $level", width - 150f, topY - 60f, textPaint)

        }
    }

    fun setMinGoal(value: Float){
        this.minGoal = value
        invalidate()
    }

    fun setMaxGoal(value: Float){
        this.maxGoal = value
        invalidate()
    }

    fun update() {
        invalidate()
    }

}