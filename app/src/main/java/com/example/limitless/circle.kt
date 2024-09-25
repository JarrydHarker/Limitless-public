package com.example.limitless

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi

class circle@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var width: Float = 0f
    private var height: Float = 0f
    private var goal: Float = 100f
    private var progress: Float = 0f
    private var paint = Paint()
    private var textPaint = Paint()
    private var thickness: Float = 10f
    private var type = 0
    private var level = 0
    private var pieValues = ArrayList<Float>()
    private var pieColours = ArrayList<Int>()

    init {
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = thickness // Set stroke width (adjust as needed)
        textPaint.style = Paint.Style.FILL
        textPaint.strokeWidth = 0f
        textPaint.textSize = 80f
        textPaint.color = Color.BLACK
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if(type == 0){
            paint.style = Paint.Style.FILL
            textPaint.textSize = 20F
            var count = 0
            var startAngle = 0F
            var midAngles = ArrayList<Float>()

            for(i in 0..pieValues.size-1){
                var value = pieValues[i]

                val endAngle = (startAngle + calcPieSegments(value)).mod(360f)  // Calculate end angle

                // Calculate center of the segment arc
                val midAngle = ((startAngle + endAngle) / 2f).mod(360f)
                midAngles.add(midAngle)

                paint.color = pieColours[i.mod(pieValues.size)]
                canvas.drawArc(50f, 50f, width, height, startAngle, calcPieSegments(value), true, paint)

                count++
                startAngle += calcPieSegments(value)
            }

        }else if(type == 1){
            canvas.drawArc(50f, 50f, width, height, 270f, calcSegmentSize(progress, goal), false, paint)
            paint.color = Color.rgb(85f,85f,85f)
            canvas.drawArc(50f,50f,width, height,270f + calcSegmentSize(progress, goal),360 - calcSegmentSize(progress, goal),false, paint)

            canvas.drawText("00:00:00", (width / 2)-130, (height / 2)+45, textPaint)
        }else if(type == 2){
            canvas.drawArc(60f,50f,width,height,180f, calcSegmentSize(progress, goal), false, paint)
            paint.color = Color.rgb(85f,85f,85f)
            canvas.drawArc(60f,50f,width,height,180+calcSegmentSize(progress, goal), 180-calcSegmentSize(progress, goal), false, paint)

            canvas.drawText("Level $level", 20f, (height/2)+60, textPaint)
            canvas.drawText("Level ${level + 1}", width-40, (height/2)+60, textPaint)
        }

    }

    private fun calcSegmentSize(progress: Float, goal: Float): Float {
        return (progress / goal) * 360
    }

    private fun calcPieSegments(value: Float): Float {
        var total = 0F

        for(value in pieValues){
            total += value
        }

        return (value/total) * (360)//pieValues.count())
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

    fun setThickness(thickness: Float) {
        this.thickness = thickness
        paint.strokeWidth = thickness
        invalidate()
    }

    fun setType(type: String){
        if(type.lowercase() == "pie"){
            this.type = 0
        }else if(type.lowercase() == "timer"){
            this.type = 1
        }else if (type.lowercase() == "progress"){
            this.type = 2
        }
    }

    fun setLevel(level: Int){
        this.level = level
    }

    fun setPieValues(values: ArrayList<Float>){
        pieValues = values
    }

    fun setPieColours(colours: ArrayList<Int>){
        this.pieColours = colours
    }

    private fun degsToRads(degrees: Float):Double{
        var Output =degrees*Math.PI/180

        Log.d("Math", "Input: $degrees\nOutput: $Output")

        return Output
    }
}