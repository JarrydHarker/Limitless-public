package com.example.limitless.Exercise

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.R
import com.example.limitless.Timer
import com.example.limitless.activityViewModel
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
        val lvExercises: ListView = findViewById(R.id.listSets_LE)
        val btnAddSet = findViewById<Button>(R.id.btnAddSet_LE)
        val btnLogSet = findViewById<Button>(R.id.btnLogSet_LE)

        val workoutId = intent.getIntExtra("workoutId", -1)
        val currentWorkout = activityViewModel.GetWorkout(workoutId)

        if (currentWorkout == null) {
            Toast.makeText(this, "Workout not found", Toast.LENGTH_SHORT).show()
            finish()
        } else {

            if (currentWorkout.arrExercises == null) {
                currentWorkout.arrExercises = mutableListOf()
            }

            val exerciseDetails = currentWorkout.arrExercises.map {
                "Exercise: ${it.GetName()}, Sets: ${it.strength?.sets ?: 0}, Reps: ${it.strength?.repetitions ?: 0}"
            }

            val exerciseAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, exerciseDetails)
            lvExercises.adapter = exerciseAdapter

            btnLogSet.setOnClickListener {
                if (exerciseAdapter.count > 0) {
                    // Remove the first item from the arrExercises list
                    currentWorkout.arrExercises.removeAt(0)
                    // Remove the first item from the adapter
                    exerciseAdapter.remove(exerciseAdapter.getItem(0))
                    exerciseAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "No exercises to remove", Toast.LENGTH_SHORT).show()
                }
            }
        }





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