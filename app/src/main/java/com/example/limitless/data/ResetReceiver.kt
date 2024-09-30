package com.example.limitless.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.limitless.currentUser
import java.time.LocalDate

class ResetReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // Reset the steps stored for the day
        val sharedPreferences = context.getSharedPreferences("StepCounterPrefs", Context.MODE_PRIVATE)

        val currentSteps = sharedPreferences.getLong("currentStepCount", 0)
        val storedSteps = sharedPreferences.getLong("storedSteps", 0)

        val editor = sharedPreferences.edit()
        editor.putLong("storedSteps", storedSteps + currentSteps)
        editor.apply()



        // Optionally, you can notify the main activity to update the UI if needed
        Log.d("StepResetReceiver", "Steps reset at midnight")
    }
}
