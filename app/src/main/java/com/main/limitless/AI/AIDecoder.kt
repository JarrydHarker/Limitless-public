package com.main.limitless.AI

import android.util.Log
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class AIDecoder {
    fun makePostRequest(request: String, onStringProcessed: (ApiResponse) -> Unit) {
        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            try {
                val url = URL("http://limitlessai.duckdns.org:11434/api/generate")
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json; utf-8")
                connection.doOutput = true

                // Create the request body as a map and serialize it to JSON using Gson
                val gson = Gson()
                val requestBody = mapOf(
                    "model" to "llama3.1",
                    "prompt" to "$request (Keep response concise and to the point)",
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
                        val bufferedReader = BufferedReader(reader)
                        bufferedReader.forEachLine { line ->
                            if (line.isNotBlank()) {
                                try {
                                    val apiResponse = gson.fromJson(line, ApiResponse::class.java)
                                    onStringProcessed(apiResponse)
                                } catch (e: Exception) {
                                    Log.e("AI", "Failed to parse JSON: $line")
                                }
                            }
                        }
                    }
                } else {
                    // Read error message if request fails
                    InputStreamReader(connection.errorStream).use { reader ->
                        val responseMessage = gson.fromJson(reader.readText(), ApiResponse::class.java)
                        onStringProcessed(responseMessage)
                    }
                }

            } catch (ex: Exception) {
                // Handle exceptions appropriately
                Log.e("NetworkError", ex.toString())
                val responseMessage = ApiResponse(
                    model = null,
                    createdAt = null,
                    response = "Error: ${ex.message}",
                    done = false,
                    totalDuration = null,
                    loadDuration = null,
                    promptEvalCount = null,
                    promptEvalDuration = null,
                    evalCount = null,
                    evalDuration = null
                )
                onStringProcessed(responseMessage)
            }
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