package com.example.limitless

import android.app.Dialog
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
var isPaused = false
class Map_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_map)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnShow = findViewById<Button>(R.id.btnTimerDialog)
        btnShow.setOnClickListener{
            showDialog()
        }


    }

    private fun showDialog(){
        val dialog = Dialog(this@Map_Activity)

        val time: Float = (60*3600000).toFloat() + (60*60000).toFloat() + (60*1000).toFloat()
        val Ticktimer = Timer(time.toLong())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.timer_dialog)
        dialog.window!!.attributes.windowAnimations=R.style.dialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.BOTTOM)

        val cancel = dialog.findViewById<Button>(R.id.btnClose)
        val go = dialog.findViewById<Button>(R.id.btnGO)
        val stop = dialog.findViewById<Button>(R.id.btnStop)
        val lblTime1 = dialog.findViewById<TextView>(R.id.lblTime1)

        stop.setOnClickListener {
            Ticktimer.pause()
        }

        go.setOnClickListener{

            if (Ticktimer.time > 0){
                Ticktimer.resume()
            }else{
                Ticktimer.start()
                go.text = "Resume"
            }

            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
                try {
                    runOnUiThread {
                        lblTime1.text = Ticktimer.getTime2()
                    }
                    //showChart(Ticktimer.get(), time)
                } catch (e: Exception) {
                    Log.e("Error", "Error: ${e.message}")
                }
            }, 0, 1, TimeUnit.SECONDS)
        }

        cancel.setOnClickListener{
            dialog.cancel()
        }
        dialog.show()
    }

    /*fun showChart(time: Float, goal: Float){
        try{
            val pieChart: PieChart = findViewById(R.id.TT_pieChart)


            // on below line we are setting user percent value,
            // setting description as enabled and offset for pie chart
            pieChart.setUsePercentValues(true)
            pieChart.getDescription().setEnabled(false)
            pieChart.setExtraOffsets(5f, 10f, 5f, 5f)

            // on below line we are setting drag for our pie chart
            pieChart.setDragDecelerationFrictionCoef(0.9f)


            // on below line we are setting hole
            // and hole color for pie chart
            pieChart.setDrawHoleEnabled(true)
            pieChart.setHoleColor(Color.WHITE)

            // on below line we are setting circle color and alpha
            pieChart.setTransparentCircleColor(Color.WHITE)
            pieChart.setTransparentCircleAlpha(0)

            // on  below line we are setting hole radius
            pieChart.setHoleRadius(90f)
            pieChart.setTransparentCircleRadius(90f)

            // on below line we are setting center text
            pieChart.setDrawCenterText(false)

            // on below line we are setting
            // rotation for our pie chart
            pieChart.setRotationAngle(270f)

            // enable rotation of the pieChart by touch
            pieChart.setRotationEnabled(false)
            pieChart.setHighlightPerTapEnabled(false)

            // on below line we are setting animation for our pie chart
            //pieChart.animateY(500, Easing.EaseInOutQuad)

            // on below line we are disabling our legend for pie chart
            pieChart.legend.isEnabled = false
            pieChart.setEntryLabelColor(Color.WHITE)
            pieChart.setEntryLabelTextSize(12f)

            // on below line we are creating array list and
            // adding data to it to display in pie chart
            var graph = time

            var entries: ArrayList<PieEntry> = ArrayList()
            entries.add(PieEntry(graph))
            entries.add(PieEntry(goal - graph))//3600000F

            if(graph > goal){
                entries.clear()
                entries.add(PieEntry(graph))
                entries.add(PieEntry(0F))//3600000F
            }

            // on below line we are setting pie data set
            val dataSet = PieDataSet(entries, "Hours Completed")

            // on below line we are setting icons.
            dataSet.setDrawIcons(false)

            // on below line we are setting slice for pie
            dataSet.sliceSpace = 0f
            dataSet.iconsOffset = MPPointF(0f, 40f)
            dataSet.selectionShift = 5f

            // add a lot of colours to list
            val colors: ArrayList<Int> = ArrayList()
            //colors.add(resources.getColor(R.color.purple_200))
            //colors.add(resources.getColor(R.color.grey))




            // on below line we are setting colors.
            dataSet.colors = colors

            // on below line we are setting pie data set
            val data = PieData(dataSet)
            data.setValueFormatter(PercentFormatter())
            data.setValueTextSize(0f)
            data.setValueTypeface(Typeface.DEFAULT_BOLD)
            data.setValueTextColor(Color.WHITE)

            try{
                pieChart.setData(data)
            }catch(e:Exception){
                Log.e("Error", e.toString())
            }

            // undo all highlights
            //pieChart.highlightValues(null)

            // loading chart
            pieChart.invalidate()
        }catch(e: Exception){
            Log.e("Error", e.toString())
        }
    }*/


}