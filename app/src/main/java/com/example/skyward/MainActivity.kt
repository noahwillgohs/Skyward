package com.example.skyward

import android.content.Context
import android.health.connect.datatypes.units.Temperature
import android.media.Image
import androidx.compose.foundation.Image
import android.os.Bundle
import android.os.Message
import android.provider.Settings
import android.provider.Settings.Global
import android.provider.Settings.Global.getString
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skyward.ui.theme.SkywardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkywardTheme {
                Scaffold (modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {

    val context = LocalContext.current
    val appName = context.getString(R.string.app_name)
    var location = context.getString(R.string.location)
    var currentTemp = context.getString(R.string.current_temp)
    var ambientTemp = context.getString(R.string.ambient_temp)
    var low = context.getString(R.string.low)
    var high = context.getString(R.string.high)
    var humidity = context.getString(R.string.humidity)
    var pressure = context.getString(R.string.pressure)
    var weatherImage = painterResource(R.drawable.sun)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {
        SkywardBanner(appName)
        Location(location)
        CurrentTemp(currentTemp, ambientTemp, weatherImage)
        OtherWeatherInformation(low, high, humidity, pressure)
    }
}

@Composable
fun SkywardBanner(appName: String) {
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
fun Location(location: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .safeContentPadding()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = location,
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}

@Composable
fun CurrentTemp(currentTemp: String, ambientTemp: String, weatherImage: Painter) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = currentTemp,
                fontSize = 72.sp
            )
            Text(
                text = ambientTemp,
                fontSize = 14.sp,
                modifier = Modifier.padding(12.dp)
            )
        }
        Image(painter = weatherImage,
            contentDescription = null,
            modifier = Modifier
                .size(72.dp),
        )
    }
}

@Composable
fun OtherWeatherInformation(low: String, high: String, humidity: String, pressure: String) {
    Column (
        modifier = Modifier
            .safeContentPadding()
            .padding(all = 24.dp)
    ) {
        Text(text = low)
        Text(text = high)
        Text(text = humidity)
        Text(text = pressure)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SkywardPreview() {
    SkywardTheme {
       MainScreen()
    }
}