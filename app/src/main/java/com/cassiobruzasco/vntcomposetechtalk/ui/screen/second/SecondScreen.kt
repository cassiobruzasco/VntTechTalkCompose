package com.cassiobruzasco.vntcomposetechtalk.ui.screen.second

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.AirPollutionResponseContent
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.AirPollutionResponseModel
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.ComponentsModel
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.DayModel
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.ForeCastModel
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.WeatherResponseItem
import com.cassiobruzasco.vntcomposetechtalk.ui.screen.common.UiCommonState
import com.cassiobruzasco.vntcomposetechtalk.ui.theme.Purple700
import com.cassiobruzasco.vntcomposetechtalk.ui.theme.Theme

@Composable
fun SecondScreen(
    navController: NavController,
    viewModel: SecondViewModel = hiltViewModel()
) {
    val weatherInfo by viewModel.weather.collectAsStateWithLifecycle()
    val location by viewModel.locationInput.collectAsStateWithLifecycle()
    val air by viewModel.air.collectAsStateWithLifecycle()

    SecondScreenContent(
        location,
        weatherInfo,
        air,
        setLocation = { viewModel.setLocation(it) },
        fetchData = viewModel::getWeatherForLocation,
        back = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SecondScreenContent(
    location: String,
    weatherInfo: UiCommonState<WeatherResponseItem>,
    air: UiCommonState<AirPollutionResponseModel>,
    setLocation: (String) -> Unit,
    fetchData: () -> Unit,
    back: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Second Screen",
                        fontSize = 25.sp,
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Purple700,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),

                onClick = { back() }
            ) {
                Text(
                    text = "back",
                    fontSize = 25.sp
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                value = location, onValueChange = setLocation
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                onClick = fetchData
            ) {
                Text(
                    text = "get forecast",
                    fontSize = 25.sp
                )
            }
            WeatherItem(weatherInfo)
            AirPollutionCard(air)
        }
    }
}

@Composable
fun WeatherItem(
    weatherInfo: UiCommonState<WeatherResponseItem>
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        when (weatherInfo) {
            is UiCommonState.Error -> {
                Text(
                    text = "An error has occurred.\n${(weatherInfo).errorMsg}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }
            UiCommonState.Idle -> {
                Text(
                    text = "Waiting for forecast data",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }
            UiCommonState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.wrapContentSize(),
                        color = Purple700
                    )
                }
            }
            is UiCommonState.Success -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Today: ${weatherInfo.data.main.temperature.toString().substringBefore(".")
                        }ºC\nHumidity: ${weatherInfo.data.main.humidity}%",
                        fontSize = 25.sp,
                    )
                    Image(
                        painter = rememberAsyncImagePainter("https://openweathermap.org/img/wn/${weatherInfo.data.weather[0].icon}@2x.png"),
                        contentDescription = "Forecast Image",
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }

    }
}

@Composable
fun AirPollutionCard(
    air: UiCommonState<AirPollutionResponseModel>
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        when (air) {
            is UiCommonState.Error -> {
                Text(
                    text = "An error has occurred.\n${(air).errorMsg}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }
            UiCommonState.Idle -> {
                Text(
                    text = "Waiting for air pollution data",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }
            UiCommonState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 5.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.wrapContentSize(),
                        color = Purple700
                    )
                }
            }
            is UiCommonState.Success -> {
                air.data.list.forEach {
                    val coText = "Concentration of CO: ${it.components.co}μg/m3"
                    val noText = "Concentration of NO: ${it.components.no}μg/m3"
                    val no2Text = "Concentration of NO2: ${it.components.no2}μg/m3"
                    val o3Text = "Concentration of O3: ${it.components.o3}μg/m3"

                    repeat (3) { i ->
                        val text = when (i) {
                            0 -> coText
                            1 -> noText
                            2 -> no2Text
                            3 -> o3Text
                            else -> ""
                        }
                        Text(
                            modifier = Modifier.padding(15.dp),
                            text = text,
                            fontSize = 18.sp,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondScreenPreview() {
    Theme {
        SecondScreenContent(
            weatherInfo = UiCommonState.Success(WeatherResponseItem(
                listOf(ForeCastModel("")),
                DayModel(10.0, humidity = 10)
            )),
            air = UiCommonState.Success(
                AirPollutionResponseModel(
                    listOf(
                        AirPollutionResponseContent(
                            ComponentsModel(co = 10f, 10f, 20f, 30f)
                        )
                    )
                )
            ),
            location = "Campinas",
            setLocation = {},
            fetchData = {},
            back = {}
        )
    }
}
