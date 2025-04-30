package com.example.skyward.services

import com.example.skyward.models.CurrentWeather
import com.example.skyward.models.ForecastData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather?")
    suspend fun getCurrentWeather(
        @Query("zip") zip: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "imperial"
    ): CurrentWeather

    @GET("forecast/daily?")
    suspend fun getForecastWeather(
        @Query("zip") zip: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "imperial",
        @Query("cnt") count: Int = 16
    ): ForecastData
}

