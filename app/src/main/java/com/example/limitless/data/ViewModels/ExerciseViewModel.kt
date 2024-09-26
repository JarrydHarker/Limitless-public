package com.example.limitless.data.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.limitless.data.WorkoutPlanner
import java.time.LocalDate

class ActivityViewModel(val currentDate: LocalDate) {
    var arrWorkouts: MutableList<WorkoutPlanner>? = null


}

class ActivityViewModelFactory(private val calorieWallet: Double) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NutritionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NutritionViewModel(LocalDate.now() , calorieWallet) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}