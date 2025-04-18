package com.example.skyward

import android.util.Log
import android.view.View
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.skyward.models.CurrentWeather
import com.example.skyward.repositories.WeatherRepository
import com.example.skyward.services.WeatherService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.Query
import java.io.IOException

class WeatherViewModel (
    private val weatherRepository: WeatherRepository,
    private val apiKey: String
) : ViewModel() {

    private val _temp = MutableLiveData<Double>()
    val temp: LiveData<Double> = _temp

    private val _feelsLike = MutableLiveData<Double>()
    val feelsLike: LiveData<Double> = _feelsLike

    private val _tempMin = MutableLiveData<Double>()
    val tempMin: LiveData<Double> = _tempMin

    private val _tempMax = MutableLiveData<Double>()
    val tempMax: LiveData<Double> = _tempMax

    private val _humidity = MutableLiveData<Double>()
    val humidity: LiveData<Double> = _humidity

    private val _pressure = MutableLiveData<Double>()
    val pressure: LiveData<Double> = _pressure

    private val _cityName = MutableLiveData<String>()
    val cityName: LiveData<String> = _cityName


    fun fetchCurrentWeather(lat: String, lon: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = weatherRepository.getWeather(lat, lon, apiKey)
            Log.e("WeatherViewModel", "API Response: $response") // Log the response
            _temp.postValue(response.main.temp)
            _feelsLike.postValue(response.main.feelsLike)
            _tempMin.postValue(response.main.tempMin)
            _tempMax.postValue(response.main.tempMax)
            _humidity.postValue(response.main.humidity)
            _pressure.postValue(response.main.pressure)
            _cityName.postValue(response.name)

        } catch (e: Exception) {
            Log.e("WeatherViewModel", "Error fetching weather data: ${e.message}")
        }
    }
}