package com.main.limitless.data

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.Executors


class DbAccess private constructor(){

    companion object {
        private var instance: DbAccess? = null


        fun GetInstance(): DbAccess {
            if (instance == null) {
                instance = DbAccess()
            }
            return instance!!
        }
    }

    private val apiUrl = "https://opscapi-cnbqbvc2g7e4hyec.switzerlandnorth-01.azurewebsites.net/api/Limitless"
    private val epUser = "/User"
    private val epUserInfo = "/UserInfo"
    private val epDay = "/Day"
    private val epExercise = "/Exercise"
    private val epCardio = "/Cardio"
    private val epStrength = "/Strength"
    private val epMeal = "/Meal"
    private val epFood = "/Food"
    private val epMovement = "/Movement"
    private val epWorkout = "/Workout"
    private val epRatios = "/Ratios"
    private val epMealFood = "/MealFood"

    //Create//
    fun CreateUser(user: User, onComplete: (String) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()

        var responseMessage = ""

        executor.execute{
            try{
                val url = URL(apiUrl + epUser)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize user object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(user)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            }catch (ex: Exception){
                // Handle exceptions appropriately
                Log.e("DbAccessError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        onComplete(responseMessage)
    }

    fun CreateUserInfo(user: UserInfo): String {
        val executor = Executors.newSingleThreadExecutor()

        var responseMessage = ""

        executor.execute{
            try{
                val url = URL(apiUrl + epUserInfo)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize user object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(user)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            }catch (ex: Exception){
                // Handle exceptions appropriately
                Log.e("DbAccessError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage
    }

    fun CreateDay(day: Day, onComplete: (String) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()

        var responseMessage = ""

        executor.execute{
            try{
                val url = URL(apiUrl + epDay)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize user object to JSON using Gson
                val gson = GsonBuilder()
                    .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())  // Register LocalDate serializer
                    .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
                    .create()

                val jsonInputString = gson.toJson(day)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            }catch (ex: Exception){
                // Handle exceptions appropriately
                Log.e("DbAccessError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
            onComplete(responseMessage)
        }
    }

    fun CreateExercise(exercise: Exercise, onComplete: (String) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute{
            try{
                val url = URL(apiUrl + epExercise)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize user object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(exercise)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }
            }catch (ex: Exception){
                // Handle exceptions appropriately
                Log.e("DbAccessError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
            onComplete(responseMessage)
        }
    }

    fun CreateCardio(cardio: Cardio): String {
        val executor = Executors.newSingleThreadExecutor()

        var responseMessage = ""

        executor.execute{
            try{
                val url = URL(apiUrl + epCardio)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize user object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(cardio)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            }catch (ex: Exception){
                // Handle exceptions appropriately
                Log.e("DbAccessError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage
    }

    fun CreateMealFood(mealfood: MealFood): String {
        val executor = Executors.newSingleThreadExecutor()

        var responseMessage = ""

        executor.execute{
            try{
                val url = URL(apiUrl + epMealFood)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize user object to JSON using Gson
                val gson = GsonBuilder()
                    .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())  // Register LocalDate serializer
                    .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
                    .create()

                val jsonInputString = gson.toJson(mealfood)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            }catch (ex: Exception){
                // Handle exceptions appropriately
                Log.e("DbAccessError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage
    }

    fun GetFoodsByMeal(mealId: Int, onComplete: (List<Food>) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        val mainHandler = Handler(Looper.getMainLooper())
        var food: List<Food> = emptyList()

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epFood + "/Meal?mealId=$mealId") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = Gson()

                        // Deserialize the JSON array into a List<User>
                        food = gson.fromJson(jsonResponse, Array<Food>::class.java).toList()
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetAllFoodError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetAllFoodError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
            mainHandler.post{
                onComplete(food)
            }

        }
    }

    fun CreateStrength(strength: Strength, onComplete: (String) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()

        var responseMessage = ""

        executor.execute{
            try{
                val url = URL(apiUrl + epStrength)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize user object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(strength)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            }catch (ex: Exception){
                // Handle exceptions appropriately
                Log.e("DbAccessError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
            onComplete(responseMessage)
        }
    }

    fun CreateMeal(meal: Meal, onComplete: (String) -> Unit){
        val executor = Executors.newSingleThreadExecutor()

        var responseMessage = ""

        executor.execute{
            try{
                val url = URL(apiUrl + epMeal)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize user object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(meal)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            }catch (ex: Exception){
                // Handle exceptions appropriately
                Log.e("DbAccessError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
            Log.d("Fuck", "Create meal response: $responseMessage")
            onComplete(responseMessage)
        }

    }

    fun CreateFood(food: Food): String {
        val executor = Executors.newSingleThreadExecutor()

        var responseMessage = ""

        executor.execute{
            try{
                val url = URL(apiUrl + epFood)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize user object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(food)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            }catch (ex: Exception){
                // Handle exceptions appropriately
                Log.e("DbAccessError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage
    }

    fun CreateMovement(movement: Movement): String {
        val executor = Executors.newSingleThreadExecutor()

        var responseMessage = ""

        executor.execute{
            try{
                val url = URL(apiUrl + epMovement)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize user object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(movement)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            }catch (ex: Exception){
                // Handle exceptions appropriately
                Log.e("DbAccessError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage
    }

    fun CreateWorkout(workout: Workout, onComplete: (String) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()

        var responseMessage = ""

        executor.execute{
            try{
                val url = URL(apiUrl + epWorkout)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize user object to JSON using Gson
                val gson = GsonBuilder()
                    .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())  // Register LocalDate serializer
                    .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
                    .create()

                val jsonInputString = gson.toJson(workout)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            }catch (ex: Exception){
                // Handle exceptions appropriately
                Log.e("DbAccessError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
            Log.d("Fuck", responseMessage)
            onComplete(responseMessage)
        }
    }
    //Create//

    //Read//
    fun GetUser(userId: String): User?{
        val executor = Executors.newSingleThreadExecutor()

        var user: User? = null

        executor.execute {
            try {
                // Construct URL with query parameter
                val url = URL( apiUrl + epUser + "?userId=$userId")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()

                        // Deserialize JSON response into User object
                        val gson = Gson()
                        user = gson.fromJson(jsonResponse, User::class.java)
                    }
                } else {
                    // Handle error if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        val errorMessage = reader.readText()
                        Log.e("GetUserError", "Error: $errorMessage")
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetUserError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return user // Return the deserialized User object (if any)
    }

    fun GetRatio(userId: String): Ratios? {
        val executor = Executors.newSingleThreadExecutor()

        var ratios: Ratios? = null

        executor.execute {
            try {
                // Construct URL with query parameter
                val url = URL( apiUrl + epRatios + "?userId=$userId")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()

                        // Deserialize JSON response into User object
                        val gson = Gson()
                        ratios = gson.fromJson(jsonResponse, Ratios::class.java)
                    }
                } else {
                    // Handle error if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        val errorMessage = reader.readText()
                        Log.e("GetUserError", "Error: $errorMessage")
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetUserError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return ratios // Return the deserialized User object (if any)
    }

    fun GetUserByEmail(email: String, onComplete: (User?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        var user: User? = null

        executor.execute {
            try {
                // Construct URL with query parameter
                val url = URL( apiUrl + epUser + "/Email?email=$email")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()

                        // Deserialize JSON response into User object
                        val gson = Gson()
                        user = gson.fromJson(jsonResponse, User::class.java)
                    }
                } else {
                    // Handle error if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        val errorMessage = reader.readText()
                        Log.e("GetUserError", "Error: $errorMessage")
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetUserError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }

            onComplete(user)  // Return the deserialized User object (if any)
        }
    }

    fun GetAllUsers(): List<User> {
        val executor = Executors.newSingleThreadExecutor()
        var users: List<User> = emptyList()

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epUser + "/All") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = Gson()

                        // Deserialize the JSON array into a List<User>
                        users = gson.fromJson(jsonResponse, Array<User>::class.java).toList()
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetAllUsersError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetAllUsersError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return users // Return the list of users (could be empty if request fails)
    }

    fun GetAllDays(): List<Day> {
        val executor = Executors.newSingleThreadExecutor()
        var days: List<Day> = emptyList()

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epDay + "/All") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = Gson()

                        // Deserialize the JSON array into a List<User>
                        days = gson.fromJson(jsonResponse, Array<Day>::class.java).toList()
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetAllDaysError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetAllDaysError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return days // Return the list of users (could be empty if request fails)
    }

    fun GetAllExercises(): List<Exercise> {
        val executor = Executors.newSingleThreadExecutor()
        var exercise: List<Exercise> = emptyList()

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epExercise + "/All") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = Gson()

                        // Deserialize the JSON array into a List<User>
                        exercise = gson.fromJson(jsonResponse, Array<Exercise>::class.java).toList()
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetAllExerciseError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetAllExerciseError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return exercise // Return the list of users (could be empty if request fails)
    }

    fun GetAllCardio(): List<Cardio> {
        val executor = Executors.newSingleThreadExecutor()
        var cardio: List<Cardio> = emptyList()

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epCardio + "/All") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = Gson()

                        // Deserialize the JSON array into a List<User>
                        cardio = gson.fromJson(jsonResponse, Array<Cardio>::class.java).toList()
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetAllCardioError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetAllCardioError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return cardio // Return the list of users (could be empty if request fails)
    }

    fun GetUserMeals(userId: String, onComplete: (List<Meal>) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        val mainHandler = Handler(Looper.getMainLooper())
        var meals: List<Meal> = emptyList()

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epMeal + "/User?userId=$userId") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = Gson()

                        // Deserialize the JSON array into a List<User>
                        meals = gson.fromJson(jsonResponse, Array<Meal>::class.java).toList()
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetAllMealsError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetAllMealsError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }

            mainHandler.post {
                onComplete(meals)
            }
        }
    }

    fun GetAllStrength(): List<Strength> {
        val executor = Executors.newSingleThreadExecutor()
        var strength: List<Strength> = emptyList()

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epStrength + "/All") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = Gson()

                        // Deserialize the JSON array into a List<User>
                        strength = gson.fromJson(jsonResponse, Array<Strength>::class.java).toList()
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetAllStrengthError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetAllStrengthError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return strength // Return the list of users (could be empty if request fails)
    }

    fun GetUserInfo(userId: String, onComplete: (UserInfo?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()

        var userInfo: UserInfo? = null

        executor.execute {
            try {
                // Construct URL with query parameter
                val url = URL( apiUrl + epUserInfo + "?userId=$userId")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()

                        // Deserialize JSON response into User object
                        val gson = Gson()
                        userInfo = gson.fromJson(jsonResponse, UserInfo::class.java)
                    }
                } else {
                    // Handle error if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        val errorMessage = reader.readText()
                        Log.e("GetUserInfoError", "Error: $errorMessage")
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetUserInfoError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
            onComplete(userInfo) // Return the deserialized User object (if any)
        }
    }

    fun GetAllMeals(): List<Meal> {
        val executor = Executors.newSingleThreadExecutor()
        var meals: List<Meal> = emptyList()

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epMeal + "/All") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = Gson()

                        // Deserialize the JSON array into a List<User>
                        meals = gson.fromJson(jsonResponse, Array<Meal>::class.java).toList()
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetAllMealsError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetAllMealsError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return meals // Return the list of users (could be empty if request fails)
    }

    fun GetAllFood(pageNum: Int, pageSize: Int): List<Food> {
        val executor = Executors.newSingleThreadExecutor()
        var food: List<Food> = emptyList()

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epFood + "/All?pageNumber=$pageNum&pageSize=$pageSize") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = Gson()

                        // Deserialize the JSON array into a List<User>
                        food = gson.fromJson(jsonResponse, Array<Food>::class.java).toList()
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetAllFoodError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetAllFoodError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return food // Return the list of users (could be empty if request fails)
    }

    suspend fun SearchForFood(strSearch: String): List<Food> {
        return withContext(Dispatchers.IO) {
            var food: List<Food> = emptyList()

            try {
                val url = URL("$apiUrl$epFood/Search?strSearch=$strSearch")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()
                        val gson = Gson()

                        // Deserialize the JSON array into a List<Food>
                        food = gson.fromJson(jsonResponse, Array<Food>::class.java).toList()
                    }
                } else {
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("SearchForFood", reader.readText())
                    }
                }
            } catch (ex: Exception) {
                Log.e("SearchForFood", ex.toString())
            }

            return@withContext food
        }
    }

    fun GetAllMovements(): List<Movement> {
        val executor = Executors.newSingleThreadExecutor()
        var movement: List<Movement> = emptyList()

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epMovement + "/All") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = Gson()

                        // Deserialize the JSON array into a List<User>
                        movement = gson.fromJson(jsonResponse, Array<Movement>::class.java).toList()
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetAllMovementError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetAllMovementError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return movement // Return the list of users (could be empty if request fails)
    }

    suspend fun SearchForMovements(strSearch: String): List<Movement> {
        return withContext(Dispatchers.IO) {
            var movements: List<Movement> = emptyList()

            try {
                val url = URL(apiUrl + epMovement + "/Search?strSearch=$strSearch")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()
                        val gson = Gson()

                        // Deserialize the JSON array into a List<Food>
                        movements = gson.fromJson(jsonResponse, Array<Movement>::class.java).toList()
                    }
                } else {
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("SearchForMoves", reader.readText())
                    }
                }
            } catch (ex: Exception) {
                Log.e("SearchForMoves", ex.toString())
            }

            Log.d("SearchForMoves", movements.size.toString())

            return@withContext movements
        }
    }

    fun GetUserMealsByDate(userId: String, currentDate: LocalDate): List<Meal> {
        val executor = Executors.newSingleThreadExecutor()
        var meals: List<Meal> = emptyList()

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epMeal + "/User/Date?userId=$userId&date=$currentDate") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = GsonBuilder()
                            .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())  // Register LocalDate serializer
                            .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
                            .create()

                        // Deserialize the JSON array into a List<User>
                        meals = gson.fromJson(jsonResponse, Array<Meal>::class.java).toList()
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetAllMealsError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetAllMealsError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return meals // Return the list of users (could be empty if request fails)
    }

    suspend fun GetExercisesByWorkoutId(workoutId: Int?): List<Exercise> {
        return withContext(Dispatchers.IO) {
            val url = URL("$apiUrl$epExercise/Workout?workoutId=$workoutId")
            val connection = url.openConnection() as HttpURLConnection
            val exercises: List<Exercise> = try {
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()
                        val gson = GsonBuilder()
                            .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
                            .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
                            .create()
                        gson.fromJson(jsonResponse, Array<Exercise>::class.java).toList()
                    }
                } else {
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetExercisesError", reader.readText())
                    }
                    emptyList() // Return an empty list on error
                }
            } catch (ex: Exception) {
                Log.e("GetExercisesError", ex.toString())
                ex.printStackTrace()
                emptyList() // Return an empty list if an exception occurs
            } finally {
                connection.disconnect()
            }
            exercises
        }
    }

    fun GetUserWorkouts(userId: String, onComplete: (List<Workout>) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        var workout: List<Workout> = emptyList()

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epWorkout + "/User?userId=$userId") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = GsonBuilder()
                            .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())  // Register LocalDate serializer
                            .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
                            .create()

                        // Deserialize the JSON array into a List<User>
                        workout = gson.fromJson(jsonResponse, Array<Workout>::class.java).toList()
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetAllWorkoutError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetAllWorkoutError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }

            Handler(Looper.getMainLooper()).post {
                onComplete(workout)
            }
        }
    }

    suspend fun GetUserWorkoutsByDate(userId: String, date: LocalDate): List<Workout> {
        return withContext(Dispatchers.IO) {
            val url = URL("$apiUrl$epWorkout/User?userId=$userId&date=${date.year}-${date.monthValue}-${date.dayOfMonth}")
            val connection = url.openConnection() as HttpURLConnection
            val workouts: List<Workout> = try {
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Check response code
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()
                        val gson = GsonBuilder()
                            .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
                            .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
                            .create()
                        gson.fromJson(jsonResponse, Array<Workout>::class.java).toList()
                    }
                } else {
                    // Log the error if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetUserWorkoutsError", reader.readText())
                    }
                    emptyList() // Return an empty list if there's an error
                }
            } catch (ex: Exception) {
                Log.e("GetUserWorkoutsError", ex.toString())
                ex.printStackTrace()
                emptyList() // Return an empty list if there's an exception
            } finally {
                connection.disconnect()
            }
            workouts
        }
    }

    fun GetWorkoutByName(name: String, date: LocalDate, onComplete: (Workout?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        var workout: Workout? = null

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epWorkout + "/Name?name=$name&date=${date.year}-${date.monthValue}-${date.dayOfMonth}") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = GsonBuilder()
                            .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())  // Register LocalDate serializer
                            .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
                            .create()

                        // Deserialize the JSON array into a List<User>
                        workout = gson.fromJson(jsonResponse, Workout::class.java)
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetWorkoutByNameError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetWorkoutByNameError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
            onComplete(workout) // Return the list of users (could be empty if request fails)
        }
    }

    fun GetMealByName(name: String, date: LocalDate, onComplete: (Meal?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()
        var meal: Meal? = null

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epMeal + "/Name?name=$name&date=${date.year}-${date.monthValue}-${date.dayOfMonth}") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = GsonBuilder()
                            .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())  // Register LocalDate serializer
                            .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
                            .create()

                        // Deserialize the JSON array into a List<User>
                        meal = gson.fromJson(jsonResponse, Meal::class.java)
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetMealByNameError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetMealByNameError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
            onComplete(meal) // Return the list of users (could be empty if request fails)
        }
    }

    fun GetAllWorkouts(): List<Workout> {
        val executor = Executors.newSingleThreadExecutor()
        var workout: List<Workout> = emptyList()

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + epWorkout + "/All") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = Gson()

                        // Deserialize the JSON array into a List<User>
                        workout = gson.fromJson(jsonResponse, Array<Workout>::class.java).toList()
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetAllWorkoutError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetAllWorkoutError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return workout // Return the list of users (could be empty if request fails)
    }

    fun GetCategories(): List<String> {
        val executor = Executors.newSingleThreadExecutor()
        var categories: List<String> = emptyList()

        executor.execute {
            try {
                // Construct the URL for the GET request
                val url = URL(apiUrl + "/Category") // Assuming the endpoint is something like /users
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to GET
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText() // Read the server's JSON response
                        val gson = Gson()

                        // Deserialize the JSON array into a List<User>
                        categories = gson.fromJson(jsonResponse, Array<String>::class.java).toList()
                    }
                } else {
                    // Handle error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetCategoriesError", reader.readText())
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetCategoriesError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return categories // Return the list of users (could be empty if request fails)
    }

    fun GetDay(date: LocalDate, userId: String, onComplete: (Day?) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()

        var day: Day? = null

        executor.execute {
            try {
                // Construct URL with query parameter
                val url = URL( apiUrl + epDay + "?date=${date.year}-${date.monthValue}-${date.dayOfMonth}&userId=${userId}")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()

                        // Deserialize JSON response into User object
                        val gson = GsonBuilder()
                            .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())  // Register LocalDate serializer
                            .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
                            .create()

                        day = gson.fromJson(jsonResponse, Day::class.java)
                    }
                } else {
                    // Handle error if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        val errorMessage = reader.readText()
                        Log.e("GetUserError", "Error: $errorMessage")
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetUserError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
            onComplete(day)
        }
    }

    fun GetExercise(exerciseId: String): Exercise?{
        val executor = Executors.newSingleThreadExecutor()

        var exercise: Exercise? = null

        executor.execute {
            try {
                // Construct URL with query parameter
                val url = URL( apiUrl + epExercise + "?exerciseId=${exerciseId}")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()

                        // Deserialize JSON response into User object
                        val gson = Gson()
                        exercise = gson.fromJson(jsonResponse, Exercise::class.java)
                    }
                } else {
                    // Handle error if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        val errorMessage = reader.readText()
                        Log.e("GetUserError", "Error: $errorMessage")
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetUserError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return exercise // Return the deserialized User object (if any)
    }

    suspend fun GetCardio(exerciseId: Int): Cardio? {
        return withContext(Dispatchers.IO) {
            val url = URL("$apiUrl$epCardio?exerciseId=$exerciseId")
            val connection = url.openConnection() as HttpURLConnection
            var cardio: Cardio? = null

            try {
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()
                        val gson = Gson()
                        cardio = gson.fromJson(jsonResponse, Cardio::class.java)
                    }
                } else {
                    InputStreamReader(connection.errorStream).use { reader ->
                        val errorMessage = reader.readText()
                        Log.e("GetCardioError", "Error: $errorMessage")
                    }
                }
            } catch (ex: Exception) {
                Log.e("GetCardioError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            } finally {
                connection.disconnect()
            }

            cardio
        }
    }


    suspend fun GetStrength(exerciseId: Int): Strength? {
        return withContext(Dispatchers.IO) {
            val url = URL("$apiUrl$epStrength?exerciseId=$exerciseId")
            val connection = url.openConnection() as HttpURLConnection
            val strength: Strength? = try {
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()
                        val gson = Gson()
                        gson.fromJson(jsonResponse, Strength::class.java)
                    }
                } else {
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetStrengthError", "Error: ${reader.readText()}")
                    }
                    null // Return null if the response code indicates failure
                }
            } catch (ex: Exception) {
                Log.e("GetStrengthError", ex.toString())
                ex.printStackTrace()
                null // Return null if an exception occurs
            } finally {
                connection.disconnect()
            }
            strength
        }
    }


    fun GetMeal(mealId: Meal): Meal?{
        val executor = Executors.newSingleThreadExecutor()

        var meal: Meal? = null

        executor.execute {
            try {
                // Construct URL with query parameter
                val url = URL( apiUrl + epMeal + "?mealId=$mealId")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()

                        // Deserialize JSON response into User object
                        val gson = Gson()
                        meal = gson.fromJson(jsonResponse, Meal::class.java)
                    }
                } else {
                    // Handle error if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        val errorMessage = reader.readText()
                        Log.e("GetMealError", "Error: $errorMessage")
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetMealError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return meal // Return the deserialized User object (if any)
    }

    fun GetFood(foodId: Food): Food?{
        val executor = Executors.newSingleThreadExecutor()

        var food: Food? = null

        executor.execute {
            try {
                // Construct URL with query parameter
                val url = URL( apiUrl + epFood + "?foodId=$foodId")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()

                        // Deserialize JSON response into User object
                        val gson = Gson()
                        food = gson.fromJson(jsonResponse, Food::class.java)
                    }
                } else {
                    // Handle error if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        val errorMessage = reader.readText()
                        Log.e("GetFoodError", "Error: $errorMessage")
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetFoodError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return food // Return the deserialized User object (if any)
    }

    suspend fun GetMovement(movementId: Int): Movement? {
        return withContext(Dispatchers.IO) {
            val url = URL("$apiUrl$epMovement?movementId=$movementId")
            val connection = url.openConnection() as HttpURLConnection
            val movement: Movement? = try {
                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()
                        val gson = Gson()
                        gson.fromJson(jsonResponse, Movement::class.java)
                    }
                } else {
                    InputStreamReader(connection.errorStream).use { reader ->
                        Log.e("GetMovementError", "Error: ${reader.readText()}")
                    }
                    null // Return null if the response code indicates failure
                }
            } catch (ex: Exception) {
                Log.e("GetMovementError", ex.toString())
                ex.printStackTrace()
                null // Return null if an exception occurs
            } finally {
                connection.disconnect()
            }
            movement
        }
    }


    fun GetWorkout(workoutId: Workout): Workout?{
        val executor = Executors.newSingleThreadExecutor()

        var workout: Workout? = null

        executor.execute {
            try {
                // Construct URL with query parameter
                val url = URL( apiUrl + epWorkout + "?workoutId=$workoutId")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "GET"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonResponse = reader.readText()

                        // Deserialize JSON response into User object
                        val gson = Gson()
                        workout = gson.fromJson(jsonResponse, Workout::class.java)
                    }
                } else {
                    // Handle error if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        val errorMessage = reader.readText()
                        Log.e("GetWorkoutError", "Error: $errorMessage")
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetWorkoutError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return workout // Return the deserialized User object (if any)
    }
    //Read//

    //Update//
    fun UpdateUser(user: User): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for updating the user
                val url = URL(apiUrl + epUser)
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to POST
                connection.requestMethod = "PUT"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize the User object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(user)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("UpdateUserError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun UpdateDay(day: Day): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for updating the user
                val url = URL(apiUrl + epDay)
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to POST
                connection.requestMethod = "PUT"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize the User object to JSON using Gson
                val gson = GsonBuilder()
                    .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())  // Register LocalDate serializer
                    .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
                    .create()

                val jsonInputString = gson.toJson(day)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("UpdateDayError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun UpdateExercise(exercise: Exercise): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for updating the user
                val url = URL(apiUrl + epExercise)
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to POST
                connection.requestMethod = "PUT"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize the User object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(exercise)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("UpdateExerciseError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun UpdateCardio(cardio: Cardio): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for updating the user
                val url = URL(apiUrl + epCardio)
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to POST
                connection.requestMethod = "PUT"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize the User object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(cardio)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("UpdateCardioError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun UpdateStrength(strength: Strength): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for updating the user
                val url = URL(apiUrl + epStrength)
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to POST
                connection.requestMethod = "PUT"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize the User object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(strength)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("UpdateUserError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun UpdateMeal(meal: Meal): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for updating the user
                val url = URL(apiUrl + epMeal)
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to POST
                connection.requestMethod = "PUT"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize the User object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(meal)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("UpdateMealError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun UpdateFood(food: Food): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for updating the user
                val url = URL(apiUrl + epUser)
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to POST
                connection.requestMethod = "PUT"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize the User object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(food)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("UpdateFoodError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun UpdateMovement(movement: Movement): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for updating the user
                val url = URL(apiUrl + epMovement)
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to POST
                connection.requestMethod = "PUT"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize the User object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(movement)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("UpdateMovementError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun UpdateWorkout(workout: Workout): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for updating the user
                val url = URL(apiUrl + epWorkout)
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to POST
                connection.requestMethod = "PUT"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Serialize the User object to JSON using Gson
                val gson = Gson()
                val jsonInputString = gson.toJson(workout)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's response message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("UpdateWorkoutError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }
    //Update//

    //Delete//
    fun DeleteUser(userId: String): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for deleting the user with the userId
                val url = URL(apiUrl + epUser + "?userId=${userId}")
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to DELETE
                connection.requestMethod = "DELETE"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's success message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("DeleteUserError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun DeleteDay(date: LocalDate, userId: String): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for deleting the user with the userId
                val url = URL(apiUrl + epDay + "?date=${date.year}-${date.monthValue}-${date.dayOfMonth}&userId=${userId}")
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to DELETE
                connection.requestMethod = "DELETE"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's success message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("DeleteDayError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun DeleteCardio(cardioId: Cardio): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for deleting the user with the userId
                val url = URL(apiUrl + epCardio + "?cardioId=${cardioId}")
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to DELETE
                connection.requestMethod = "DELETE"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's success message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("DeleteCardioError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun DeleteStrength(strengthId: Strength): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for deleting the user with the userId
                val url = URL(apiUrl + epStrength+ "?strengthId=${strengthId}")
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to DELETE
                connection.requestMethod = "DELETE"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's success message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("DeleteStrengthError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun DeleteMeal(mealId: Meal): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for deleting the user with the userId
                val url = URL(apiUrl + epMeal+ "?mealId=${mealId}")
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to DELETE
                connection.requestMethod = "DELETE"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's success message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("DeleteMealError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun DeleteFood(foodId: Food): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for deleting the user with the userId
                val url = URL(apiUrl + epFood+ "?foodId=${foodId}")
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to DELETE
                connection.requestMethod = "DELETE"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's success message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("DeleteFoodError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun DeleteMovement(movementId: Movement): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for deleting the user with the userId
                val url = URL(apiUrl + epMovement+ "?movementId=${movementId}")
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to DELETE
                connection.requestMethod = "DELETE"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's success message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("DeleteMovementError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }

    fun DeleteWorkout(workoutId: Workout): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for deleting the user with the userId
                val url = URL(apiUrl + epWorkout+ "?workoutId=${workoutId}")
                val connection = url.openConnection() as HttpURLConnection

                // Set the request method to DELETE
                connection.requestMethod = "DELETE"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        responseMessage = reader.readText() // Get the server's success message
                    }
                } else {
                    // Read error message if the request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = reader.readText()
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("DeleteWorkoutError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return responseMessage // Return the server's response (success or error message)
    }
    //Delete//



    // LocalDate Serializer
    class LocalDateSerializer : JsonSerializer<LocalDate> {
        override fun serialize(
            src: LocalDate,
            typeOfSrc: Type?,
            context: JsonSerializationContext?
        ): JsonElement {
            return JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE))  // "yyyy-MM-dd"
        }
    }

    // LocalDate Deserializer
    class LocalDateDeserializer : JsonDeserializer<LocalDate> {
        override fun deserialize(
            json: JsonElement,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): LocalDate {
            return LocalDate.parse(json.asString, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    }

}