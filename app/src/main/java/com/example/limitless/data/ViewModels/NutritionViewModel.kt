package com.example.limitless.data.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.limitless.data.CalorieCounter
import com.example.limitless.data.Ratios
import java.time.LocalDate

class NutritionViewModel(val currentDate: LocalDate , var calorieWallet: Double, var ratio: Ratios): ViewModel() {


    var carbWallet = calorieWallet*ratio.carbs
    var proteinWallet = calorieWallet*ratio.protein
    var fibreWallet = calorieWallet*ratio.fibre
    var fatWallet = calorieWallet*ratio.fat

    val calorieCounter = CalorieCounter(calorieWallet)

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

    fun GetTotalCalories(): Double {
        return calorieCounter.CalculateTotalCalories()
    }
}