package com.example.limitless

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.Exercise.Log_Exercise
import com.example.limitless.data.Exercise
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Exercise_Summary : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_exercise_summary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnStart = findViewById<Button>(R.id.btnStart_ES)
        val btnBack = findViewById<Button>(R.id.btnBack_ES)
        val lblType = findViewById<TextView>(R.id.lblType_ES)
        val lblBodyPart = findViewById<TextView>(R.id.lblBodyPart_ES)
        val lblDifficulty = findViewById<TextView>(R.id.lblDifficulty_ES)
        val lblDescription = findViewById<TextView>(R.id.lblDescription_ES)


        btnStart.setOnClickListener {
            if(lblType.text == "Cardio"){
                showDialog()
            }else{
                val intent = Intent(this, Log_Exercise::class.java)
                startActivity(intent)
            }
        }

        btnBack.setOnClickListener {
            val intent = Intent(this, Exercise::class.java)
            startActivity(intent)
        }




    }

    private fun showDialog(){
        val dialog = Dialog(this@Exercise_Summary)

        val time: Float = (60*3600000).toFloat() + (60*60000).toFloat() + (60*1000).toFloat()
        val Ticktimer = Timer(time.toLong())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.timer_dialog)
        dialog.window!!.attributes.windowAnimations=R.style.dialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
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
}