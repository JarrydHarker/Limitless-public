package com.main.limitless.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    // Exercise List Converters
    @TypeConverter
    fun fromExerciseList(value: List<Exercise>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toExerciseList(value: String): List<Exercise>? {
        val listType = object : TypeToken<List<Exercise>>() {}.type
        return gson.fromJson(value, listType)
    }

    // Movement Object Converters
    @TypeConverter
    fun fromMovement(value: Movement?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMovement(value: String): Movement? {
        return gson.fromJson(value, Movement::class.java)
    }

    // Cardio Object Converters
    @TypeConverter
    fun fromCardio(value: Cardio?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCardio(value: String): Cardio? {
        return gson.fromJson(value, Cardio::class.java)
    }

    // Strength Object Converters
    @TypeConverter
    fun fromStrength(value: Strength?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStrength(value: String): Strength? {
        return gson.fromJson(value, Strength::class.java)
    }
}
