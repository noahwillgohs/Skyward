package com.example.skyward

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
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

        weatherViewModel.fetchCurrentWeather("55024")
        enableEdgeToEdge()
        setContent {
            var showForecast by remember { mutableStateOf(false) }
            var zipCode by remember { mutableStateOf("55024") }

            SkywardTheme {
                if(showForecast) {
                    Scaffold(modifier = Modifier.fillMaxSize()
                        .background(colorResource(R.color.purple_200))) { innerPadding ->
                        ForecastScreen(
                            viewModel = weatherViewModel,
                            onBackClick = { showForecast = false })
                    }
                }
                else
                    Scaffold (modifier = Modifier.fillMaxSize()
                        .background(colorResource(R.color.purple_500))) { innerPadding ->
                        MainScreen(
                            viewModel = weatherViewModel,
                            onForecastClick = {
                                weatherViewModel.fetchForecastWeather(zipCode)
                                showForecast = true
                            },
                            onZipSearch = { newZipCode ->
                                zipCode = newZipCode
                                weatherViewModel.fetchCurrentWeather(zipCode)
                                weatherViewModel.fetchForecastWeather(zipCode)
                            }
                        )
                }
            }
        }
    }
}