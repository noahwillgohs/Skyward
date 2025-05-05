package com.example.skyward.viewmodels

import com.example.skyward.repositories.WeatherRepository
import com.example.skyward.services.MockService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class WeatherViewModelTest {
    private lateinit var dispatcher: TestDispatcher
    private lateinit var viewModel: WeatherViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        dispatcher = UnconfinedTestDispatcher()
        val service = WeatherRepository(
            weatherService = MockService()
        )
        viewModel = WeatherViewModel(service, dispatcher, "")
    }

    @Test
    fun `Fetching weather should update values with weather data`() = runTest(dispatcher) {
        viewModel.fetchCurrentWeather("")
        val temp = viewModel.temp.value
        assertEquals(50.0, temp)
    }
}