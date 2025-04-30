package com.example.skyward

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: WeatherViewModel,
               onForecastClick: () -> Unit,
               onZipSearch: (String) -> Unit
){
    val temp by viewModel.temp.observeAsState()
    val cityName by viewModel.cityName.observeAsState()
    val tempMin by viewModel.tempMin.observeAsState()
    val tempMax by viewModel.tempMax.observeAsState()
    val humidity by viewModel.humidity.observeAsState()
    val pressure by viewModel.pressure.observeAsState()
    val feelsLike by viewModel.feelsLike.observeAsState()
    val icon by viewModel.weatherIcon.observeAsState()
    val fetchWeatherError by viewModel.fetchWeatherError.observeAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
            .border(2.dp, Color.Black, shape = RoundedCornerShape(12.dp))
            .background(Brush.verticalGradient(
                colors = listOf(colorResource(R.color.purple_200), colorResource(R.color.teal_200))),
                RoundedCornerShape(12.dp)
            )
    ){
        Banner("Skyward")
        Location(cityName)
        CurrentTemp(temp?.roundToInt().toString() + "°",
            stringResource(R.string.ambient_temp) + " " + feelsLike?.roundToInt() + "°",
            painterResource(getWeatherIcon(icon)))
        OtherWeatherInformation(
            stringResource(R.string.low) + " " + tempMin?.roundToInt() + "°",
            stringResource(R.string.high) + " " + tempMax?.roundToInt() + "°",
            stringResource(R.string.humidity) + " " + humidity?.roundToInt() + "%",
            stringResource(R.string.pressure1) + " " + pressure?.roundToInt() + " " + stringResource(R.string.pressure2))
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = { onForecastClick() },
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Text(text = stringResource(R.string.view_forecast))
            }
        }
        ZipCodeChange(viewModel.zipCode.value,
            { viewModel.updateZipCode(it) },
            onSubmit = {
                if (viewModel.zipCode.value.length == 5) {
                    onZipSearch(viewModel.zipCode.value)
                }
            }
        )
        if(!fetchWeatherError.equals(null)){
            AlertDialog(
                onDismissRequest = { viewModel.clearFetchWeatherError() },
                confirmButton = {
                    TextButton(onClick = {viewModel.clearFetchWeatherError() }) {
                        Text("Ok")
                    }
                }


            )
        }
    }
}

@Composable
fun Banner(appName: String) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.purple_200),
                shape = RoundedCornerShape(12.dp))
            .padding(vertical = 10.dp)
    ) {
        Text(text = appName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            textAlign = TextAlign.Left,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic
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
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
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
            .fillMaxWidth()
            .safeContentPadding()
            .padding(all = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
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

@Composable
fun ZipCodeChange(
    zipCode: String,
    onZipChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        TextField(
            value = zipCode,
            onValueChange = {
                if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                    onZipChange(it)
                }
            },
            placeholder = { Text(stringResource(R.string.zipcode_field_text)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (zipCode.length == 5) {
                        keyboardController?.hide()
                        onSubmit()
                    }
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )
         Button(
             onClick = {
                 if (zipCode.length == 5) {
                     keyboardController?.hide()
                     onSubmit()
                 }
             },
             enabled = zipCode.length == 5
         ) {
             Text(stringResource(R.string.zipcode_button))
         }
    }
}

fun getWeatherIcon(code: String?): Int {
    return when (code) {
        "01d" -> R.drawable.sun
        "01n" -> R.drawable.moon
        "02d" -> R.drawable.fewcloudsday
        "02n" -> R.drawable.fewcloudsnight
        "03d", "04d", "03n", "04n" -> R.drawable.cloudy
        "09d", "10d", "09n", "10n" -> R.drawable.rain
        "11d", "11n" -> R.drawable.thunder
        "13d", "13n" -> R.drawable.snow
        "50d", "50n" -> R.drawable.fog

        else -> R.drawable.sun
    }
}