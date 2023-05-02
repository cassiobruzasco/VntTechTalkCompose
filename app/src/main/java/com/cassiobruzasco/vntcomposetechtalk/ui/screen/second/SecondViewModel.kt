package com.cassiobruzasco.vntcomposetechtalk.ui.screen.second

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.AirPollutionResponseModel
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.WeatherResponseItem
import com.cassiobruzasco.vntcomposetechtalk.data.remote.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(private val weatherRepository: WeatherRepository): ViewModel() {

    private val _weather = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weather = _weather.asStateFlow()

    init {
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        viewModelScope.launch {
            weatherRepository.getLocation("Campinas").collect { locationResponse ->
                if (locationResponse.isSuccessful) {
                    locationResponse.body()?.let {
                        getForecast(it.coord.lat.toString(), it.coord.lon.toString())
                    } ?: run {
                        _weather.update { WeatherState.Error("ERROR: Empty Body Error") }
                    }
                } else {
                    _weather.update { WeatherState.Error("ERROR: ${locationResponse.message()}") }
                }
            }
        }
    }

    private suspend fun getForecast(lat: String, lon: String) {
        weatherRepository.getWeather(lat, lon)
            .zip(weatherRepository.getAirPollution(lat, lon)) { forecast, pollution ->
                if (forecast.isSuccessful && pollution.isSuccessful) {
                    return@zip WeatherState.Success(forecast.body(), pollution.body())
                } else {
                    return@zip WeatherState.Error("ERROR: getForecast")
                }
            }.collect {
                _weather.value = it
            }
    }

    sealed class WeatherState {
        object Loading: WeatherState()
        class Success(
            val weatherItem: WeatherResponseItem?,
            val airPollution: AirPollutionResponseModel?
            ): WeatherState()
        class Error(val errorMsg: String): WeatherState()
    }
}