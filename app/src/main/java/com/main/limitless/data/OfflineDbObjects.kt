package com.main.limitless.data

import androidx.room.Entity
import androidx.room.PrimaryKey

class OfflineDbObjects {
    @Entity(tableName = "workouts")
    data class Workout(
        @PrimaryKey val workoutId: Int = 0,
        val date: Long, // Store as timestamp (milliseconds)
        val name: String = "",
        val userId: String = ""
    )


    @Entity(tableName = "exercises")
    data class Exercise(
        @PrimaryKey val exerciseId: Int = 0,
        val movementId: Int = 0,
        val workoutId: Int = 0,
        val exerciseType: String, // Can be either "strength" or "cardio"
        val sets: Int? = null,
        val repetitions: Int? = null,
        val time: Int? = null,
        val distance: Double? = null
    )

    @Entity(tableName = "movements")
    data class Movement(
        @PrimaryKey val movementId: Int = 0,
        val name: String = "",
        val description: String? = null,
        val type: String = "",
        val bodypart: String = "",
        val equipment: String = "",
        val difficultyLevel: String = "",
        val max: Double = 0.0
    )

    @Entity(tableName = "cardio_exercises")
    data class Cardio(
        @PrimaryKey val exerciseId: Int = 0,
        val time: Int = 0,
        val distance: Double = 0.0
    )

    @Entity(tableName = "strength_exercises")
    data class Strength(
        @PrimaryKey val exerciseId: Int = 0,
        val sets: Int = 0,
        val repetitions: Int = 0,
        val favourite: Boolean = false
    )
}