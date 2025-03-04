package com.main.limitless.data
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.main.limitless.activityViewModel
import com.main.limitless.dbAccess
import com.main.limitless.nutritionViewModel
import java.time.LocalDate

class User(
    var userId: String = "",
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var password: String = "",
){
    var userInfo = UserInfo()
    var ratios = Ratios()
    var currentDay = Day(LocalDate.now(), userId = userId)

    fun InitialiseUserInfo(userInfo: UserInfo){
        this.userInfo = userInfo
    }

    fun GenerateID(){
        val pwHasher = PasswordHasher()
        val id = pwHasher.HashPassword(name + surname)

        this.userId = id.substring(0, 10)
    }

    fun SetCalorieWallet(wallet: Double){
        this.userInfo.calorieWallet = wallet
    }

    fun GetCalorieWallet(): Double{
        return userInfo.calorieWallet
    }

    fun GetStepGoal(): Int {
        return userInfo.stepGoal
    }

    fun GetWeight(): Double?{
        return userInfo.weight
    }

    fun GetWeightGoal(): Double? {
        return userInfo.weightGoal
    }

    fun SignUpUser(onComplete: (String) -> Unit) {
        val pwHasher = PasswordHasher()
        val hashedPW = pwHasher.HashPassword(password)
        password = hashedPW
        GenerateID()
        if(userId.isNotEmpty() && name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
             dbAccess.CreateUser(this){ response ->
                 onComplete(response)
            }
        }else {
            onComplete("Invalid user")
        }
    }

    fun CreateDay(){
        currentDay.date = nutritionViewModel.currentDate
        currentDay.calories = nutritionViewModel.CalculateTotalCalories()
        currentDay.steps = activityViewModel.steps
        currentDay.water = nutritionViewModel.water
        currentDay.userId = userId
        currentDay.activeTime = 0 //TODO Implement active time
        currentDay.weight = nutritionViewModel.weight

        dbAccess.CreateDay(currentDay){response ->
        }
    }

    fun SetHeight(height: Double){
        this.userInfo.height = height
    }

    fun SetWeight(weight: Double){
        this.userInfo.weight = weight
    }

    fun SetWeightGoal(weight: Double){
        this.userInfo.weightGoal = weight
    }

    fun SetStepGoal(steps: Int){
        this.userInfo.stepGoal = steps
    }

    fun SaveUserInfo(){
        userInfo.userId = userId
        dbAccess.CreateUserInfo(userInfo)
    }

    fun CalcCalorieWallet(){
        //TODO Equation for men
        userInfo.calorieWallet = 88.362 + (13.397 * userInfo.weightGoal!!) + (4.799 * userInfo.height!!) - (5.677 * 25)//TODO Change to age

        //TODO Equation for women
        //447.593 + (9.247 * userInfo.weightGoal!!) + (3.098 * userInfo.height!!) - (4.330 * 25)//TODO Change to age
    }

    fun LoadUserData(onComplete: () -> Unit) {
        dbAccess.GetUserInfo(userId){ info ->
            if(info != null){
                userInfo = info
                onComplete()
            }
            onComplete()
        }
    }

    fun LogOut(){
        SaveUserInfo()

        currentDay.date = nutritionViewModel.currentDate
        currentDay.calories = nutritionViewModel.CalculateTotalCalories()
        currentDay.steps = activityViewModel.steps
        currentDay.water = nutritionViewModel.water
        currentDay.userId = userId
        currentDay.activeTime = 0 //TODO Implement active time
        currentDay.weight = nutritionViewModel.weight

        dbAccess.UpdateDay(currentDay)
    }
}

data class UserInfo(
    var userId: String? = null,
    var height: Double? = null,
    var weight: Double? = null,
    var weightGoal: Double? = null,
    var calorieWallet: Double = 2000.0,
    var stepGoal: Int = 10000
)

