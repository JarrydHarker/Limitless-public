package com.example.limitless.Nutrition

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.R
import com.example.limitless.currentUser
import com.example.limitless.data.DbAccess
import com.example.limitless.data.Food
import com.example.limitless.data.Meal
import com.example.limitless.data.dbAccess
import com.example.limitless.nutritionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var mealListAdapter: ArrayAdapter<String>
private val mealDescriptions = mutableListOf<String>()

class Log_Meal : AppCompatActivity() {

    val meal = Meal()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_meal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val listView = findViewById<ListView>(R.id.lvCreatMeal)
        mealListAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mealDescriptions)
        listView.adapter = mealListAdapter

        val btnLog = findViewById<Button>(R.id.btnLog_LM)
        val btnCreateMeal = findViewById<Button>(R.id.btnCreateMeal_LM)
        val lblMealTitle = findViewById<TextView>(R.id.lblMealTitle_LM)
        val mealFoods: MutableList<Food> = mutableListOf()
        val spinMeals: Spinner = findViewById(R.id.spinPrevFoods_LM)
        val mealsAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)

        val meals = dbAccess.GetUserMeals(currentUser?.userId!!)

        for(meal in meals){
            mealsAdapter.add(meal.name)
        }

        spinMeals.adapter = mealsAdapter

        lblMealTitle.text = mealTitle

        btnLog.setOnClickListener {
            // Create a new meal with the provided foods
            meal.date = nutritionViewModel.currentDate
            meal.arrFoods = mealFoods
            meal.userId = currentUser?.userId

            nutritionViewModel.CreateMeal(meal)

            val intent = Intent(this, Diet_Activity::class.java)
            startActivity(intent)
        }

        btnCreateMeal.setOnClickListener {
            showDialog(mealFoods)
        }
    }

    fun showDialog(mealFoods: MutableList<Food>) {
        val dialog = Dialog(this@Log_Meal)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.create_meal_dialog)
        dialog.window!!.attributes.windowAnimations = R.style.dialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)

        dialog.show()

        val close = dialog.findViewById<Button>(R.id.btnCloseDialog)
        val add = dialog.findViewById<Button>(R.id.btnAddMeal_LMD)
        val txtFoodSearch: AutoCompleteTextView = dialog.findViewById(R.id.txtFoodSearch)
        val lvMealPreview: ListView = dialog.findViewById(R.id.lvMealPreview)
        var arrFoods: MutableList<Food> = mutableListOf()
        val txtMealName: EditText = dialog.findViewById(R.id.txtMealName_LMD)

        meal.name = txtMealName.text.toString()

        txtFoodSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {  // Start searching after 1 character
                    fetchFoodSuggestions(s.toString())  // Fetch suggestions from the server
                }
            }

            private fun fetchFoodSuggestions(search: String) {
                val foodAdapter = ArrayAdapter<String>(
                    this@Log_Meal,
                    android.R.layout.simple_dropdown_item_1line,
                    mutableListOf()
                )
                // Start a coroutine in the Main (UI) thread
                CoroutineScope(Dispatchers.Main).launch {
                    val db = DbAccess.GetInstance()
                    val arrFetchFoods = db.SearchForFood(search)
                    arrFoods = arrFetchFoods.toMutableList()
                    foodAdapter.clear()

                    for (food in arrFetchFoods) {
                        foodAdapter.add(food.description)
                    }

                    if (foodAdapter.isEmpty) {
                        foodAdapter.add("No food found") // Show no results
                    }

                    foodAdapter.notifyDataSetChanged()
                    txtFoodSearch.setAdapter(foodAdapter)
                    Log.d("FoodAdapter", "FoodAdapter after notify: ${foodAdapter.count}")
                    txtFoodSearch.showDropDown()
                }
            }
        })

        txtFoodSearch.setOnItemClickListener { parent, view, position, id ->
            // Get the index of the item clicked
            val itemIndex = position
            mealFoods.add(arrFoods[itemIndex])

            val mealpreviewAdapter = ArrayAdapter<String>(this@Log_Meal, android.R.layout.simple_list_item_1, mutableListOf())

            for (food in mealFoods) {
                mealpreviewAdapter.add(food.description)
            }

            lvMealPreview.adapter = mealpreviewAdapter

            txtFoodSearch.text.clear()
            txtFoodSearch.dismissDropDown()
        }

        close.setOnClickListener {
            dialog.dismiss()
        }

        add.setOnClickListener {
            var mealDescription: String

            for(food in mealFoods){
                mealDescription = "${mealFoods.indexOf(food) + 1}. ${food.description}: ${food.calories} kcal"
                mealListAdapter.add(mealDescription)
            }

            mealListAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
    }
}
