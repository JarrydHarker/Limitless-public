package com.example.limitless.data

import android.util.Log
import java.time.LocalDate

class CalorieCounter(var calorieWallet: Double?) {
    var calories = 0
    var arrMeals: MutableList<Meal>? = null

    fun CreateMeal(foods: List<Food>) {
        //val dbAccess = DbAccess.GetInstance()

            // Create a new meal
            val meal = Meal(
                mealId = "1",
                date = LocalDate.now(),
                userId = "user123", // Replace with actual user ID
                name = "My Meal",
            )

            meal.arrFoods = foods.toMutableList()

            // Add the meal to the calorie counter's meal list
            if (arrMeals == null) {
                arrMeals = mutableListOf(meal)
                Log.d("Food", "Number of meals: ${arrMeals?.count()}")
            }else{
                arrMeals?.add(meal)
            }
    }


    fun ChangeWallet(newWallet: Double){
        calorieWallet = newWallet
    }

    fun CalculateTotalCalories(): Double{
        var totalCalories = 0.0

        if(arrMeals != null){
            Log.d("Food", "arrMeals is not null")
            for(meal in arrMeals!!){
                var mealCalories = 0.0

                for(food in meal.arrFoods){
                    mealCalories += food.calories
                    Log.d("Food", "${food.description}|${food.calories}")
                }

                totalCalories += mealCalories
            }
        }

        return totalCalories
    }

    fun GetTotalCarbs(): Double{
        var totalCarbs = 0.0

        if(arrMeals != null){
            for(meal in arrMeals!!){
                var mealCarbs = 0.0

                for(food in meal.arrFoods){
                    mealCarbs += food.carbohydrates!!
                }

                totalCarbs += mealCarbs
            }
        }

        return totalCarbs
    }

    fun GetTotalFat(): Double {
        var totalFats = 0.0

        if (arrMeals != null) {
            for (meal in arrMeals!!) {
                var mealFats = 0.0

                for (food in meal.arrFoods) {
                    mealFats += food.fat!!
                }

                totalFats += mealFats
            }
        }

        return totalFats
    }

    fun GetTotalProtein(): Double {
        var totalProteins = 0.0

        if (arrMeals != null) {
            for (meal in arrMeals!!) {
                var mealProteins = 0.0

                for (food in meal.arrFoods) {
                    mealProteins += food.protein!!
                }

                totalProteins += mealProteins
            }
        }

        return totalProteins
    }

    fun GetTotalFibre(): Double {
        var totalFibre = 0.0

        if (arrMeals != null) {
            for (meal in arrMeals!!) {
                var mealFibre = 0.0

                for (food in meal.arrFoods) {
                    mealFibre += food.fibre!!
                }

                totalFibre += mealFibre
            }
        }

        return totalFibre
    }

}