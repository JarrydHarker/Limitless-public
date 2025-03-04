package com.main.limitless.Nutrition

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.main.limitless.Exercise.Exercise_Activity
import com.main.limitless.MainActivity
import com.main.limitless.R
import com.main.limitless.Settings
import com.main.limitless.ThemeManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.main.limitless.isOnline

class Diet_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diet)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView9, fragment_water_tracker())
                .commitNow()
        }
        // Apply window insets for system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Setup button to navigate to Add Meal page
       val btnAddMeal: Button = findViewById(R.id.btnAddMeal)
        btnAddMeal.setOnClickListener {
            if(isOnline){
                val intent = Intent(this, MealTracker::class.java)
                val options = ActivityOptionsCompat.makeCustomAnimation(
                    this, R.anim.slide_in_right, R.anim.slide_out_left
                )
                startActivity(intent, options.toBundle())
            }else{
                Toast.makeText(this, "This function requires an internet connection", Toast.LENGTH_LONG).show()
            }
        }

        ThemeManager.applyTheme(this)
        // Initialize bottom navigation bar
        val bottomNavBar: BottomNavigationView = findViewById(R.id.NavBar)
        ThemeManager.updateNavBarColor(this, bottomNavBar)
        bottomNavBar.selectedItemId = R.id.ic_nutrition

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
