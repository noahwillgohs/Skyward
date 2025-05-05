package com.example.skyward.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skyward.models.ForecastData
import com.example.skyward.repositories.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel (
    private val weatherRepository: WeatherRepository,
    private val dispatcher: CoroutineDispatcher,
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

    private val _weatherIcon = MutableLiveData<String>()
    val weatherIcon: LiveData<String> = _weatherIcon

    private val _zipCode = mutableStateOf("")
    val zipCode: State<String> = _zipCode

    private val _fetchWeatherError = MutableLiveData<String?>()
    val fetchWeatherError: LiveData<String?> = _fetchWeatherError

    private  val _fCityName = MutableLiveData<String>()
    val fCityName: LiveData<String> = _fCityName

    private val _forecastList = MutableLiveData<List<ForecastData.Forecast>>()
    val forecastList: LiveData<List<ForecastData.Forecast>> = _forecastList

    fun fetchCurrentWeather(zip: String) = viewModelScope.launch(dispatcher) {

        _fetchWeatherError.postValue(null)
        try {
            val response = weatherRepository.getWeather(zip, apiKey)
            Log.e("WeatherViewModel", "API Response: $response") // Log API response
            _temp.postValue(response.main.temp)
            _feelsLike.postValue(response.main.feelsLike)
            _tempMin.postValue(response.main.tempMin)
            _tempMax.postValue(response.main.tempMax)
            _humidity.postValue(response.main.humidity)
            _pressure.postValue(response.main.pressure)
            _cityName.postValue(response.name)
            _weatherIcon.postValue(response.weather.firstOrNull()?.icon ?: "default")

        } catch (e: Exception) {
            _fetchWeatherError.postValue("Error fetching forecast: " + e.message)
        }
    }

    fun fetchForecastWeather(zip: String) = viewModelScope.launch(dispatcher) {
        try {
            val response = weatherRepository.getForecast(zip, apiKey)
            Log.e("WeatherViewModel", "API Response: $response") // Log API response
            _fCityName.postValue(response.city.cityName)
            _forecastList.postValue(response.list)

        } catch (e: Exception) {
            Log.e("WeatherViewModel", "API Response: ${e.message}")
            _fetchWeatherError.postValue("Error fetching forecast: " + e.message)
        }
    }

    fun updateZipCode(newZipCode: String) {
        _zipCode.value = newZipCode
    }

    fun clearFetchWeatherError() {
        _fetchWeatherError.value = null
    }
}