package com.example.limitless.data.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.limitless.currentUser
import com.example.limitless.data.DbAccess
import com.example.limitless.data.Food
import com.example.limitless.data.Meal
import com.example.limitless.data.MealFood
import com.example.limitless.data.Ratios
import com.example.limitless.data.dbAccess
import com.example.limitless.nutritionViewModel
import java.time.LocalDate

class NutritionViewModel(val currentDate: LocalDate = LocalDate.now() , var calorieWallet: Double = 2000.0, var ratio: Ratios = Ratios()): ViewModel() {

    val weight: Double? = null
    val water: Double = 0.0
    var steps: Int = 0
    var carbWallet = calorieWallet*ratio.carbs
    var proteinWallet = calorieWallet*ratio.protein
    var fibreWallet = calorieWallet*ratio.fibre
    var fatWallet = calorieWallet*ratio.fat

    var arrMeals: MutableList<Meal> = mutableListOf()

    fun CreateMeal(meal: Meal) {
        dbAccess.CreateMeal(meal){
            dbAccess.GetMealByName(meal.name, currentDate){ DBmeal ->
                if (DBmeal != null) {
                    for(food in meal.arrFoods){
                        dbAccess.CreateMealFood(MealFood(mealId = DBmeal.mealId!!,date = currentDate, foodId = food.foodId!!, userId = currentUser?.userId!!))
                    }
                }
            }
        }

        // Add the meal to the calorie counter's meal list
        arrMeals.add(meal)
    }

    fun LoadUserData(){
        arrMeals = mutableListOf()
        arrMeals.addAll(dbAccess.GetUserMealsByDate(currentUser!!.userId, currentDate))
    }

    fun ChangeWallet(newWallet: Double){
        calorieWallet = newWallet
    }

    fun CalculateTotalCalories(): Double{
        var totalCalories = 0.0

        Log.d("Food", "arrMeals is not null")
        for(meal in arrMeals){
            var mealCalories = 0.0

            for(food in meal.arrFoods){
                mealCalories += food.calories
                Log.d("Food", "${food.description}|${food.calories}")
            }

            totalCalories += mealCalories
        }

        return totalCalories
    }

    fun GetTotalCarbs(): Double{
        var totalCarbs = 0.0

        for(meal in arrMeals){
            var mealCarbs = 0.0

            for(food in meal.arrFoods){
                mealCarbs += food.carbohydrates!!
            }

            totalCarbs += mealCarbs
        }

        return totalCarbs
    }

    fun GetTotalFat(): Double {
        var totalFats = 0.0

        for (meal in arrMeals) {
            var mealFats = 0.0

            for (food in meal.arrFoods) {
                mealFats += food.fat!!
            }

            totalFats += mealFats
        }

        return totalFats
    }

    fun GetTotalProtein(): Double {
        var totalProteins = 0.0

        for (meal in arrMeals) {
            var mealProteins = 0.0

            for (food in meal.arrFoods) {
                mealProteins += food.protein!!
            }

            totalProteins += mealProteins
        }

        return totalProteins
    }

    fun GetTotalFibre(): Double {
        var totalFibre = 0.0

        for (meal in arrMeals) {
            var mealFibre = 0.0

            for (food in meal.arrFoods) {
                mealFibre += food.fibre!!
            }

            totalFibre += mealFibre
        }

        return totalFibre
    }


    fun ChangeCalorieWallet(newWallet: Double){
        calorieWallet = newWallet

        ReCalculateWallets()
    }

    private fun ReCalculateWallets(){
        carbWallet = calorieWallet*ratio.carbs
        proteinWallet = calorieWallet*ratio.protein
        fibreWallet = calorieWallet*ratio.fibre
        fatWallet = calorieWallet*ratio.fat
    }

    fun GetMealFoods(mealId: Int, onComplete: (List<Food>) -> Unit) {
        dbAccess.GetFoodsByMeal(mealId){ foods ->
            onComplete(foods)
        }
    }
}