package com.example.limitless.data.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.limitless.data.CalorieCounter

class NutritionViewModel(var calorieWallet: Double? = 2250.0): ViewModel() {
    var proteinRatio = 0.35
    var carbRatio = 0.45
    var fibreRatio = 0.014
    var fatRatio = 0.20

    var carbWallet = calorieWallet!!*carbRatio
    var proteinWallet = calorieWallet!!*proteinRatio
    var fibreWallet = calorieWallet!!*fibreRatio
    var fatWallet = calorieWallet!!*fatRatio

    val calorieCounter = CalorieCounter(calorieWallet)

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
            proteinRatio = originalRatios["protein"]!! * (remainder / totalUnmodifiedOriginal)
        }
        if (modifiedRatio != "carb") {
            carbRatio = originalRatios["carb"]!! * (remainder / totalUnmodifiedOriginal)
        }
        if (modifiedRatio != "fibre") {
            fibreRatio = originalRatios["fibre"]!! * (remainder / totalUnmodifiedOriginal)
        }
        if (modifiedRatio != "fat") {
            fatRatio = originalRatios["fat"]!! * (remainder / totalUnmodifiedOriginal)
        }
    }

    // Change the protein ratio
    fun ChangeProteinRatio(newRatio: Double) {
        proteinRatio = newRatio

        if (CheckRatios()) {
            val remainder = 1.0 - proteinRatio
            redistributeRemainder(remainder, "protein")
        }

        ReCalculateWallets()
    }

    // Change the carb ratio
    fun ChangeCarbRatio(newRatio: Double) {
        carbRatio = newRatio

        if (CheckRatios()) {
            val remainder = 1.0 - carbRatio
            redistributeRemainder(remainder, "carb")
        }

        ReCalculateWallets()
    }

    // Change the fibre ratio
    fun ChangeFibreRatio(newRatio: Double) {
        fibreRatio = newRatio

        if (CheckRatios()) {
            val remainder = 1.0 - fibreRatio
            redistributeRemainder(remainder, "fibre")
        }

        ReCalculateWallets()
    }

    // Change the fat ratio
    fun ChangeFatRatio(newRatio: Double) {
        fatRatio = newRatio

        if (CheckRatios()) {
            val remainder = 1.0 - fatRatio
            redistributeRemainder(remainder, "fat")
        }

        ReCalculateWallets()
    }

    fun ChangeCalorieWallet(newWallet: Double){
        calorieWallet = newWallet

        ReCalculateWallets()
    }

    private fun CheckRatios(): Boolean{
        return (proteinRatio + carbRatio + fibreRatio + fatRatio) > 1.0
    }

    private fun ReCalculateWallets(){
        carbWallet = calorieWallet!!*carbRatio
        proteinWallet = calorieWallet!!*proteinRatio
        fibreWallet = calorieWallet!!*fibreRatio
        fatWallet = calorieWallet!!*fatRatio
    }

    fun GetTotalCalories(): Double {
        return calorieCounter.CalculateTotalCalories()
    }
}

class NutritionViewModelFactory(private val calorieWallet: Double) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NutritionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NutritionViewModel(calorieWallet) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}