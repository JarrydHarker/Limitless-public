package com.example.limitless.Exercise

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.R
import com.example.limitless.activityViewModel
import com.example.limitless.data.Exercise

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

        if(activityViewModel.currentWorkout != null){
            for(exercise in activityViewModel.currentWorkout!!.arrExercises){
                listAdapter.add(exercise.toString())
            }
        }

        lvStart_Workout.adapter = listAdapter

    }
}