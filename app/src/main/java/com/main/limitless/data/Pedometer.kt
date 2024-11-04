package com.main.limitless.data

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.main.limitless.activityViewModel

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
                    Log.d("Steps", "Pedo: $stepsSinceReboot")

                    // Retrieve the stored steps reset at midnight from SharedPreferences
                    val sharedPreferences = context.getSharedPreferences("StepCounterPrefs", Context.MODE_PRIVATE)
                    storedSteps = sharedPreferences.getLong("storedSteps", 0)

                    Log.d("Steps", "Retrieved stored steps: $storedSteps")

                    // Calculate daily steps by subtracting storedSteps from stepsSinceReboot
                    currentStepCount = stepsSinceReboot - storedSteps

                    if (storedSteps == 0L) {
                        storeSteps(stepsSinceReboot)  // Save the steps since reboot, not storedSteps which is 0
                        Log.d("Steps", "Storing steps since reboot: $stepsSinceReboot")
                    }

                    Log.d("Steps", "Stored: $storedSteps")
                    Log.d("Steps", "Current: $currentStepCount")

                    activityViewModel.steps = currentStepCount.toInt()

                    val editor = sharedPreferences.edit()
                    editor.putLong("currentStepCount", currentStepCount)
                    editor.apply()

                    Log.d("Steps", "Updated current step count: $currentStepCount")

                    onStepCountUpdated(currentStepCount)
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensor?.let {
            sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    private fun storeSteps(steps: Long) {
        val sharedPreferences = context.getSharedPreferences("StepCounterPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("storedSteps", steps)
        editor.apply() // Save the changes
        Log.d("Steps", "Steps stored: $steps")
    }
}
