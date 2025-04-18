package com.example.skyward

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlin.math.roundToInt

@Composable
fun MainScreen(viewModel: WeatherViewModel) {

    val context = LocalContext.current
    val temp by viewModel.temp.observeAsState()
    val cityName by viewModel.cityName.observeAsState()
    val tempMin by viewModel.tempMin.observeAsState()
    val tempMax by viewModel.tempMax.observeAsState()
    val humidity by viewModel.humidity.observeAsState()
    val pressure by viewModel.pressure.observeAsState()
    val feelsLike by viewModel.feelsLike.observeAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
    ){
        Banner("Skyward")
        Location(cityName)
        CurrentTemp(temp?.roundToInt().toString() + "°",
            context.getString(R.string.ambient_temp) + " " + feelsLike?.roundToInt().toString() + "°",
            painterResource(R.drawable.sun))
        OtherWeatherInformation(context.getString(R.string.low) + " " + tempMin?.roundToInt().toString() + "°",
            context.getString(R.string.high) + " " + tempMax?.roundToInt().toString() + "°",
            context.getString(R.string.humidity) + " " + humidity?.roundToInt().toString() + "%",
            context.getString(R.string.pressure1) + " " + pressure?.roundToInt().toString() + " " + context.getString(R.string.pressure2))
    }
}

@Composable
fun Banner(appName: String) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.LightGray)
            .padding(vertical = 10.dp)
    ) {
        Text(text = appName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            textAlign = TextAlign.Left,
            fontSize = 20.sp
        )
    }
}

@Composable
fun Location(location: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .safeContentPadding()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        if (location != null) {
            Text(
                text = location,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun CurrentTemp(currentTemp: String?, ambientTemp: String?, weatherImage: Painter) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            if (currentTemp != null) {
                Text(
                    text = currentTemp,
                    fontSize = 72.sp
                )
            }
            if (ambientTemp != null) {
                Text(
                    text = ambientTemp,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
        Image(painter = weatherImage,
            contentDescription = null,
            modifier = Modifier
                .size(72.dp),
        )
    }
}

@Composable
fun OtherWeatherInformation(low: String?, high: String?, humidity: String?, pressure: String?) {
    Column (
        modifier = Modifier
            .safeContentPadding()
            .padding(all = 24.dp)
    ) {
        if (low != null) {
            Text(text = low)
        }
        if (high != null) {
            Text(text = high)
        }
        if (humidity != null) {
            Text(text = humidity)
        }
        if (pressure != null) {
            Text(text = pressure)
        }
    }
}