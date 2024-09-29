package com.example.limitless

import java.util.Locale

class Timer(val endTime: Long) {
    var time: Long = 0
    var startTime: Long = 0

    var isTiming = false

    fun start(){
        if (!isTiming) {
            startTime = System.currentTimeMillis()
            isTiming = true
        }
    }

    fun pause() {
        if (isTiming) {
            isTiming = false
        }
    }

    fun resume() {
        if (!isTiming) {
            startTime = System.currentTimeMillis() - time // Adjust start time by subtracting elapsed time
            isTiming = true
        }
    }

    fun reset(newTime: Long) {
        time = newTime
        isTiming = false
    }

    private fun update2() {
        if (isTiming ) {
            if(time <= endTime){
                time = System.currentTimeMillis() - startTime
            }else isTiming = false
        }
    }

    private fun update() {
        if (isTiming) {
            val elapsed = System.currentTimeMillis() - startTime
            time -= elapsed
            startTime = System.currentTimeMillis()
            if (time <= 0) {
                time = 0
                isTiming = false
            }
        }
    }

    fun get(): Float{
        return time.toFloat()
    }

    fun getTime(): String{
        update()
        return formatTime(time)
    }

    fun getRemainingTime(): Long {
        update()
        return time
    }

    fun getTime2(): String{
        update2()
        return formatTime(time)
    }

    private fun formatTime(time: Long): String {
        var seconds = (time / 1000).toInt()
        var minutes = seconds / 60
        val hours = minutes / 60

        seconds = seconds.mod(60)
        minutes = minutes.mod(60)

        return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds)
    }
}