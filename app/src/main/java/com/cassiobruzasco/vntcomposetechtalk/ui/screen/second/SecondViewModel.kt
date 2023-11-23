package com.cassiobruzasco.vntcomposetechtalk.ui.screen.second

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.AirPollutionResponseModel
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.LocationResponseModel
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.ResponseData
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.WeatherResponseItem
import com.cassiobruzasco.vntcomposetechtalk.data.remote.repository.WeatherRepository
import com.cassiobruzasco.vntcomposetechtalk.ui.screen.common.UiCommonState
import com.cassiobruzasco.vntcomposetechtalk.ui.screen.common.toUiCommonState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _weather = MutableStateFlow<UiCommonState<WeatherResponseItem>>(UiCommonState.Idle)
    val weather = _weather.asStateFlow()

    private val _air = MutableStateFlow<UiCommonState<AirPollutionResponseModel>>(UiCommonState.Idle)
    val air = _air.asStateFlow()

    private val _locationInput = MutableStateFlow("Campinas")
    val locationInput = _locationInput.asStateFlow()

    fun getWeatherForLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            _weather.value = UiCommonState.Loading
            _air.value = UiCommonState.Loading
            when (val res = weatherRepository.getLocation(_locationInput.value).single()) {
                is ResponseData.Success -> {
                    val lat = res.response.coord.lat.toString()
                    val lon = res.response.coord.lon.toString()
                    listOf(
                        async { fetchWeatherData(lat, lon) },
                        async { getAirPollution(lat, lon) }
                    )
                }

                else -> {
                    _weather.value = UiCommonState.Error(errorMsg = "Location Unknown")
                    _air.value = UiCommonState.Error(errorMsg = "Location Unknown")
                }
            }
        }
    }

    private suspend fun fetchWeatherData(lat: String, lon: String) {
        _weather.value = weatherRepository.getWeather(lat, lon).single().toUiCommonState { it.response }
    }

    private suspend fun getAirPollution(lat: String, lon: String) {
        _air.value = weatherRepository.getAirPollution(lat, lon).single().toUiCommonState { it.response }
    }

    fun setLocation(location: String) {
        _locationInput.value = location
    }
}
