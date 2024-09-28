package com.example.limitless.Exercise

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.Nutrition.Diet_Activity
import com.example.limitless.MainActivity
import com.example.limitless.R
import com.example.limitless.Report_Activity
import com.example.limitless.Settings
import com.example.limitless.ThemeManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class Workout_Planner : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_workout_planner)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Nicks Animation things
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)
//        val btt2 = AnimationUtils.loadAnimation(this, R.anim.btt2)
//        val btt3 = AnimationUtils.loadAnimation(this, R.anim.btt3)
//        val btt4 = AnimationUtils.loadAnimation(this, R.anim.btt4)

        val textView14 = findViewById<TextView>(R.id.textView14)
        val wp_ListPlanner = findViewById<ListView>(R.id.wp_ListPlanner)

        textView14.startAnimation(ttb)
        wp_ListPlanner.startAnimation(btt)
        //till here

        val bottomNavBar: BottomNavigationView = findViewById(R.id.NavBar)

        ThemeManager.updateNavBarColor(this, bottomNavBar)

        bottomNavBar.setSelectedItemId(R.id.ic_workouts)
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
                R.id.ic_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.ic_Report -> {
                    startActivity(Intent(this, Report_Activity::class.java))
                    true
                }
                R.id.ic_settings -> {
                    startActivity(Intent(this, Settings::class.java))
                    true
                }
                else -> false
            }
        }
    }
}