package com.example.limitless.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class StepResetReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // Reset the steps stored for the day
        val sharedPreferences = context.getSharedPreferences("StepCounterPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("storedSteps", 0) // Reset step count to zero
        editor.apply()

        // Optionally, you can notify the main activity to update the UI if needed
        Log.d("StepResetReceiver", "Steps reset at midnight")
    }
}
