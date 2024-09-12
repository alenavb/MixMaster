package com.example.gigachatappmain

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object Utils {
    @SuppressLint("NewApi")
    fun needToken(): Boolean {
        val context = MyApp.appContext
        val sharedpref =
            context.getSharedPreferences(
                "user details",
                AppCompatActivity.MODE_PRIVATE
            )

        val timeToStartToken = sharedpref?.getString("timeToStartToken", null)
        if (timeToStartToken == null)
            return true

        val today = LocalDateTime.now()
        val startToken = LocalDateTime.parse(timeToStartToken)

        val duration = ChronoUnit.MINUTES.between(startToken, today)
        if (duration < 30)
            return false
        else
            return true
    }
}