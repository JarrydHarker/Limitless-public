package com.example.limitless

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
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
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)
        val btt2 = AnimationUtils.loadAnimation(this, R.anim.btt2)
        //val btt3 = AnimationUtils.loadAnimation(this, R.anim.btt3)
        //val btt4 = AnimationUtils.loadAnimation(this, R.anim.btt4)

        val textView42 = findViewById<TextView>(R.id.textView60)
        val UWG_Linear = findViewById<LinearLayout>(R.id.UWG_Linear)

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

        textView42.startAnimation(ttb)
        UWG_Linear.startAnimation(stb)
        next.startAnimation(btt)
        skip.startAnimation(btt2)


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

                currentUser?.CreateDay()

                val intent = Intent(this, User_DailySteps::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,
                    getString(R.string.please_fill_in_field_or_press_skip_to_continue3), Toast.LENGTH_SHORT).show()
            }
        }
        skip.setOnClickListener{
            val intent = Intent(this, User_DailySteps::class.java)
            startActivity(intent)
        }

    }
}