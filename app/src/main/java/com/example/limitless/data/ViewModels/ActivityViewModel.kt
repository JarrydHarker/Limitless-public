package com.example.limitless.data.ViewModels

import android.util.Log
import com.example.limitless.currentUser
import com.example.limitless.data.DbAccess
import com.example.limitless.data.Workout
import com.example.limitless.data.dbAccess
import java.time.LocalDate

class ActivityViewModel(val currentDate: LocalDate) {
    var arrWorkouts: MutableList<Workout> = mutableListOf()
    var steps = 0
    var db = DbAccess.GetInstance()
    var currentWorkout: Workout? = null

    fun AddWorkout(workout: Workout, onComplete: (Int?) -> Unit) {
        dbAccess.CreateWorkout(workout){ response ->
            dbAccess.GetWorkoutByName(workout.name!!, currentDate){ dbWorkout->
                workout.workoutId = dbWorkout?.workoutId
                Log.d("Fuck", "Workout ID: ${workout.workoutId.toString()}")
                arrWorkouts.add(workout)

                onComplete(workout.workoutId)
            }
        }
    }

    fun GetWorkouts(): List<Workout>?{
        return arrWorkouts
    }

    fun LoadUserData() {
        arrWorkouts = mutableListOf()

        dbAccess.GetUserWorkoutsByDate(currentUser?.userId!!, currentDate){workouts ->
            arrWorkouts.addAll(workouts)
        }

    }

    fun GetWorkout(workoutId: Int): Workout? {
        for(workout in arrWorkouts){
            if(workout.workoutId == workoutId){
                return workout
            }
        }

        return null
    }

    fun SaveWorkout(currentWorkout: Workout) {
        for(exercise in currentWorkout.arrExercises){
            exercise.workoutId = currentWorkout.workoutId!!
            dbAccess.CreateExercise(exercise){ response ->
                Log.d("Fuck", "${exercise.strength?.sets}")
                if(exercise.strength != null){
                    dbAccess.CreateStrength(exercise.strength!!){ response ->
                        Log.d("Fuck", response)
                    }
                }

                if(exercise.cardio != null){
                    dbAccess.CreateCardio(exercise.cardio!!)
                }
            }
        }
    }
}