package com.main.limitless.data.Offline

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.main.limitless.data.Cardio
import com.main.limitless.data.Converters
import com.main.limitless.data.Exercise
import com.main.limitless.data.Movement
import com.main.limitless.data.Strength
import com.main.limitless.data.Workout



@Database(
    entities = [Workout::class, Exercise::class, Movement::class, Cardio::class, Strength::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun movementDao(): MovementDao
    abstract fun cardioDao(): CardioDao
    abstract fun strengthDao(): StrengthDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "exercise_tracker_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

    @Dao
    interface WorkoutDao {
        @Insert (onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(workout: Workout)

        @Query("SELECT * FROM workouts")
        suspend fun getAllWorkouts(): List<Workout>

        @Delete
        suspend fun delete(workout: Workout)
    }

    @Dao
    interface ExerciseDao {
        @Insert (onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(exercise: Exercise)

        @Query("SELECT * FROM exercises WHERE workoutId = :workoutId")
        suspend fun getExercisesForWorkout(workoutId: Int): List<Exercise>

        @Delete
        suspend fun delete(exercise: Exercise)
    }

    @Dao
    interface MovementDao {
        @Insert (onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(movement: Movement)

        @Query("SELECT * FROM movements")
        suspend fun getAllMovements(): List<Movement>

        @Query("SELECT * FROM movements WHERE movementId = :movementId")
        suspend fun getMovementById(movementId: Int): Movement

        @Delete
        suspend fun delete(movement: Movement)
    }

    @Dao
    interface CardioDao {
        @Insert (onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(cardio: Cardio)

        @Query("SELECT * FROM cardio_exercises WHERE exerciseId = :exerciseId")
        suspend fun getCardioExercise(exerciseId: Int): Cardio?

        @Delete
        suspend fun delete(cardio: Cardio)
    }

    @Dao
    interface StrengthDao {
        @Insert (onConflict = OnConflictStrategy.REPLACE)
        suspend fun insert(strength: Strength)

        @Query("SELECT * FROM strength_exercises WHERE exerciseId = :exerciseId")
        suspend fun getStrengthExercise(exerciseId: Int): Strength?

        @Delete
        suspend fun delete(strength: Strength)
    }