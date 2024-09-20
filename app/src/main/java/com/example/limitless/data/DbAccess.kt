package com.example.limitless.data

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
    //Read//

    //Update//

    //Update//

    //Delete//

    //Delete//
}