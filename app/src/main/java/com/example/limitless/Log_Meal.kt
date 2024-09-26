package com.example.limitless

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.limitless.data.CalorieCounter
import com.example.limitless.data.Food
import com.example.limitless.data.ViewModels.NutritionViewModel

private lateinit var mealListAdapter: ArrayAdapter<String>
private val mealDescriptions = mutableListOf<String>()

class Log_Meal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_meal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val listView = findViewById<ListView>(R.id.listView)
        mealListAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mealDescriptions)
        listView.adapter = mealListAdapter

        val btnLog = findViewById<Button>(R.id.btnLog_LM)
        val btnCreateMeal = findViewById<Button>(R.id.btnCreateMeal_LM)
        val lblMealTitle = findViewById<TextView>(R.id.lblMealTitle_LM)
        lblMealTitle.text = mealTitle

        btnLog.setOnClickListener {

        }

        btnCreateMeal.setOnClickListener {
            val foods = listOf(
                Food(
                    foodId = "2",
                    mealId = "1",
                    category = "Vegetable",
                    description = "Carrot",
                    weight = 100.0,
                    calories = 41,
                    protein = 0.9,
                    carbohydrates = 10.0,
                    fibre = 2.8,
                    fat = 0.2,
                    cholesterol = 0.0,
                    sugar = 4.7,
                    saturatedFat = 0.0,
                    vitaminB12 = 0.0,
                    vitaminB6 = 0.1,
                    vitaminK = 13.2,
                    vitaminE = 0.7,
                    vitaminC = 5.9,
                    vitaminA = 835.0,
                    zinc = 0.2,
                    magnesium = 12.0,
                    sodium = 69.0,
                    potassium = 320.0,
                    iron = 0.3,
                    calcium = 33.0,
                    meal = null
                )
            )

            // Create a CalorieCounter instance
            val calorieCounter = CalorieCounter(calorieWallet = 2000.0)


            showDialog(foods, nutritionViewModel.calorieCounter)
        }

    }

    fun showDialog(foods: List<Food>, calorieCounter: CalorieCounter){
        val dialog = Dialog(this@Log_Meal)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.create_meal_dialog)
        dialog.window!!.attributes.windowAnimations=R.style.dialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)

        val close = dialog.findViewById<Button>(R.id.btnCloseDialog)
        val add = dialog.findViewById<Button>(R.id.btnAddMeal_LMD)

        close.setOnClickListener {
            dialog.dismiss()
        }

        add.setOnClickListener {
            // Create a new meal with the provided foods
            calorieCounter.CreateMeal(foods, calorieCounter)

            val meal = calorieCounter.arrMeals?.last()
            meal?.let {
                val mealDescription = "${it.name}: ${it.foods.joinToString(", ") { food -> food.description }}"
                mealDescriptions.add(mealDescription)
                mealListAdapter.notifyDataSetChanged()
            }

            dialog.dismiss()
        }

        dialog.show()
    }
}