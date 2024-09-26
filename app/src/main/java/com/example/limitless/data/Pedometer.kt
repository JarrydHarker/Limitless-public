package com.example.limitless.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import java.util.Calendar

private const val TAG = "STEP_COUNT_LISTENER"

class Pedometer(private val context: Context) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    // Store steps with timestamp
    private var currentStepCount: Long = 0 // To track steps since last store
    private var storedSteps: Long = 0 // To store steps for the day
    private var isListening = false
    private var listener: SensorEventListener? = null

    fun startListening(onStepCountUpdated: (Long) -> Unit) {
        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event != null) {
                    val stepsSinceReboot = event.values[0].toLong()

                    // Retrieve the stored steps reset at midnight from SharedPreferences
                    val sharedPreferences = context.getSharedPreferences("StepCounterPrefs", Context.MODE_PRIVATE)
                    storedSteps = sharedPreferences.getLong("storedSteps", stepsSinceReboot)

                    // Calculate daily steps by subtracting storedSteps from stepsSinceReboot
                    currentStepCount = stepsSinceReboot - storedSteps

                    val editor = sharedPreferences.edit()
                    editor.putLong("currentStepCount", currentStepCount)
                    editor.apply()

                    // Update the UI or app logic with the current step count
                    onStepCountUpdated(currentStepCount)
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensor?.let {
            sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    // Stop listening and unregister the listener
    fun stopListening() {
        if (!isListening) return

        sensorManager.unregisterListener(listener)
        listener = null
        isListening = false
        Log.d(TAG, "Sensor listener unregistered")
    }

    private fun storeSteps(steps: Long) {
        val sharedPreferences = context.getSharedPreferences("StepCounterPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("steps_today", steps)
        editor.apply() // Save the changes
    }
}
