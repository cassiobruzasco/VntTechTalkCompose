package com.cassiobruzasco.vntcomposetechtalk.ui.screen.second

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.ComponentsModel
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.WeatherResponseItem

@Composable
fun SecondScreen(navController: NavController, roll: Int) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Log.d("SecondScreen", "Roll as a parameter: $roll")

        val viewModel: SecondViewModel = hiltViewModel()
        val weatherInfo by viewModel.weather.collectAsStateWithLifecycle()

        when (weatherInfo) {
            is SecondViewModel.WeatherState.Error -> {
                Text(
                    text = "An error has occurred. ${(weatherInfo as SecondViewModel.WeatherState.Error).errorMsg}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }
            SecondViewModel.WeatherState.Loading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    strokeWidth = 8.dp,
                    modifier = Modifier.scale(0.3f)
                )
            }
            is SecondViewModel.WeatherState.Success -> {
                val response = (weatherInfo as SecondViewModel.WeatherState.Success)
                Column {

                    response.weatherItem?.let {
                        WeatherItem(weatherItem = it)
                    }
                    response.airPollution?.list?.get(0)?.let {
                        AirPollutionCard(it.components)
                    }
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(PaddingValues(20.dp)),
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Text(
                            text = "Pop back stack",
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherItem(weatherItem: WeatherResponseItem) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(PaddingValues(12.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Today: ${weatherItem.main.temperature.toString().substringBefore(".")
                }ºC\nHumidity: ${weatherItem.main.humidity}%",
                fontSize = 25.sp,
            )
            Image(
                painter = rememberAsyncImagePainter(
                    "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}@2x.png"
                ),
                contentDescription = "Forecast Image",
                modifier = Modifier.size(64.dp)
            )
        }
    }
}

@Composable
fun AirPollutionCard(
    airPollutionItem: ComponentsModel
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(PaddingValues(12.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val coText = "Concentration of CO (Carbon monoxide):\n${airPollutionItem.co}μg/m3"
            val noText = "Concentration of NO (Nitrogen monoxide):\n${airPollutionItem.no}μg/m3"
            val no2Text = "Concentration of NO2 (Nitrogen dioxide):\n${airPollutionItem.no2}μg/m3"
            val o3Text = "Concentration of O3 (Ozone):\n${airPollutionItem.o3}μg/m3"
            for (i in 0..3) {
                val text = when (i) {
                    0 -> coText
                    1 -> noText
                    2 -> no2Text
                    3 -> o3Text
                    else -> ""
                }
                Text(
                    text = text,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(PaddingValues(0.dp, 6.dp))
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}
