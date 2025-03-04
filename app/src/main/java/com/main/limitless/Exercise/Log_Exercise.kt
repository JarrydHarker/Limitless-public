package com.main.limitless.Exercise

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
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.main.limitless.R
import com.main.limitless.Settings
import com.main.limitless.Timer
import com.main.limitless.activityViewModel
import com.main.limitless.data.Strength
import com.main.limitless.data.Workout
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
        val btnLogSet = findViewById<Button>(R.id.btnLogSet_LE)
        val workoutId = intent.getIntExtra("workoutId", -1)
        val currentWorkout = activityViewModel.GetWorkout(workoutId)
        val lblCurrentExercise_LE: TextView = findViewById(R.id.lblCurrentExercise_LE)
        val Back: ImageView = findViewById(R.id.backback)

        Back.setOnClickListener{
            val intent = Intent(this, Exercise_Activity::class.java)
            startActivity(intent)
        }

        if (currentWorkout == null) {
            Toast.makeText(this, getString(R.string.workout_not_found), Toast.LENGTH_SHORT).show()
            finish()
        } else {

            // Get the exercise name for the TextView
            var currentExercise = currentWorkout.arrExercises.firstOrNull()
            lblCurrentExercise_LE.text = currentExercise?.GetName() ?: getString(R.string.no_exercise_available)

            val exerciseDetails: MutableList<String> = mutableListOf()

            // Prepare the details for the ListView
            for(set in 1..currentExercise!!.strength!!.sets+1){
                exerciseDetails.add("Set $set: Reps: ${currentExercise.strength?.repetitions ?: 1}")
            }

            val exerciseAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, exerciseDetails)
            lvExercises.adapter = exerciseAdapter

            btnLogSet.setOnClickListener {
                if(btnLogSet.text == getString(R.string.next_set1)){
                    btnLogSet.text = getString(R.string.log_set1)
                }

                if(exerciseAdapter.count == 1){
                    btnLogSet.text = getString(R.string.next_set)
                }

                if (exerciseAdapter.count > 0) {
                    // Remove the first item from the adapter
                    exerciseAdapter.remove(exerciseAdapter.getItem(0))
                    exerciseAdapter.notifyDataSetChanged()



                } else {
                    if(currentWorkout.arrExercises.count() > 1){
                        exerciseDetails.clear()
                        exerciseAdapter.clear()
                        currentWorkout.arrExercises.removeAt(0)
                        currentExercise = currentWorkout.arrExercises[0]

                        if(currentExercise != null){
                            lblCurrentExercise_LE.text = currentExercise!!.GetName()
                            for(set in 1..currentExercise!!.strength!!.sets){
                                exerciseDetails.add("Set $set: Reps: ${currentExercise!!.strength?.repetitions ?: 1}")
                            }

                            exerciseAdapter.notifyDataSetChanged()
                        }
                    }else{
                        Toast.makeText(this,
                            getString(R.string.workout_completed), Toast.LENGTH_LONG).show()

                        val intent = Intent(this, Exercise_Activity::class.java)
                        startActivity(intent)
                    }
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
        val btnClose = dialog.findViewById<ImageView>(R.id.btnClose_RD)
        val btnAdd10 = dialog.findViewById<Button>(R.id.btnAdd10_RD)
        val btnMinus10 = dialog.findViewById<Button>(R.id.btnMinus_RD)
        val lblTimer = dialog.findViewById<TextView>(R.id.lblTimer_RD)
        val btnReset = dialog.findViewById<Button>(R.id.btnReset_RD)

        Ticktimer.reset(time)
        lblTimer.text = Ticktimer.getTime()

        btnMinus10.setOnClickListener {
            time = Ticktimer.getRemainingTime() - 10 * 1000
            if (time < 0) time = 0
            Ticktimer.reset(time)
            lblTimer.text = Ticktimer.getTime()
        }

        btnAdd10.setOnClickListener {
            time = Ticktimer.getRemainingTime() + 10 * 1000
            Ticktimer.reset(time)
            lblTimer.text = Ticktimer.getTime()
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnStart.setOnClickListener {
            Ticktimer.start()

            Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay({
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
            lblTimer.text = Ticktimer.getTime()
        }
        dialog.show()
    }

    private fun ShowDialogAddSet(exerciseAdapter: ArrayAdapter<String>, currentWorkout: Workout) {
        val dialog = Dialog(this@Log_Exercise)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.add_set_dialog)
        dialog.window!!.attributes.windowAnimations = R.style.dialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)

        val txtReps = dialog.findViewById<EditText>(R.id.txtReps_AS)
        val btnAddSet = dialog.findViewById<Button>(R.id.btnAddSet_AS)

        btnAddSet.setOnClickListener {
            val reps = txtReps.text.toString().toIntOrNull()
            if (reps != null && currentWorkout!!.arrExercises.isNotEmpty()) {
                val exercise = currentWorkout.arrExercises[0] // Assuming you want to add to the first exercise
                exercise.strength?.let {
                    it.sets += 1
                    it.repetitions += reps
                } ?: run {
                    exercise.strength = Strength(sets = 1, repetitions = reps)
                }

                // Update the ListView
                val exerciseDetails = currentWorkout.arrExercises.map {
                    "Exercise: ${it.GetName()}, Sets: ${it.strength?.sets ?: 0}, Reps: ${it.strength?.repetitions ?: 0}"
                }
                exerciseAdapter.clear()
                exerciseAdapter.addAll(exerciseDetails)
                exerciseAdapter.notifyDataSetChanged()

                dialog.dismiss()
            } else {
                Toast.makeText(this, getString(R.string.please_enter_valid_reps), Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }
}