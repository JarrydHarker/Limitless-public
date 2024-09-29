package com.example.limitless.Exercise

import android.app.Dialog
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
import com.example.limitless.R
import com.example.limitless.Timer
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class Log_Exercise : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_exercise)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnRestTimer = findViewById<Button>(R.id.btnRestTimer_LE)

        btnRestTimer.setOnClickListener {
            ShowDialog()
        }
    }

    fun ShowDialog(){
        val dialog = Dialog(this@Log_Exercise)

        var time: Long = 60 * 1000 // Initialize with 1 minute
        var Ticktimer = Timer(time)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.rest_dialog)
        dialog.window!!.attributes.windowAnimations= R.style.dialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)

        val btnStart = dialog.findViewById<Button>(R.id.btnStart_RD)
        val btnClose = dialog.findViewById<Button>(R.id.btnClose_RD)
        val btnAdd10 = dialog.findViewById<Button>(R.id.btnAdd10_RD)
        val btnMinus10 = dialog.findViewById<Button>(R.id.btnMinus_RD)
        val lblTimer = dialog.findViewById<TextView>(R.id.lblTimer_RD)
        val btnReset = dialog.findViewById<Button>(R.id.btnReset_RD)



        Ticktimer.reset(time)
        lblTimer.text = Ticktimer.getTime()

        btnMinus10.setOnClickListener {
            time -= 10 * 1000
            if (time < 0) time = 0 // Prevent negative time
            Ticktimer.reset(time) // Update the timer with the new time
            lblTimer.text = Ticktimer.getTime() // Update the displayed time
        }

        btnAdd10.setOnClickListener {
            time += 10 * 1000
            Ticktimer.reset(time) // Update the timer with the new time
            lblTimer.text = Ticktimer.getTime() // Update the displayed time
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnStart.setOnClickListener {
            Ticktimer.start()

            Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate({
                try {
                    runOnUiThread {
                        lblTimer.text = Ticktimer.getTime()
                    }
                } catch (e: Exception) {
                    Log.e("Error", "Error: ${e.message}")
                }
            }, 0, 1, TimeUnit.SECONDS)
        }

        btnReset.setOnClickListener {
            Ticktimer.reset(time)
        }

        dialog.show()
    }
}