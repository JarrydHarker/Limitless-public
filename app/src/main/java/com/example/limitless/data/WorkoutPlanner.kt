package com.example.limitless.data

class WorkoutPlanner {
    var arrExercises: MutableList<Exercise>? = null

    fun AddExercise(exercise: Exercise){
        if(arrExercises == null){
            arrExercises = mutableListOf(exercise)
        }else{
            arrExercises?.add(exercise)
        }
    }
}