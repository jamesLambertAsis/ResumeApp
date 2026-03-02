package com.example.jla.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.Exclude
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class WeatherDetails (
    val timeZone: String = "",
    val temperature: Float = 0f,
    val weatherCode: Int = 0,
    val windSpeed: Float = 0f,
    val time: String = "",
    val timeToDisplay: String = ""
) {


    @RequiresApi(Build.VERSION_CODES.O)
    fun formatOpenMeteoTime(
        time: String,
        apiTimezone: String
    ): String {
        val localDateTime = LocalDateTime.parse(time)
        val zoned = localDateTime.atZone(ZoneId.of(apiTimezone))
        val deviceTime = zoned.withZoneSameInstant(ZoneId.systemDefault())

        return deviceTime.format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a")
        )
    }

}