package com.example.skyward

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.skyward.repositories.WeatherRepository
import com.example.skyward.services.RetrofitInstance
import com.example.skyward.ui.theme.SkywardTheme


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherRepository = WeatherRepository(RetrofitInstance.weatherService)
        val weatherViewModel = WeatherViewModel(
            weatherRepository,
            apiKey = resources.getString(R.string.weather_api_key)
        )
        weatherViewModel.fetchCurrentWeather("44.95", "-93.09")
        enableEdgeToEdge()
        setContent {
            SkywardTheme {
                Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
                        MainScreen(viewModel = weatherViewModel)

                }
            }
        }
    }
}