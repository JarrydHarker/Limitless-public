package com.example.limitless.data

class CalorieCounter(var calorieWallet: Double?) {
    var calories = 0
    var arrMeals: MutableList<Meal>? = null

    fun CreateMeal(foods: List<Food>){
        val dbAccess = DbAccess.GetInstance()


    }

    fun ChangeWallet(newWallet: Double){
        calorieWallet = newWallet
    }

    fun CalculateTotalCalories(): Double{
        var totalCalories = 0.0

        if(arrMeals != null){
            for(meal in arrMeals!!){
                var mealCalories = 0.0

                for(food in meal.foods){
                    mealCalories += food.calories
                }

                totalCalories += mealCalories
            }
        }

        return totalCalories
    }
}