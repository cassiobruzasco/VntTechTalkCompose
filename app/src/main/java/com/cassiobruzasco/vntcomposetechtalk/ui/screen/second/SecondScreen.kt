package com.cassiobruzasco.vntcomposetechtalk.ui.screen.second

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.DayModel
import com.cassiobruzasco.vntcomposetechtalk.ui.screen.component.NavigationButton

@Composable
fun SecondScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {

        val viewModel: SecondViewModel = hiltViewModel()
        val weatherInfo = viewModel.weather.collectAsState()
        val composeWeatherInfo = viewModel.composeWeather
        Spacer(modifier = Modifier.height(20.dp))
        //when (weatherInfo.value) {} - In case of StateFlow
        when (composeWeatherInfo) {
            SecondViewModel.WeatherState.Error -> {
                Text(
                    text = "An error has occurred.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            SecondViewModel.WeatherState.Loading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .scale(0.3f)
                )
            }
            is SecondViewModel.WeatherState.Success -> {
                // val list = (weatherInfo.value as SecondViewModel.WeatherState.Success)
                Column {
                    // list.forEachIndexed {}
                    composeWeatherInfo.weatherItem.list.forEachIndexed { index, forecast ->
                        WeatherItem(weatherItem = forecast, index = index)
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    NavigationButton(
                        navController = navController,
                        screen = "first_screen",
                        text = "Go to first screen"
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(PaddingValues(20.dp)),
                        onClick = { navController.popBackStack() }
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
fun WeatherItem(
    modifier: Modifier = Modifier,
    weatherItem: DayModel,
    index: Int
) {
    val day = if (index == 0) "today" else "tomorrow"

    Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Text(
            text = "Temperature $day: ${
                weatherItem.temperature.day.toString().substringBefore(".")
            }ºC\nHumidity: ${weatherItem.humidity}%",
            fontSize = 25.sp
        )
        Image(
            painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}@2x.png"),
            contentDescription = "Forecast Image",
            modifier = modifier.size(64.dp)
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
}
