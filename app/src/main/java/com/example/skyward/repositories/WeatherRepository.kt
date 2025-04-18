package com.example.skyward.repositories

import com.example.skyward.models.CurrentWeather
import com.example.skyward.services.WeatherService

class WeatherRepository(private val weatherService: WeatherService) {
    suspend fun getWeather(lat: String, lon: String, apiKey: String): CurrentWeather {
        return weatherService.getCurrentWeather(lat, lon, apiKey)
    }
}