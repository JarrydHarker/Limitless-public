package com.example.limitless

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.limitless.data.User
import com.google.android.material.bottomnavigation.BottomNavigationView

var currentUser: User? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val bottomNavBar: BottomNavigationView = findViewById(R.id.NavBar)
        val dailyActivity: CardView = findViewById(R.id.dailyActivityCard)
        val workout: CardView = findViewById(R.id.workoutsCard)
        val recyclerView: RecyclerView = findViewById(R.id.PE_ListExercises)

        if(currentUser == null){
            val intent = Intent(this, SSOActivity::class.java)
            startActivity(intent)
        }

        bottomNavBar.setSelectedItemId(R.id.ic_home)
        bottomNavBar.setOnNavigationItemSelectedListener{item ->
            when (item.itemId){
                R.id.ic_workouts -> {
                    startActivity(Intent(this, Exercise_Activity::class.java))
                    true
                }
                R.id.ic_nutrition -> {
                    startActivity(Intent(this, Diet_Activity::class.java))
                    true
                }
                R.id.ic_AI -> {
                    startActivity(Intent(this, AI_Page::class.java))
                    true
                }
                R.id.ic_settings -> {
                    startActivity(Intent(this, Settings::class.java))
                    true
                }
                else -> false
            }
        }

        dailyActivity.setOnClickListener{
            val intent = Intent(this, Exercise_Activity::class.java)
            startActivity(intent)
        }
        workout.setOnClickListener{
            val intent = Intent(this, Workout_Planner::class.java)
            startActivity(intent)
        }

        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        //recyclerView.adapter = moviesAdapter

    }
}