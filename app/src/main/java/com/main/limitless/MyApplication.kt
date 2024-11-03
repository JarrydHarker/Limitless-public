package com.main.limitless

import android.app.Activity
import android.app.Application
import android.os.Bundle

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Register ActivityLifecycleCallbacks to monitor activity lifecycle changes
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                // Apply theme when an activity is created
                ThemeManager.applyTheme(activity)
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }
}