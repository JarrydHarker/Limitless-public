package com.main.limitless.Nutrition

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.main.limitless.Exercise.Exercise_Activity
import com.main.limitless.MainActivity
import com.main.limitless.R
import com.main.limitless.Settings
import com.main.limitless.ThemeManager
import com.google.android.material.bottomnavigation.BottomNavigationView


class MealTracker : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_tracker)

        val bottomNavBar: BottomNavigationView = findViewById(R.id.NavBar)

        ThemeManager.updateNavBarColor(this, bottomNavBar)

        bottomNavBar.setSelectedItemId(R.id.ic_nutrition)
        bottomNavBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_workouts -> {
                    navigateToActivityLeft(Exercise_Activity::class.java)
                    true
                }
                R.id.ic_home -> {
                    navigateToActivityLeft(MainActivity::class.java)
                    true
                }

                R.id.ic_settings -> {
                    navigateToActivityRight(Settings::class.java)
                    true
                }
                R.id.ic_nutrition -> {
                    navigateToActivityRight(Diet_Activity::class.java)
                    true
                }
                else -> false
            }
        }
    }
    // Helper function to navigate to another activity with transition
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