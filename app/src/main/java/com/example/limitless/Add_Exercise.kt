package com.example.limitless

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

class Add_Exercise : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_exercise)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val workout = findViewById<Button>(R.id.btnCreateExercise_AE)

        workout.setOnClickListener {
            ShowDialog()
        }
    }

    fun ShowDialog(){
        val dialog = Dialog(this@Add_Exercise)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.attributes.windowAnimations=R.style.dialogAnimation
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.add_exercise_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)

        val btnAddWorkout = dialog.findViewById<Button>(R.id.btnAddWorkout_WD)

        val btnClose = dialog.findViewById<Button>(R.id.btnClose_WD)

        btnAddWorkout.setOnClickListener {
          
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()
    }
}