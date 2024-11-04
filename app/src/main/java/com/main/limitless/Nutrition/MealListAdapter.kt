package com.main.limitless.Nutrition

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.NumberPicker
import android.widget.TextView
import com.main.limitless.R
import com.main.limitless.data.Food
import com.main.limitless.nutritionViewModel

class MealListAdapter(context: Context, private val lstFoods: List<Food>) : ArrayAdapter<Food>(context, 0, lstFoods) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.log_meal_list, parent, false)

        val foodNameTextView: TextView = view.findViewById(R.id.lm_FoodName)
        val numberPicker: NumberPicker = view.findViewById(R.id.lm_NumberPick)

        foodNameTextView.text = "${lstFoods.indexOf(getItem(position)!!) + 1}. ${getItem(position)!!.description}: ${getItem(position)!!.calories} kcal"

        numberPicker.minValue = 0
        numberPicker.maxValue = 1000

        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            nutritionViewModel.ScaleFood(getItem(position)!!, newVal.toDouble())
            Log.d("NumberPicker", "New value: $newVal for item: ${getItem(position)}")
        }
        return view
    }
}
