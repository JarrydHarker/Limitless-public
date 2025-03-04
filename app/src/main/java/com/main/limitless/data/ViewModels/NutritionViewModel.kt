package com.main.limitless.data.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.main.limitless.currentUser
import com.main.limitless.data.Food
import com.main.limitless.data.Meal
import com.main.limitless.data.MealFood
import com.main.limitless.data.Ratios
import com.main.limitless.dbAccess
import java.time.LocalDate

class NutritionViewModel(val currentDate: LocalDate = LocalDate.now() , var calorieWallet: Double = 2000.0, var ratio: Ratios = Ratios()): ViewModel() {

    val weight: Double? = null
    var water: Double = 0.00
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
    fun setWaterIntake(intake: Double) {
        water = intake
    }

    fun getWaterIntake(): Double {
        return water
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

    fun ScaleFood(food: Food, newWeight: Double) {
        val oldWeight = food.weight ?: return // Exit if old weight is null to avoid division by null
        val scaleFactor = newWeight / oldWeight

        // Update weight
        food.weight = newWeight

        // Scale all nutrient values proportionally
        food.calories = (food.calories * scaleFactor).toInt() // Convert back to Int
        food.protein = food.protein?.times(scaleFactor)
        food.carbohydrates = food.carbohydrates?.times(scaleFactor)
        food.fibre = food.fibre?.times(scaleFactor)
        food.fat = food.fat?.times(scaleFactor)
        food.cholesterol = food.cholesterol?.times(scaleFactor)
        food.sugar = food.sugar?.times(scaleFactor)
        food.saturatedFat = food.saturatedFat?.times(scaleFactor)
        food.vitaminB12 = food.vitaminB12?.times(scaleFactor)
        food.vitaminB6 = food.vitaminB6?.times(scaleFactor)
        food.vitaminK = food.vitaminK?.times(scaleFactor)
        food.vitaminE = food.vitaminE?.times(scaleFactor)
        food.vitaminC = food.vitaminC?.times(scaleFactor)
        food.vitaminA = food.vitaminA?.times(scaleFactor)
        food.zinc = food.zinc?.times(scaleFactor)
        food.magnesium = food.magnesium?.times(scaleFactor)
        food.sodium = food.sodium?.times(scaleFactor)
        food.potassium = food.potassium?.times(scaleFactor)
        food.iron = food.iron?.times(scaleFactor)
        food.calcium = food.calcium?.times(scaleFactor)
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