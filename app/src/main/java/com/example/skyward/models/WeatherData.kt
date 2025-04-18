package com.example.skyward.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeather(
    val main: Main,
    val weather: List<Weather>,
    val name: String
) {

    @Serializable
    data class Main(
        @SerialName("temp")
        val temp: Double = 0.0,
        @SerialName("feels_like")
        val feelsLike: Double = 0.0,
        @SerialName("temp_min")
        val tempMin: Double = 0.0,
        @SerialName("temp_max")
        val tempMax: Double = 0.0,
        @SerialName("pressure")
        val pressure: Double = 0.0,
        @SerialName("humidity")
        val humidity: Double = 0.0,
        @SerialName("sea_level")
        val seaLevel: Double = 0.0,
        @SerialName("grnd_level")
        val grndLevel: Double = 0.0
    )

    @Serializable
    data class Weather(
        @SerialName("id")
        val id: Int,
        @SerialName("main")
        val main: String,
        @SerialName("description")
        val description: String,
        @SerialName("icon")
        val icon: String
    )
}