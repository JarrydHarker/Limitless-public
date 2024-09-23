package com.example.limitless.data

import android.health.connect.datatypes.MealType
import android.util.Log
import com.google.gson.Gson
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.concurrent.Executors


class DbAccess {
    private val apiUrl = "https://localhost:7230/api/Limitless"
    private val epUser = "/User"
    private val epDay = "/Day"
    private val epExercise = "/Exercise"
    private val epCardio = "/Cardio"
    private val epStrength = "/Strength"
    private val epMeal = "/Meal"
    private val epFood = "/Food"
    private val epMovement = "/Movement"
    private val epWorkout = "/Workout"

    //Create//
    fun CreateUser(user: User): String {
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

        return responseMessage
    }

    fun CreateDay(day: Day): String {
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
                val gson = Gson()
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
        }

        return responseMessage
    }

    fun CreateExercise(exercise: Exercise): String {
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
        }

        return responseMessage
    }

    fun CreateCardio(cardio: CardioExercise): String {
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

    fun CreateStrength(strength: StrengthExercise): String {
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
        }

        return responseMessage
    }

    fun CreateMeal(meal: Meal): String {
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
        }

        return responseMessage
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

    fun CreateWorkout(workout: Workout): String {
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


    fun GetDay(date: LocalDate, userId: String): Day?{
        val executor = Executors.newSingleThreadExecutor()

        var day: Day? = null

        executor.execute {
            try {
                // Construct URL with query parameter
                val url = URL( apiUrl + epDay + "?year=${date.year}&month=${date.month}&day=${date.dayOfMonth}&dayOfWeek=${date.dayOfWeek}&userId=${userId}")
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
        }

        return day // Return the deserialized User object (if any)
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

    fun GetCardio(cardioId: String): CardioExercise?{
        val executor = Executors.newSingleThreadExecutor()

        var cardio: CardioExercise? = null

        executor.execute {
            try {
                // Construct URL with query parameter
                val url = URL( apiUrl + epCardio + "?cardioId=$cardioId")
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
                        cardio = gson.fromJson(jsonResponse, CardioExercise::class.java)
                    }
                } else {
                    // Handle error if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        val errorMessage = reader.readText()
                        Log.e("GetCardioError", "Error: $errorMessage")
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetCardioError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return cardio // Return the deserialized User object (if any)
    }

    fun GetStrength(strengthId: StrengthExercise): StrengthExercise?{
        val executor = Executors.newSingleThreadExecutor()

        var strength: StrengthExercise? = null

        executor.execute {
            try {
                // Construct URL with query parameter
                val url = URL( apiUrl + epStrength + "?strengthId=$strengthId")
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
                        strength = gson.fromJson(jsonResponse, StrengthExercise::class.java)
                    }
                } else {
                    // Handle error if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        val errorMessage = reader.readText()
                        Log.e("GetStrengthError", "Error: $errorMessage")
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetStrengthError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return strength // Return the deserialized User object (if any)
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

    fun GetMovement(movementId: Movement): Movement?{
        val executor = Executors.newSingleThreadExecutor()

        var movement: Movement? = null

        executor.execute {
            try {
                // Construct URL with query parameter
                val url = URL( apiUrl + epMovement + "?movementId=$movementId")
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
                        movement = gson.fromJson(jsonResponse, Movement::class.java)
                    }
                } else {
                    // Handle error if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        val errorMessage = reader.readText()
                        Log.e("GetMovementError", "Error: $errorMessage")
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("GetMovementError", ex.toString())
                ex.printStackTrace() // For debugging purposes
            }
        }

        return movement // Return the deserialized User object (if any)
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

    //Update//

    //Delete//
    fun DeleteUser(userId: String): String {
        val executor = Executors.newSingleThreadExecutor()
        var responseMessage = ""

        executor.execute {
            try {
                // Construct URL for deleting the user with the userId
                val url = URL(apiUrl + epUser)
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

    //Delete//
}