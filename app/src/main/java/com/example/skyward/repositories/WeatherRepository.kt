package com.example.skyward.repositories

import com.example.skyward.models.CurrentWeather
import com.example.skyward.models.ForecastData
import com.example.skyward.services.WeatherService

class WeatherRepository(private val weatherService: WeatherService) {
    suspend fun getWeather(zip: String, apiKey: String): CurrentWeather {
        return weatherService.getCurrentWeather(zip, apiKey)
    }

    suspend fun getForecast(zip: String, apiKey: String): ForecastData {
        print("IN REPOSITORY")
        return weatherService.getForecastWeather(zip, apiKey)
    }
}