package com.example.limitless.data.Graphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.icu.util.Output
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import java.util.Locale
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin

class CircleGraphic @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var isEmpty: Boolean =false
    private var width: Float = 0f
    private var height: Float = 0f
    private var goal: Float = 100f
    private var progress: Float = 0f
    private var paint = Paint()
    private var textPaint = Paint()
    private var thickness: Float = 10f
    private var type = 0
    private var level = 0
    private var pieValues = ArrayList<Pair<String, Float>>()
    private var pieColours = ArrayList<Int>()
    private var isUpdate = false
    private var isReset = false
    private var drawLevels = false
    private var drawWeekly = false
    private var hasLabel = true

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Initialization code
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = thickness
        textPaint.style = Paint.Style.FILL
        textPaint.strokeWidth = 0f
        textPaint.textSize = 50f
        textPaint.color = Color.BLACK

        if(!isEmpty){
            if(type == 0){
                if(pieValues.size > 0){
                    drawPie(canvas)
                }else{
                    canvas.drawText("Get to work to see analytics here!", width/2, height/2, textPaint)
                }

            }else if(type == 1){
                drawTimer(canvas)
            }else if(type == 2){
                drawProgress(canvas)
            }else if(type == 3){
                drawCircle(canvas)
            }
        }else drawEmpty(canvas)


        if(isUpdate){
            updateTimer(canvas)
            isUpdate = false
        }

        if(isReset){
            updateTimer(canvas)
            isReset = false
        }
    }

    private fun drawCircle(canvas: Canvas) {

        if(hasLabel){
            textPaint.textSize=height/5
            // Set the typeface to bold
            textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textPaint.color= Color.WHITE

            canvas.drawArc(10f, 10f, width, height, 270f, calcSegmentSize(progress, goal), false, paint)
            paint.color = Color.LTGRAY
            canvas.drawArc(10f,10f,width, height,270f + calcSegmentSize(progress, goal),360 - calcSegmentSize(progress, goal),false, paint)

            canvas.drawText((goal - progress).toInt().toString(), (width / 2)-100, (height / 2), textPaint)
            textPaint.textSize=height/9
            textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            canvas.drawText("remaining", (width / 2)-100, (height / 2)+80, textPaint)
        }else {
            canvas.drawArc(10f, 10f, width, height, 270f, calcSegmentSize(progress, goal), false, paint)
            paint.color = Color.LTGRAY
            canvas.drawArc(10f,10f,width, height,270f + calcSegmentSize(progress, goal),360 - calcSegmentSize(progress, goal),false, paint)
        }
    }

    private fun calcSegmentSize(progress: Float, goal: Float): Float {
        return if(progress < goal){
            (progress / goal) * 360
        }else 360f
    }

    private fun calcProgressSize(progress: Float, goal: Float): Float {
        return if(progress < goal){
            (progress / goal) * 180
        }else 180f
    }

    private fun calcPieSegments(value: Float): Float {
        var total = 0F

        for((category, number) in pieValues){
            total += number
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
        }else if(type.lowercase() == "circle"){
            this.type = 3
        }
    }

    fun setLevel(level: Int){
        this.level = level
        drawLevels = true
    }

    fun setPieValues(values: ArrayList<Pair<String, Float>>){
        pieValues = values
        invalidate()
    }

    fun setPieColours(colours: ArrayList<Int>){
        this.pieColours = colours
    }

    private fun updateTimer(canvas: Canvas){
        if(type == 1){
            canvas.drawColor(Color.WHITE)
            drawTimer(canvas)
        }

        invalidate()
    }

    fun updateTimer(progress: Float){
        this.progress = progress
        isUpdate = true
        invalidate()
    }

    fun RemoveLabel(){
        hasLabel = false
    }

    fun resetTimer(goal: Float, progress: Float){
        this.goal = goal
        this.progress = progress

        isReset = true
    }

    fun restartTimer(){
        progress = 0f
        invalidate()
    }


    private fun degsToRads(degrees: Float):Double{
        var Output =degrees*Math.PI/180

        return Output
    }

    private fun convertToTime(): String{
        var output = ""

        var seconds = (progress / 1000).toInt()
        var minutes = seconds / 60
        val hours = minutes / 60

        seconds = seconds.mod(60)
        minutes = minutes.mod(60)
        output = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)

        return output
    }


    private fun drawPie(canvas: Canvas){
        paint.style = Paint.Style.FILL
        textPaint.textSize = 20F
        var count = 0
        var startAngle = 0F
        var midAngles = ArrayList<Float>()

        for(i in 0..pieValues.size-1){
            val (name ,value) = pieValues[i]

            val endAngle = (startAngle + calcPieSegments(value)).mod(360f)  // Calculate end angle

            // Calculate center of the segment arc
            val midAngle = ((startAngle + endAngle) / 2f).mod(360f)
            midAngles.add(midAngle)

            var colorIndex = i.mod(pieColours.size)
            paint.color = pieColours[colorIndex]
            canvas.drawArc(50f, 50f, width, height, startAngle, calcPieSegments(value), true, paint)

            count++
            startAngle += calcPieSegments(value)


        }
        for (i in pieValues ){
            val index = pieValues.indexOf(i)
            val legendheight = (((height/2) -50) +(index *45))
            val (name, value) = pieValues[index]

            paint.color = pieColours[pieValues.indexOf(i)]
            textPaint.textSize= 35f
            canvas.drawRect(width +30f , legendheight+20f, width +10F, legendheight, paint)
            canvas.drawText("$name:${formatToHoursMins(value)}", width +40f, legendheight+20f,textPaint)
        }
        /*for(i in 0..midAngles.size-1){
            val radius = width / 2f
            var midAngle = midAngles[i]
            var textX = 0f
            var textY = 0f
            var value = pieValues[i]
            var radAngle = degsToRads(midAngle)

            // Calculate text position based on center and radius
            if(midAngle == 0f){
                textX = width/2 + radius/2
                textY = height/2
            }else if(midAngle == 180f){
                textX = width/2 - radius/2
                textY = height/2
            }else if(midAngle == 270f ){
                textX = width/2
                textY = height/2 - radius/2
            }else if(midAngle == 90f){
                    textX = width/2
                    textY = height/2 + radius/2
                }else{
                    if(midAngle > 270f){//top right
                        textX = ((width/2) + (cos(radAngle) * (radius/2))).toFloat()
                        textY = ((height/2) + (sin(radAngle) * (radius/2))).toFloat()
                    }else if(midAngle > 180f && midAngle < 270f){//top left
                        textX = ((width/2) + (cos(radAngle) * (radius/2))).toFloat()
                        textY = ((height/2) + (sin(radAngle) * (radius/2))).toFloat()
                    }else if(midAngle > 90f && midAngle < 180f){//bottom left
                        textX = ((width/2) + (cos(radAngle) * (radius/2))).toFloat()
                        textY = ((height/2) + (sin(radAngle) * (radius/2))).toFloat()
                    }else if(midAngle < 90f){//bottom right
                        textX = ((width/2) + (cos(radAngle) * (radius/2))).toFloat()
                        textY = ((height/2) + (sin(radAngle) * (radius/2))).toFloat()
                    }
                }


            canvas.drawText("${value.toInt()}",textX,textY,textPaint);
        }*/
    }

    private fun drawTimer(canvas: Canvas){
        textPaint.textSize=80f
        paint.style = Paint.Style.STROKE
        canvas.drawArc(50f, 50f, width, height, 270f, calcSegmentSize(progress, goal), false, paint)
        paint.color = Color.LTGRAY
        canvas.drawArc(50f,50f,width, height,270f + calcSegmentSize(progress, goal),360 - calcSegmentSize(progress, goal),false, paint)

        canvas.drawText(convertToTime(), (width / 2)-100, (height / 2)+60, textPaint)
    }

    fun setWeekly(){
        drawWeekly = true
    }

    private fun formatToHoursMins(value: Float): String{
        val Hours = floor(value)
        val Mins = floor(value.mod(Hours)*60)

        return if(Mins > 0) {
            "${Hours.toInt()}h ${Mins.toInt()}m"
        }else return "${Hours.toInt()}h"
    }

    private fun drawProgress(canvas: Canvas){

        if(drawWeekly){
            textPaint.textSize = 80f

            val startAngle = 180f
            val sweepAngle = calcProgressSize(progress, goal)

            // Ensure sweepAngle is within 0-180 degrees
            val clampedSweepAngle = sweepAngle.coerceIn(0f, 180f)

            // Draw the progress arc
            paint.color = Color.GREEN // Use a color for the progress arc
            canvas.drawArc(60f, 50f, width, height, startAngle, clampedSweepAngle, false, paint)
            // Draw the remaining arc
            paint.color = Color.LTGRAY
            canvas.drawArc(60f, 50f, width, height, startAngle + clampedSweepAngle, 180f - clampedSweepAngle, false, paint)

            // Log for debugging
            Log.d("Math", clampedSweepAngle.toString())
            canvas.drawText("${formatToHoursMins(progress)}/${formatToHoursMins(goal)}", (width / 2)-150, (height / 2)-100, textPaint)
            canvas.drawText("This Week", (width / 2)-150, (height / 2)-100+textPaint.textSize, textPaint)

        }else{

            textPaint.textSize = 80f


            canvas.drawText("${formatToHoursMins(progress)}/${formatToHoursMins(goal)}", (width / 2)-100, (height / 2)-100, textPaint)
            canvas.drawText("Max Daily Goal", (width / 2)-230, (height / 2)-100+textPaint.textSize, textPaint)

            val startAngle = 180f
            val sweepAngle = calcProgressSize(progress, goal)

            // Ensure sweepAngle is within 0-180 degrees
            val clampedSweepAngle = sweepAngle.coerceIn(0f, 180f)

            // Draw the progress arc
            paint.color = Color.GREEN // Use a color for the progress arc
            canvas.drawArc(60f, 50f, width, height, startAngle, clampedSweepAngle, false, paint)
            // Draw the remaining arc
            paint.color = Color.LTGRAY
            canvas.drawArc(60f, 50f, width, height, startAngle + clampedSweepAngle, 180f - clampedSweepAngle, false, paint)

        }

        if(drawLevels){
            canvas.drawText("Level $level", 20f, (height/2)+60, textPaint)
            canvas.drawText("Level ${level + 1}", width-40, (height/2)+60, textPaint)
        }
    }

    fun setEmpty(){
        this.isEmpty = true
    }

    private fun drawEmpty(canvas: Canvas) {
        if(drawWeekly){
            canvas.drawText("No weekly goal set", width/2-150,height/2, textPaint)
        }else{
            canvas.drawText("No graph data", width/2-100,height/2-100, textPaint)
        }
    }
}