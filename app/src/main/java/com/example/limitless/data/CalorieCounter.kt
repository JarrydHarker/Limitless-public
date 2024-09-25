package com.example.limitless.data

class CalorieCounter(calorieWallet: Int) {
    val CalorieWallet = calorieWallet
    var calories = 0
    var arrMeals: MutableList<Meal>? = null

    fun CreateMeal(foods: List<Food>){
        val dbAccess = DbAccess.GetInstance()
    }
}