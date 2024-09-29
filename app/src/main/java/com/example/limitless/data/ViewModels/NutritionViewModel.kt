package com.example.limitless.data.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.limitless.currentUser
import com.example.limitless.data.DbAccess
import com.example.limitless.data.Meal
import com.example.limitless.data.Ratios
import com.example.limitless.nutritionViewModel
import java.time.LocalDate

class NutritionViewModel(val currentDate: LocalDate , var calorieWallet: Double, var ratio: Ratios): ViewModel() {

    val db = DbAccess.GetInstance()
    var carbWallet = calorieWallet*ratio.carbs
    var proteinWallet = calorieWallet*ratio.protein
    var fibreWallet = calorieWallet*ratio.fibre
    var fatWallet = calorieWallet*ratio.fat

    var arrMeals: MutableList<Meal>? = null

    fun CreateMeal(meal: Meal) {
        db.CreateMeal(meal)

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
}