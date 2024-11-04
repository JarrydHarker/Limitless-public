package com.main.limitless.Exercise

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.main.limitless.R
import com.main.limitless.Settings
import com.main.limitless.activityViewModel

class Start_Workout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start_workout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val lvStart_Workout: ListView = findViewById(R.id.listExercises_WIP)
        val listAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        val btnSartWorkout: Button = findViewById(R.id.btnStart_WIP)
        val lblWorkout: TextView = findViewById(R.id.lblWorkoutName)
        val backimg: ImageView = findViewById(R.id.backimg)

        backimg.setOnClickListener{
            val intent = Intent(this, Exercise_Activity::class.java)
            startActivity(intent)
        }

        if(activityViewModel.currentWorkout != null){
            lblWorkout.setText(activityViewModel.currentWorkout?.name)

            for(exercise in activityViewModel.currentWorkout!!.arrExercises){
                listAdapter.add(exercise.toString())
            }



        }

        lvStart_Workout.adapter = listAdapter


        btnSartWorkout.setOnClickListener {

        }
    }
}