class Ratios(
    var protein: Double = 0.35,
    var carbs: Double = 0.45,
    var fibre: Double = 0.014,
    var fat: Double = 0.20,
){
    // Change the protein ratio
    fun ChangeProteinRatio(newRatio: Double) {
        protein = newRatio

        if (CheckRatios()) {
            val remainder = 1.0 - protein
            redistributeRemainder(remainder, "protein")
        }
    }

    // Change the carb ratio
    fun ChangeCarbRatio(newRatio: Double) {
        carbs = newRatio

        if (CheckRatios()) {
            val remainder = 1.0 - carbs
            redistributeRemainder(remainder, "carb")
        }
    }

    // Change the fibre ratio
    fun ChangeFibreRatio(newRatio: Double) {
        fibre = newRatio

        if (CheckRatios()) {
            val remainder = 1.0 - fibre
            redistributeRemainder(remainder, "fibre")
        }
    }

    // Change the fat ratio
    fun ChangeFatRatio(newRatio: Double) {
        fat = newRatio

        if (CheckRatios()) {
            val remainder = 1.0 - fat
            redistributeRemainder(remainder, "fat")
        }
    }

    fun redistributeRemainder(remainder: Double, modifiedRatio: String) {
        // Store original ratios
        val originalRatios = mapOf(
            "protein" to 0.35,
            "carb" to 0.45,
            "fibre" to 0.014,
            "fat" to 0.20
        )

        // Calculate the total of the unmodified original ratios
        val totalUnmodifiedOriginal = (if (modifiedRatio != "protein") originalRatios["protein"]!! else 0.0) +
                (if (modifiedRatio != "carb") originalRatios["carb"]!! else 0.0) +
                (if (modifiedRatio != "fibre") originalRatios["fibre"]!! else 0.0) +
                (if (modifiedRatio != "fat") originalRatios["fat"]!! else 0.0)

        // Redistribute the leftover ratio proportionally to the unmodified ratios
        if (modifiedRatio != "protein") {
            protein = originalRatios["protein"]!! * (remainder / totalUnmodifiedOriginal)
        }
        if (modifiedRatio != "carb") {
            carbs = originalRatios["carb"]!! * (remainder / totalUnmodifiedOriginal)
        }
        if (modifiedRatio != "fibre") {
            fibre = originalRatios["fibre"]!! * (remainder / totalUnmodifiedOriginal)
        }
        if (modifiedRatio != "fat") {
            fat = originalRatios["fat"]!! * (remainder / totalUnmodifiedOriginal)
        }
    }

    private fun CheckRatios(): Boolean{
        return (protein + carbs + fibre + fat) > 1.0
    }
}

class Day(
    var date: LocalDate,  // Use String or LocalDate depending on your needs
    var steps: Int = 0,
    var calories: Double = 0.0,
    var weight: Double? = null,
    var activeTime: Int = 0,
    var water: Double = 0.0,
    var userId: String,
)

@Entity(tableName = "workouts")
class Workout(

   @PrimaryKey (autoGenerate = true) var workoutId: Int = 0,
    var date: String = "",
    var name: String = "",
    var userId: String = ""
){
    var arrExercises: MutableList<Exercise> = mutableListOf()

    fun AddExercise(exercise: Exercise) {
        arrExercises.add(exercise)
    }

    fun AddExercises(exercises: List<Exercise>) {
        arrExercises.addAll(exercises)
    }
}
@Entity(tableName = "exercises")
class Exercise(
   @PrimaryKey (autoGenerate = true) var exerciseId: Int = 0,
    var movementId: Int = 0,
    var workoutId: Int = 0
){
    var movement = Movement()

    fun GetName(): String {
        return movement.name
    }
fun getCategory(): String {
    return movement.type
}
    override fun toString(): String {
        if(strength != null){
            return "${movement.name}\t${strength!!.sets}x${strength!!.repetitions}"
        }else{
            return movement.name
        }
    }

    var cardio: Cardio? = null
    var strength: Strength? = null
}
@Entity(tableName = "movements")
class Movement(
 @PrimaryKey (autoGenerate = true) var movementId: Int? = 0,
    var name: String = "",
    var description: String? = null,
    var type: String = "",
    var bodypart: String = "",
    var equipment: String = "",
    var difficultyLevel: String = "",
    var max: Double = 0.0
){

}
@Entity(tableName = "cardio_exercises")
class Cardio(
    @PrimaryKey var exerciseId: Int? = 0,
    var time: Int = 0,
    var distance: Double = 0.0
){

}
@Entity(tableName = "strength_exercises")
class Strength(
 @PrimaryKey (autoGenerate = true) var exerciseId: Int? = 0,
    var sets: Int = 0,
    var repetitions: Int = 0,
    var favourite: Boolean = false
){

}

class MealFood(
    var mealId: Int = 0,
    var foodId: Int = 0,
    var date: LocalDate,
    var userId: String = ""
){

}

class Meal(
    var mealId: Int? = 0,
    var date: LocalDate? = null,  // Use String or LocalDate depending on your needs
    var userId: String? = null,
    var name: String = ""
){
    var arrFoods: MutableList<Food> = mutableListOf()
}

class Food(
    var foodId: Int? = 0,
    var mealId: String? = null,
    var category: String? = null,
    var description: String? = null,
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
    var calcium: Double? = null
){

}

