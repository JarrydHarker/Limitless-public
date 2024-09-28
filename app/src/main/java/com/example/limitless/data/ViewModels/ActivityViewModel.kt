package com.example.limitless.data.ViewModels

import com.example.limitless.data.Workout
import java.time.LocalDate

class ActivityViewModel(val currentDate: LocalDate) {
    var arrWorkouts: MutableList<Workout>? = null
    var steps = 0


}