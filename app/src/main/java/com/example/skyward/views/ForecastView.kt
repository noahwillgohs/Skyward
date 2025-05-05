package com.example.skyward.views

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skyward.R
import com.example.skyward.models.ForecastData
import com.example.skyward.viewmodels.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun ForecastScreen(viewModel: WeatherViewModel, onBackClick: () -> Unit) {

    val forecastData = viewModel.forecastList.observeAsState()
    val fCityName = viewModel.fCityName.observeAsState()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
            .background(
                Brush.verticalGradient(
                colors = listOf(colorResource(R.color.purple_200), colorResource(R.color.teal_200))),
                RoundedCornerShape(12.dp)
            )
    ){
        Button(
        onClick = { onBackClick() },
        modifier = Modifier
            .padding(bottom = 12.dp),
    ) {
        Text(stringResource(R.string.back))
    }
        forecastData.value?.let { forecast ->
            Column ( modifier = Modifier
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = fCityName.value.toString(),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(forecast) { forecastData ->
                        ForecastItem(forecastData)
                    }
                }
            }
        } ?: Text(text = stringResource(R.string.loading))

    }
}

@Composable
fun ForecastItem(forecastData: ForecastData.Forecast) {
    val date = SimpleDateFormat("EEE, MMM d",
        Locale.getDefault()).format(Date(forecastData.date * 1000))
    val icon = forecastData.fWeather[0].icon

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(12.dp))

            .safeContentPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .padding(8.dp)) {
            Text(
                text = date,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.high) + " " + forecastData.temp.max.roundToInt() + "°",
                fontSize = 18.sp
            )
            Text(
                text = stringResource(R.string.low) + " " + forecastData.temp.min.roundToInt() + "°",
                fontSize = 18.sp
            )
        }

        Image(
            painter = painterResource(getWeatherIcon(icon)),
            contentDescription = null,
            modifier = Modifier
                .size(82.dp)
                .padding(12.dp),
            contentScale = ContentScale.Fit
        )
    }
}