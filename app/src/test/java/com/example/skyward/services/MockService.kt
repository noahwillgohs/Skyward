package com.example.skyward.services

import com.example.skyward.models.CurrentWeather
import com.example.skyward.models.ForecastData

class MockService: WeatherService {
    override suspend fun getCurrentWeather(
        zip: String,
        apiKey: String,
        units: String
    ): CurrentWeather {
        val main = CurrentWeather.Main(
            temp = 50.0,
            feelsLike = 51.0,
            tempMin = 45.0,
            tempMax = 55.0,
            pressure = 1000.0,
            humidity = 50.0,
            seaLevel = 900.0,
            grndLevel = 800.0
        )

        val weather = listOf(CurrentWeather.Weather(
            id = 1,
            main = "sunny weather",
            description = "this is test weather",
            icon = "01d"
        ))
        return CurrentWeather(
            main = main,
            weather = weather,
            name = "Farmington"
        )
    }

    override suspend fun getForecastWeather(
        zip: String,
        apiKey: String,
        units: String,
        count: Int
    ): ForecastData {
        TODO("Not yet implemented")
    }
}