package com.example.limitless.data
import java.time.LocalDate

data class User(
    var userId: String = "",
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var password: String = "",
    var weightGoal: Double? = null,
    var calorieWallet: Double? = null,
    var stepGoal: Int = 0,
    var tblDays: MutableList<Day> = mutableListOf()
)

data class Day(
    var date: LocalDate,
    var userId: String = "",
    var steps: Int = 0,
    var calories: Double = 0.0,
    var weight: Double? = null,
    var activeTime: Int = 0,
    var water: Double = 0.0,
    var meals: MutableList<Meal> = mutableListOf(),
    var workouts: MutableList<Workout> = mutableListOf(),
    var user: User = User()
)

data class Meal(
    var mealId: String = "",
    var date: LocalDate? = null,
    var userId: String? = null,
    var name: String = "",
    var day: Day? = null,
    var foods: MutableList<Food> = mutableListOf()
)

data class Workout(
    var workoutId: String = "",
    var date: LocalDate,
    var userId: String = "",
    //var day: Day = Day(),
    var exercises: MutableList<Exercise> = mutableListOf()
)

data class Food(
    var foodId: String = "",
    var mealId: String? = null,
    var category: String = "",
    var description: String = "",
    var weight: Double? = null,
    var calories: Int = 0,
    var protein: Double? = null,
    var carbohydrates: Double? = null,
    var fibre: Double? = null,
    var fat: Double? = null,
    var cholesterol: Double? = null,
    var sugar: Double? = null,
    var saturatedFat: Double? = null,
    var vitaminB12: Double? = null,
    var vitaminB6: Double? = null,
    var vitaminK: Double? = null,
    var vitaminE: Double? = null,
    var vitaminC: Double? = null,
    var vitaminA: Double? = null,
    var zinc: Double? = null,
    var magnesium: Double? = null,
    var sodium: Double? = null,
    var potassium: Double? = null,
    var iron: Double? = null,
    var calcium: Double? = null,
    var meal: Meal? = null
)

data class Exercise(
    var exerciseId: String = "",
    var workoutId: String = "",
    var movementId: String = "",
    var cardio: CardioExercise = CardioExercise(),
    var strength: StrengthExercise = StrengthExercise(),
    var movement: Movement = Movement(),
    //var workout: Workout = Workout()
)

data class CardioExercise(
    var exerciseId: String = "",
    var time: Int = 0,
    var distance: Double = 0.0,
    var exercise: Exercise? = null
)

data class StrengthExercise(
    var exerciseId: String = "",
    var sets: Int = 0,
    var repetitions: Int = 0,
    var favourite: Boolean = false,
    var exercise: Exercise? = null
)

data class Movement(
    var movementId: String = "",
    var name: String = "",
    var description: String? = null,
    var type: String = "",
    var bodyPart: String = "",
    var equipment: String = "",
    var difficultyLevel: String = "",
    var max: Double? = null,
    var exercises: MutableList<Exercise> = mutableListOf()
)
