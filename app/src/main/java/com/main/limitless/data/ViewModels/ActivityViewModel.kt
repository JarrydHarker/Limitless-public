package com.main.limitless.data.ViewModels

import android.content.Context
import android.util.Log
import com.main.limitless.activityViewModel
import com.main.limitless.currentUser
import com.main.limitless.data.Offline.AppDatabase
import com.main.limitless.data.Movement
import com.main.limitless.data.Workout
import com.main.limitless.data.dbAccess
import com.main.limitless.isOnline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate

class ActivityViewModel(val currentDate: LocalDate) {
    var arrWorkouts: MutableList<Workout> = mutableListOf()
    var steps = 0
    var currentWorkout: Workout? = null

    fun AddWorkout(workout: Workout, onComplete: (Int?) -> Unit) {
        dbAccess.CreateWorkout(workout){ response ->
            dbAccess.GetWorkoutByName(workout.name, currentDate){ dbWorkout->
                workout.workoutId = dbWorkout?.workoutId!!
                arrWorkouts.add(workout)

                onComplete(workout.workoutId)
            }
        }
    }

    fun GetWorkouts(): List<Workout> {
        return arrWorkouts
    }

    fun LoadUserData(context: Context) {
        arrWorkouts = mutableListOf()

        CoroutineScope(Dispatchers.Main).launch {
            if (isOnline) {
                // Attempt to load data from online database
                currentUser?.userId?.let { userId ->
                    try {
                        // Load workouts asynchronously in IO context
                        val workouts = withContext(Dispatchers.IO) {
                            dbAccess.GetUserWorkoutsByDate(userId, currentDate)
                        }

                        for (w in workouts) {
                            val workout = Workout(w.workoutId, w.date, w.name, w.userId)
                            arrWorkouts.add(workout)

                            // Load exercises for the workout
                            val exercises = withContext(Dispatchers.IO) {
                                dbAccess.GetExercisesByWorkoutId(workout.workoutId)
                            }
                            workout.AddExercises(exercises)

                            // Load movement, strength, and cardio details for each exercise
                            for (exercise in workout.arrExercises) {
                                withContext(Dispatchers.IO) {
                                    exercise.movement = dbAccess.GetMovement(exercise.movementId) ?: Movement()
                                    exercise.strength = dbAccess.GetStrength(exercise.exerciseId)
                                    exercise.cardio = dbAccess.GetCardio(exercise.exerciseId)
                                }
                            }
                        }

                        // Update the offline database once all data is loaded
                        try {
                            withContext(Dispatchers.IO) {
                                UpdateOfflineDb(context)
                            }
                        } catch (ex: Exception) {
                            Log.e("OfflineSync", "Error updating offline DB: ${ex.message}")
                        }

                    } catch (ex: Exception) {
                        Log.e("DataLoad", "Error loading data online: ${ex.message}")
                    }
                } ?: Log.e("LoadUserData", "User ID is null")

            } else {
                Log.d("OfflineLoad", "It gets to the offline else")
                // Handle offline case
                try {
                    withContext(Dispatchers.IO) {
                        LoadOfflineData(context)
                    }
                } catch (ex: Exception) {
                    Log.e("OfflineDataLoad", "Error loading offline data: ${ex.message}")
                }
            }
        }
    }


    suspend fun LoadOfflineData(context: Context) {
        val offlineDB = AppDatabase.getDatabase(context)
        val offlineWorkouts = offlineDB.workoutDao().getAllWorkouts()
        Log.d("LoadOfflineData", "Fetched offline workouts: $offlineWorkouts")

        for(workout in offlineWorkouts){
            val exercises = offlineDB.exerciseDao().getExercisesForWorkout(workout.workoutId)
            workout.arrExercises.addAll(exercises)
            Log.d("LoadOfflineData", "Fetched exercises for workout ${workout.workoutId}: $exercises")

            for(exercise in workout.arrExercises){
                exercise.movement = offlineDB.movementDao().getMovementById(exercise.movementId)
                Log.d("LoadOfflineData", "Fetched movement for exercise ${exercise.exerciseId}: ${exercise.movement}")

                val strength = offlineDB.strengthDao().getStrengthExercise(exercise.exerciseId)
                val cardio = offlineDB.cardioDao().getCardioExercise(exercise.exerciseId)

                if(strength != null){
                    exercise.strength = strength
                    Log.d("LoadOfflineData", "Fetched strength for exercise ${exercise.exerciseId}: $strength")
                }

                if(cardio != null){
                    exercise.cardio = cardio
                    Log.d("LoadOfflineData", "Fetched cardio for exercise ${exercise.exerciseId}: $cardio")
                }
            }

            arrWorkouts.add(workout)
        }
    }


    suspend fun UpdateOfflineDb(context: Context) {
        val offlineDB = AppDatabase.getDatabase(context)
        val existingWorkouts = offlineDB.workoutDao().getAllWorkouts().toSet()
        Log.d("UpdateOfflineDb", "Fetched existing offline workouts: $existingWorkouts")

        for(workout in arrWorkouts){
            if(!existingWorkouts.contains(workout)){
                offlineDB.workoutDao().insert(workout)
                Log.d("UpdateOfflineDb", "Inserted workout: $workout")

                for(exercise in workout.arrExercises){
                    offlineDB.exerciseDao().insert(exercise)
                    offlineDB.movementDao().insert(exercise.movement)
                    Log.d("UpdateOfflineDb", "Inserted exercise: $exercise and movement: ${exercise.movement}")

                    if(exercise.cardio != null){
                        offlineDB.cardioDao().insert(exercise.cardio!!)
                        Log.d("UpdateOfflineDb", "Inserted cardio: ${exercise.cardio}")
                    } else {
                        offlineDB.strengthDao().insert(exercise.strength!!)
                        Log.d("UpdateOfflineDb", "Inserted strength: ${exercise.strength}")
                    }
                }
            } else {
                Log.d("UpdateOfflineDb", "Workout already exists: $workout")
            }
        }
        Log.d("UpdateOfflineDb", "Completed updating offline database")
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
            exercise.workoutId = currentWorkout.workoutId

            dbAccess.CreateExercise(exercise){ id ->
                if(exercise.strength != null){
                    exercise.strength?.exerciseId = id.toInt()
                    dbAccess.CreateStrength(exercise.strength!!){ response ->
                        Log.d("Fuck", response)
                    }
                }

                if(exercise.cardio != null){
                    exercise.cardio?.exerciseId = id.toInt()
                    dbAccess.CreateCardio(exercise.cardio!!)
                }
            }
        }
    }
}