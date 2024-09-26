package com.example.limitless

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.limitless.data.ViewModels.NutritionViewModel
import com.example.limitless.data.ViewModels.NutritionViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

val viewModelFactory = NutritionViewModelFactory(currentUser!!.calorieWallet!!)

class Diet_Activity : AppCompatActivity() {

    private lateinit var nutritionViewModel: NutritionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diet)

        // Apply window insets for system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize ViewModel with calorieWallet from currentUser
        currentUser!!.calorieWallet = 2000.0
        val viewModelFactory = NutritionViewModelFactory(currentUser!!.calorieWallet!!)

        nutritionViewModel = ViewModelProvider(this, viewModelFactory)
            .get(NutritionViewModel::class.java)

        // Setup button to navigate to Add Meal page
       val btnAddMeal: Button = findViewById(R.id.btnAddMeal)
        btnAddMeal.setOnClickListener {
            val intent = Intent(this, MealTracker::class.java)
            val options = ActivityOptionsCompat.makeCustomAnimation(
                this, R.anim.slide_in_right, R.anim.slide_out_left
            )
            startActivity(intent, options.toBundle())
        }

        // Initialize bottom navigation bar
        val bottomNavBar: BottomNavigationView = findViewById(R.id.NavBar)
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
                R.id.ic_Report -> {
                    navigateToActivityLeft(Report_Activity::class.java)
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
//old code if needed
/*
package com.example.limitless

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.limitless.data.Meal
import com.example.limitless.data.ViewModels.NutritionViewModel
import com.example.limitless.data.ViewModels.NutritionViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

val viewModelFactory = NutritionViewModelFactory(currentUser!!.calorieWallet!!)

class Diet_Activity : AppCompatActivity() {

    private lateinit var nutritionViewModel: NutritionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_diet)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        currentUser!!.calorieWallet = 2000.0


        // Use ViewModelProvider to instantiate the ViewModel
        nutritionViewModel = ViewModelProvider(this, viewModelFactory)
            .get(NutritionViewModel::class.java)
            
        val btnAddMeal: Button = findViewById(R.id.btnAddMeal)

        btnAddMeal.setOnClickListener{
            val intent = Intent(this, MealTracker::class.java)
            startActivity(intent)
        }


        val bottomNavBar: BottomNavigationView = findViewById(R.id.NavBar)

        bottomNavBar.setSelectedItemId(R.id.ic_nutrition)
        bottomNavBar.setOnNavigationItemSelectedListener{item ->
            when (item.itemId){
                R.id.ic_workouts -> {
                    startActivity(Intent(this, Exercise_Activity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                R.id.ic_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                R.id.ic_Report -> {
                    startActivity(Intent(this, Report_Activity::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                R.id.ic_settings -> {
                    startActivity(Intent(this, Settings::class.java))
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    true
                }
                else -> false
            }
        }
    }
}
 */
