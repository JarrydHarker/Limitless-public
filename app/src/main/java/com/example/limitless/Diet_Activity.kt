package com.example.limitless

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.limitless.data.ViewModels.NutritionViewModel
import com.example.limitless.data.ViewModels.NutritionViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

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
        val viewModelFactory = NutritionViewModelFactory(currentUser!!.calorieWallet!!)

        // Use ViewModelProvider to instantiate the ViewModel
        nutritionViewModel = ViewModelProvider(this, viewModelFactory)
            .get(NutritionViewModel::class.java)
            
        val intent = findViewById<Button>(R.id.btnaddmealpage)

        
        val bottomNavBar: BottomNavigationView = findViewById(R.id.NavBar)

        bottomNavBar.setSelectedItemId(R.id.ic_nutrition)
        bottomNavBar.setOnNavigationItemSelectedListener{item ->
            when (item.itemId){
                R.id.ic_workouts -> {
                    startActivity(Intent(this, Exercise_Activity::class.java))
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