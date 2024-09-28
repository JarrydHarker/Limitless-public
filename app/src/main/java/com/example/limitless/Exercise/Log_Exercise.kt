package com.example.limitless.Exercise

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.R

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

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.rest_dialog)
        dialog.window!!.attributes.windowAnimations= R.style.dialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)

        val btnStart = dialog.findViewById<Button>(R.id.btnStart_RD)
        val btnClose = findViewById<Button>(R.id.btnClose_RD)
        val btnAdd10 = findViewById<Button>(R.id.btnAdd10_RD)
        val btnMinus10 = findViewById<Button>(R.id.btnMinus_RD)

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}