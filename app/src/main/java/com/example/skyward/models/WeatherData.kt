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
        val temp: Double,
        @SerialName("feels_like")
        val feelsLike: Double,
        @SerialName("temp_min")
        val tempMin: Double,
        @SerialName("temp_max")
        val tempMax: Double,
        @SerialName("pressure")
        val pressure: Double,
        @SerialName("humidity")
        val humidity: Double,
        @SerialName("sea_level")
        val seaLevel: Double,
        @SerialName("grnd_level")
        val grndLevel: Double
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

@Serializable
data class ForecastData (
    @SerialName("city")
    val city: City,
    @SerialName("list")
    val list: List<Forecast>
) {

    @Serializable
    data class City (
        @SerialName("name")
        val cityName: String
    )

    @Serializable
    data class Forecast (
        @SerialName("dt")
        val date: Long,
        @SerialName("temp")
        val temp: ForecastTemp,
        @SerialName("feels_like")
        val fFeelsLike: ForecastFeelsLike,
        @SerialName("pressure")
        val fPressure: Double,
        @SerialName("humidity")
        val fHumidity: Double,
        @SerialName("weather")
        val fWeather: List<CurrentWeather.Weather>
    ) {
        @Serializable
        data class ForecastTemp (
            @SerialName("day")
            val day: Double,
            @SerialName("min")
            val min: Double,
            @SerialName("max")
            val max: Double
        )

        @Serializable
        data class  ForecastFeelsLike (
            @SerialName("day")
            val day: Double
        )
    }
}

