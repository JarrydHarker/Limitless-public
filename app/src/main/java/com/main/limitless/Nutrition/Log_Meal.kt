package com.main.limitless.Nutrition

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.main.limitless.R
import com.main.limitless.currentUser
import com.main.limitless.data.Food
import com.main.limitless.data.Meal
import com.main.limitless.data.dbAccess
import com.main.limitless.nutritionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var mealListAdapter: ArrayAdapter<String>
private val mealDescriptions = mutableListOf<String>()

class Log_Meal : AppCompatActivity() {

    var mealname = ""

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
        mealListAdapter = ArrayAdapter(this, R.layout.log_meal_list, R.id.lm_FoodName, mealDescriptions)
        listView.adapter = mealListAdapter

        val btnLog = findViewById<Button>(R.id.btnLog_LM)
        val btnCreateMeal = findViewById<Button>(R.id.btnCreateMeal_LM)
        val lblMealTitle = findViewById<TextView>(R.id.lblMealTitle_LM)
        val mealFoods: MutableList<Food> = mutableListOf()
        val spinMeals: AutoCompleteTextView = findViewById(R.id.spinPrevFoods_LM)
        var meal = Meal()
        val Back: ImageView = findViewById(R.id.LM_ivBack)
        var prevMeals: MutableList<Meal> = mutableListOf()
        val inflater = LayoutInflater.from(this)
        val numberPickerView = inflater.inflate(R.layout.log_meal_list, null)
        val numberPicker: NumberPicker = numberPickerView.findViewById(R.id.lm_NumberPick)

        numberPicker.minValue = 0
        numberPicker.maxValue = 1000

        Back.setOnClickListener{
            val intent = Intent(this, MealTracker::class.java)
            startActivity(intent)
        }

        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d("NumberPicker", "New value: $newVal")
        }

        CoroutineScope(Dispatchers.Main).launch {
            dbAccess.GetUserMeals(currentUser?.userId!!) { meals ->
                val mealsAdapter = ArrayAdapter<String>(this@Log_Meal, R.layout.log_meal_list)

                prevMeals.addAll(meals)

                for (meal in meals) {
                    mealsAdapter.add(meal.name)
                }

                spinMeals.setAdapter(mealsAdapter)
                mealsAdapter.notifyDataSetChanged()
                spinMeals.showDropDown()
            }
        }

        spinMeals.setOnItemClickListener { adapterView: AdapterView<*>, view2: View, i: Int, l: Long ->
            meal = prevMeals[i]

                val mealsAdapter =
                    ArrayAdapter<String>(this@Log_Meal, R.layout.log_meal_list)

                nutritionViewModel.GetMealFoods(meal.mealId!!) { foods ->
                    Log.d("Fuck", "Num Foods: ${foods.size}")
                    meal.arrFoods.addAll(foods)

                    for (food in meal.arrFoods) {
                        mealsAdapter.add(food.description)
                    }
                    mealsAdapter.notifyDataSetChanged()
                    listView.adapter = mealsAdapter
                }

        }

        spinMeals.setOnClickListener{
            spinMeals.showDropDown()
        }

        lblMealTitle.text = mealTitle

        btnLog.setOnClickListener {

            // Create a new meal with the provided foods
            if(listView.adapter.count != 0){
                meal.date = nutritionViewModel.currentDate
                meal.arrFoods = mealFoods
                meal.userId = currentUser?.userId
                
              if(meal.name.isNotEmpty()){
                nutritionViewModel.CreateMeal(meal)
              }
               
                val intent = Intent(this, Diet_Activity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,
                    getString(R.string.please_add_food_to_the_meal), Toast.LENGTH_SHORT).show()
            }

        }

        btnCreateMeal.setOnClickListener {
            mealFoods.clear()
            mealListAdapter.clear()
            listView.adapter = mealListAdapter
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
                    val arrFetchFoods = dbAccess.SearchForFood(search)
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
            mealListAdapter.clear()
            val name = txtMealName.text.toString()

            if(name.isNotEmpty()){
                mealname = name
            }else {
                Toast.makeText(this, getString(R.string.please_enter_meal_name), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            var mealDescription: String

            if(txtMealName.text.isNotEmpty()){
                for(food in mealFoods){
                    mealDescription = "${mealFoods.indexOf(food) + 1}. ${food.description}: ${food.calories} kcal"
                    mealListAdapter.add(mealDescription)
                }
                mealListAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }else{
                Toast.makeText(this,
                    getString(R.string.please_enter_meal_name_and_food), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
