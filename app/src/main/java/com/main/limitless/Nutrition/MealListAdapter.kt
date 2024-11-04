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

class MealListAdapter(context: Context, private val mealDescriptions: List<String>) : ArrayAdapter<String>(context, 0, mealDescriptions) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.log_meal_list, parent, false)

        val foodNameTextView: TextView = view.findViewById(R.id.lm_FoodName)
        val numberPicker: NumberPicker = view.findViewById(R.id.lm_NumberPick)

        // Set the food name
        foodNameTextView.text = getItem(position)

        // Configure NumberPicker
        numberPicker.minValue = 0
        numberPicker.maxValue = 1000

        numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.d("NumberPicker", "New value: $newVal for item: ${getItem(position)}")
        }

        return view
    }
}
