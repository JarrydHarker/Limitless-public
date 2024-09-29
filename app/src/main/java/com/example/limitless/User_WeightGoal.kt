package com.example.limitless

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.data.ViewModels.ActivityViewModel
import com.example.limitless.data.ViewModels.NutritionViewModel
import java.time.LocalDate

class User_WeightGoal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_weight_goal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val next: Button = findViewById(R.id.UWG_btnNext)
        val skip: Button = findViewById(R.id.UWG_btnSkip)

        val npMain: NumberPicker = findViewById(R.id.UWG_Main)
        npMain.minValue = 0
        npMain.maxValue = 500

        val npSecondary: NumberPicker = findViewById(R.id.UWG_Secondary)
        npSecondary.minValue = 0
        npSecondary.maxValue = 9

        val npText: TextView = findViewById(R.id.UWG_txtUnit)
        npText.text = "kg"

        next.setOnClickListener{
            if(npMain.value != 0)
            {
                val weight: Double = (npMain.value + (npSecondary.value/10)).toDouble()
                currentUser?.SetWeightGoal(weight)

                currentUser?.CalcCalorieWallet()
                currentUser?.SaveUserInfo()

                // Initialize ViewModel with calorieWallet from currentUser
                nutritionViewModel = NutritionViewModel(LocalDate.now(), currentUser!!.GetCalorieWallet(), currentUser!!.ratios)
                activityViewModel = ActivityViewModel(LocalDate.now())

                val intent = Intent(this, User_DailySteps::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this, "Please fill in field or press Skip to continue", Toast.LENGTH_SHORT).show()
            }
        }
        skip.setOnClickListener{
            val intent = Intent(this, User_DailySteps::class.java)
            startActivity(intent)
        }

    }
}