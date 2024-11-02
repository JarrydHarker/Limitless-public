package com.example.limitless.data.AI

import android.util.Log
import com.google.android.gms.common.api.Api
import com.google.gson.Gson
import java.io.OutputStreamWriter
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class AIDecoder {
    fun makePostRequest(request: String, onStringProcessed: (ApiResponse) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            val responses = mutableListOf<ApiResponse>()
            var responseMessage = ApiResponse(
            model = null,
            createdAt = null,
            response = "Unknown error occurred", // Default message
            done = false,
            totalDuration = null,
            loadDuration = null,
            promptEvalCount = null,
            promptEvalDuration = null,
            evalCount = null,
            evalDuration = null
            )

            try {
                val url = URL("http://limitlessai.duckdns.org:11434/api/generate") // Your API URL
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Create the request body as a map and serialize it to JSON using Gson
                val gson = Gson()
                val requestBody = mapOf(
                    "model" to "llama3.1",
                    "prompt" to request,
                    "stream" to true
                )
                val jsonInputString = gson.toJson(requestBody)

                // Write the JSON data to the output stream
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(jsonInputString)
                    writer.flush()
                }

                // Read the response message from the input stream
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStreamReader(connection.inputStream).use { reader ->
                        val jsonString = reader.readText()
                        Log.d("AI", jsonString) // Log the entire response

                        // Split by newlines to handle each JSON object separately
                        val jsonObjects = jsonString.split("\n")


                        jsonObjects.forEach { json ->
                            if (json.isNotBlank()) {
                                val apiResponse = gson.fromJson(json, ApiResponse::class.java)
                                if (apiResponse != null) {
                                    responses.add(apiResponse)
                                } else {
                                    Log.e("AI", "Failed to parse JSON: $json")
                                }
                            }
                        }
                    }
                } else {
                    // Read error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        responseMessage = gson.fromJson(reader.readText(), ApiResponse::class.java)
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("NetworkError", ex.toString())
                responseMessage.response = "Error: ${ex.message}"
            }

            if(responses.isNotEmpty()){
                responseMessage.response = ""
                for(response in responses){
                    responseMessage.response += response.response
                }
                Log.d("AI", responseMessage.response.toString())
            }

            // Execute the callback with the response message
            onStringProcessed(responseMessage)
        }
    }
}

data class ApiResponse(
    val model: String?,
    val createdAt: String?,
    var response: String?,
    val done: Boolean,
    val totalDuration: Int?,
    val loadDuration: Int?,
    val promptEvalCount: Int?,
    val promptEvalDuration: Int?,
    val evalCount: Int?,
    val evalDuration: Int?
)