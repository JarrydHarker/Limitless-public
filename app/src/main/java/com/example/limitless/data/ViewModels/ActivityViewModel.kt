package com.example.limitless.data.ViewModels

import android.util.Log
import com.example.limitless.currentUser
import com.example.limitless.data.DbAccess
import com.example.limitless.data.Workout
import com.example.limitless.data.dbAccess
import java.time.LocalDate

class ActivityViewModel(val currentDate: LocalDate) {
    var arrWorkouts: MutableList<Workout>? = null
    var steps = 0
    val db = DbAccess.GetInstance()

    fun AddWorkout(workout: Workout) {
        db.CreateWorkout(workout)

        if (arrWorkouts == null) {
            arrWorkouts = mutableListOf(workout)
        }else{
            arrWorkouts?.add(workout)
        }
    }

    fun GetWorkouts(): List<Workout>?{
        return arrWorkouts
    }

    fun LoadUserData() {
        arrWorkouts = mutableListOf()
        arrWorkouts!!.addAll(dbAccess.GetUserWorkoutsByDate(currentUser?.userId!!, currentDate))
    }
}