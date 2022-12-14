package com.cassiobruzasco.vntcomposetechtalk.data.remote.repository

import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.WeatherResponseItem
import retrofit2.Response

interface WeatherRepository {
    suspend fun getWeather(location: String, count: Int): Response<WeatherResponseItem>
}