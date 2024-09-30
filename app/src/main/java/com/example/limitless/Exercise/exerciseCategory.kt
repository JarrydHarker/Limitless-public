package com.example.limitless.Exercise

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.example.limitless.MainActivity
import com.example.limitless.Nutrition.Diet_Activity
import com.example.limitless.R
import com.example.limitless.Report_Activity
import com.example.limitless.Settings
import com.example.limitless.ThemeManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class exerciseCategory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_category)
        val bottomNavBar: BottomNavigationView = findViewById(R.id.NavBar)

        ThemeManager.updateNavBarColor(this, bottomNavBar)

        bottomNavBar.setSelectedItemId(R.id.ic_workouts)
        bottomNavBar.setOnNavigationItemSelectedListener{item ->
            when (item.itemId){
                R.id.ic_home -> {
                    navigateToActivityRight(MainActivity::class.java)
                    true
                }
                R.id.ic_nutrition -> {
                    navigateToActivityRight(Diet_Activity::class.java)
                    true
                }

                R.id.ic_settings -> {
                    navigateToActivityRight(Settings::class.java)
                    true
                }
                R.id.ic_workouts -> {
                    startActivity(Intent(this, Exercise_Activity::class.java))
                    true
                }
                else -> false
            }
        }
    }
    private fun navigateToActivityRight(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        val options = ActivityOptionsCompat.makeCustomAnimation(
            this, R.anim.slide_in_right, R.anim.slide_out_left
        )
        startActivity(intent, options.toBundle())
    }
    private fun navigateToActivityLeft(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        val options = ActivityOptionsCompat.makeCustomAnimation(
            this, R.anim.slide_in_left, R.anim.slide_out_right
        )
        startActivity(intent, options.toBundle())
    }

}