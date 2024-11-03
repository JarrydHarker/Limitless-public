package com.main.limitless

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

object ThemeManager {
    private const val PREFS_NAME = "MyAppPrefs"
    private const val KEY_DARK_MODE = "dark_mode"

    // Check if dark mode is enabled
    fun isDarkModeEnabled(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_DARK_MODE, false)
    }

    // Save the dark mode state
    fun saveDarkModeState(context: Context, isDarkMode: Boolean) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(KEY_DARK_MODE, isDarkMode).apply()
    }

    // Apply the appropriate theme (dark or light)
    fun applyTheme(context: Context) {
        val isDarkMode = isDarkModeEnabled(context)
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    // Update the navigation bar color based on the theme
    fun updateNavBarColor(activity: AppCompatActivity, bottomNavBar: BottomNavigationView) {
        val isDarkMode = isDarkModeEnabled(activity)
        val backgroundColor = if (isDarkMode) {
            ContextCompat.getColor(activity, R.color.darkBackground)
        } else {
            ContextCompat.getColor(activity, R.color.lightBackground)
        }
        bottomNavBar.setBackgroundColor(backgroundColor)
    }
}