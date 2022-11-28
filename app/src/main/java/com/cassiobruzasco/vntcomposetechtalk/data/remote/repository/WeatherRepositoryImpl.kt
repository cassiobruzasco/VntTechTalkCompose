package com.cassiobruzasco.vntcomposetechtalk.data.remote.repository

import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.WeatherResponseItem
import com.cassiobruzasco.vntcomposetechtalk.data.remote.service.WeatherApi
import com.cassiobruzasco.vntcomposetechtalk.data.remote.repository.WeatherRepository
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val api: WeatherApi): WeatherRepository {

    override suspend fun getWeather(location: String, count: Int): Response<WeatherResponseItem> {
        return api.getWeather(location = location, count = count)
    }
}