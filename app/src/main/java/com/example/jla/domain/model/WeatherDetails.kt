package com.example.jla.domain.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
data class WeatherDetails (
    val timeZone: String = "",
    val temperature: Float = 0f,
    val weatherCode: Int = 0,
    val windSpeed: Float = 0f,
    val time: String = "",
    val formattedTime: String = formatOpenMeteoTime(time = time, timeZone),
    val timeToDisplay: String = "",
    val weatherCodeDescription: String = getWeatherCodeDescription(weatherCode)
) {

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun formatOpenMeteoTime(
            time: String,
            apiTimezone: String
        ): String {
            if (time.isEmpty()) {
                return ""
            }
            val localDateTime = LocalDateTime.parse(time)
            val zoned = localDateTime.atZone(ZoneId.of(apiTimezone))
            val deviceTime = zoned.withZoneSameInstant(ZoneId.systemDefault())

            return deviceTime.format(
                DateTimeFormatter.ofPattern("MMMM dd, YYYY hh:mm a")
            )
        }

        fun getWeatherCodeDescription(weatherCode: Int): String {
            return when(weatherCode) {
                0 -> "Clear Sky"
                1, 2, 3 -> "Mainly Clear, Partly Cloudy, Overcast"
                45, 48 -> "Fog and Dense Fog"
                51, 53, 55 -> "Drizzle: Light, Moderate, and Dense Intensity"
                56, 57 -> "Freezing Drizzle: Light and Dense Intensity"
                61, 63, 65 -> "Rain: Slight, Moderate and Heavy Intensity"
                66, 67 -> "Freezing Rain: Light and Heavy Intensity"
                71, 73, 75 -> "Snowfall: Slight, Moderate, and Heavy Intensity"
                77 -> "Snow Grains"
                80, 81, 82 -> "Rain Showers: Slight, Moderate, and Heavy"
                85, 86 -> "Snow Showers"
                95 -> "Thunderstorm: Slight or Moderate"
                96, 99 -> "Thunderstorm with Slight or Heavy Hail"
                else -> "Unknown"
            }
        }
    }


}