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

        intent.setOnClickListener {
            val navigateToAddMeal = Intent(this, MealTracker::class.java)
            startActivity(navigateToAddMeal)
        }

    }
}