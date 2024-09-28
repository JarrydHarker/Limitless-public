package com.example.limitless.data
import java.time.LocalDate

class User(
    var userId: String = "",
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var password: String = "",
){
    var userInfo = UserInfo()
    var ratios = Ratios()
    var currentDay = Day(LocalDate.now())

    fun InitialiseUserInfo(userInfo: UserInfo){
        this.userInfo = userInfo
    }

    fun SetCalorieWallet(wallet: Double){
        this.userInfo.calorieWallet = wallet
    }

    fun SetStepGoal(goal: Double) {
        this.userInfo.stepGoal = goal
    }

    fun GetCalorieWallet(): Double{
        return userInfo.calorieWallet
    }

    fun GetStepGoal(): Double {
        return userInfo.stepGoal
    }
}

class UserInfo(
    var height: Double? = null,
    var weight: Double? = null,
    var weightGoal: Double? = null,
    var calorieWallet: Double = 2000.0,
    var stepGoal: Double = 10000.0
){

}

class Ratios(
    var protein: Double = 0.35,
    var carbs: Double = 0.45,
    var fibre: Double = 0.014,
    var fat: Double = 0.20,
){
    // Change the protein ratio
    fun ChangeProteinRatio(newRatio: Double) {
        protein = newRatio

        if (CheckRatios()) {
            val remainder = 1.0 - protein
            redistributeRemainder(remainder, "protein")
        }
    }

    // Change the carb ratio
    fun ChangeCarbRatio(newRatio: Double) {
        carbs = newRatio

        if (CheckRatios()) {
            val remainder = 1.0 - carbs
            redistributeRemainder(remainder, "carb")
        }
    }

    // Change the fibre ratio
    fun ChangeFibreRatio(newRatio: Double) {
        fibre = newRatio

        if (CheckRatios()) {
            val remainder = 1.0 - fibre
            redistributeRemainder(remainder, "fibre")
        }
    }

    // Change the fat ratio
    fun ChangeFatRatio(newRatio: Double) {
        fat = newRatio

        if (CheckRatios()) {
            val remainder = 1.0 - fat
            redistributeRemainder(remainder, "fat")
        }
    }

    fun redistributeRemainder(remainder: Double, modifiedRatio: String) {
        // Store original ratios
        val originalRatios = mapOf(
            "protein" to 0.35,
            "carb" to 0.45,
            "fibre" to 0.014,
            "fat" to 0.20
        )

        // Calculate the total of the unmodified original ratios
        val totalUnmodifiedOriginal = (if (modifiedRatio != "protein") originalRatios["protein"]!! else 0.0) +
                (if (modifiedRatio != "carb") originalRatios["carb"]!! else 0.0) +
                (if (modifiedRatio != "fibre") originalRatios["fibre"]!! else 0.0) +
                (if (modifiedRatio != "fat") originalRatios["fat"]!! else 0.0)

        // Redistribute the leftover ratio proportionally to the unmodified ratios
        if (modifiedRatio != "protein") {
            protein = originalRatios["protein"]!! * (remainder / totalUnmodifiedOriginal)
        }
        if (modifiedRatio != "carb") {
            carbs = originalRatios["carb"]!! * (remainder / totalUnmodifiedOriginal)
        }
        if (modifiedRatio != "fibre") {
            fibre = originalRatios["fibre"]!! * (remainder / totalUnmodifiedOriginal)
        }
        if (modifiedRatio != "fat") {
            fat = originalRatios["fat"]!! * (remainder / totalUnmodifiedOriginal)
        }
    }

    private fun CheckRatios(): Boolean{
        return (protein + carbs + fibre + fat) > 1.0
    }
}

class Day(
    var date: LocalDate,  // Use String or LocalDate depending on your needs
    var steps: Int = 0,
    var calories: Double = 0.0,
    var weight: Double? = null,
    var activeTime: Int = 0,
    var water: Double = 0.0,

){
    var arrMeals: MutableList<Meal> = mutableListOf() // Initialize empty mutable list for meals
    var arrWorkouts: MutableList<Workout> = mutableListOf() // Initialize empty mutable list for workouts
}

class Workout(
    var workoutId: String = "",
    var date: String,  // Use String or LocalDate depending on your needs
){
    var arrExercises: MutableList<Exercise> = mutableListOf()
}

class Exercise(
    var exerciseId: String = "",
    var movement: Movement,
    var cardio: Cardio?,
    var strength: Strength?
){

}

class Movement(
    var movementId: Int = 0,
    var name: String = "",
    var description: String? = null,
    var type: String = "",
    var bodypart: String = "",
    var equipment: String = "",
    var difficultyLevel: String = "",
    var max: Double = 0.0
){

}

class Cardio(
    var time: Int = 0,
    var distance: Double = 0.0
){

}

class Strength(
    var sets: Int = 0,
    var repetitions: Int = 0,
    var favourite: Boolean = false
){

}

class MealFood(
    var mealId: String = "",
    var foodId: Int = 0
){

}

class Meal(
    var mealId: String = "",
    var date: LocalDate? = null,  // Use String or LocalDate depending on your needs
    var userId: String? = null,
    var name: String = ""
){
    var arrFoods: MutableList<Food> = mutableListOf()
}

class Food(
    var foodId: Int = 0,
    var mealId: String? = null,
    var category: String? = null,
    var description: String? = null,
    var weight: Double? = null,
    var calories: Int = 0,
    var protein: Double? = null,
    var carbohydrates: Double? = null,
    var fibre: Double? = null,
    var fat: Double? = null,
    var cholesterol: Double? = null,
    var sugar: Double? = null,
    var saturatedFat: Double? = null,
    var vitaminB12: Double? = null,
    var vitaminB6: Double? = null,
    var vitaminK: Double? = null,
    var vitaminE: Double? = null,
    var vitaminC: Double? = null,
    var vitaminA: Double? = null,
    var zinc: Double? = null,
    var magnesium: Double? = null,
    var sodium: Double? = null,
    var potassium: Double? = null,
    var iron: Double? = null,
    var calcium: Double? = null
){

}

