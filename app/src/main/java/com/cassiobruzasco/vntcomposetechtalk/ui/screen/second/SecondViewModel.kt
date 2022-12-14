package com.cassiobruzasco.vntcomposetechtalk.ui.screen.second

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.WeatherResponseItem
import com.cassiobruzasco.vntcomposetechtalk.data.remote.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(private val weatherRepository: WeatherRepository): ViewModel() {

    private val _weather = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weather = _weather.asStateFlow()

    var composeWeather by mutableStateOf<WeatherState>(WeatherState.Loading)
        private set

    init {
        getWeather()
    }

    private fun getWeather() {
        viewModelScope.launch {
            val response = weatherRepository.getWeather("Campinas", 2)
            if (response.isSuccessful) {
                response.body()?.let { weatherItem ->
                    // same way as past project
                    //_weather.update { WeatherState.Success(weatherItem) }
                    composeWeather = WeatherState.Success(weatherItem)
                } ?: kotlin.run {
                    //_weather.update { WeatherState.Error }
                    composeWeather = WeatherState.Error
                }
            } else {
                composeWeather = WeatherState.Error
            }
        }
    }

    sealed class WeatherState {
        object Loading: WeatherState()
        class Success(val weatherItem: WeatherResponseItem): WeatherState()
        object Error: WeatherState()
    }
}