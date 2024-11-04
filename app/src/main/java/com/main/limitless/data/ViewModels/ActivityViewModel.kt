package com.main.limitless.data.ViewModels

import android.content.Context
import android.util.Log
import com.main.limitless.currentUser
import com.main.limitless.data.offline.AppDatabase
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

        if (isOnline) {
            // Launching a coroutine on the main thread if context is needed for UI updates
            CoroutineScope(Dispatchers.Main).launch {
                // Load workouts asynchronously
                val workouts = withContext(Dispatchers.IO) {
                    dbAccess.GetUserWorkoutsByDate(currentUser?.userId!!, currentDate)
                }
                // Process workouts and add exercises to each workout
                for (w in workouts) {
                    val workout = Workout(w.workoutId, w.date, w.name, w.userId)
                    arrWorkouts.add(workout)

                    // Load exercises for each workout
                    val exercises = withContext(Dispatchers.IO) {
                        dbAccess.GetExercisesByWorkoutId(workout.workoutId)
                    }
                    workout.AddExercises(exercises)

                    // Load movement, strength, and cardio details for each exercise
                    for (exercise in workout.arrExercises) {
                        exercise.movement = withContext(Dispatchers.IO) {
                            dbAccess.GetMovement(exercise.movementId)
                        } ?: Movement() // Default to empty Movement if null

                        exercise.strength = withContext(Dispatchers.IO) {
                            dbAccess.GetStrength(exercise.exerciseId)
                        }

                        exercise.cardio = withContext(Dispatchers.IO) {
                            dbAccess.GetCardio(exercise.exerciseId)
                        }
                    }
                }

                try{
                    // Offline database update code here, after all workouts and exercises are loaded
                    withContext(Dispatchers.IO) {
                        UpdateOfflineDb(context)
                    }
                }catch(ex: Exception){
                    Log.e("OfflineSync", "${ex}")
                }
            }
        } else {
            CoroutineScope(Dispatchers.Main).launch {
                // Handle offline case, e.g., loading data from local storage
                withContext(Dispatchers.IO) {
                    LoadOfflineData(context)
                }
            }
        }
    }

    suspend fun LoadOfflineData(context: Context) {
        val offlineDB = AppDatabase.getDatabase(context)
        val offlineWorkouts = offlineDB.workoutDao().getAllWorkouts()

        for(workout in offlineWorkouts){
            workout.arrExercises.addAll(offlineDB.exerciseDao().getExercisesForWorkout(workout.workoutId))

            for(exercise in workout.arrExercises){
                exercise.movement = offlineDB.movementDao().getMovementById(exercise.movementId)

                val strength = offlineDB.strengthDao().getStrengthExercise(exercise.exerciseId)
                val cardio = offlineDB.cardioDao().getCardioExercise(exercise.exerciseId)

                if(strength != null){
                    exercise.strength = strength
                }

                if(cardio != null){
                    exercise.cardio = cardio
                }
            }
        }

    }

    suspend fun UpdateOfflineDb(context: Context) {
        val offlineDB = AppDatabase.getDatabase(context)

        for(workout in arrWorkouts){
            if(!offlineDB.workoutDao().getAllWorkouts().contains(workout)){
                offlineDB.workoutDao().insert(workout)

                for(exercise in workout.arrExercises){
                    offlineDB.exerciseDao().insert(exercise)
                    offlineDB.movementDao().insert(exercise.movement)

                    if(exercise.cardio != null){
                        offlineDB.cardioDao().insert(exercise.cardio!!)
                    }else {
                        offlineDB.strengthDao().insert(exercise.strength!!)
                    }
                }
            }
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