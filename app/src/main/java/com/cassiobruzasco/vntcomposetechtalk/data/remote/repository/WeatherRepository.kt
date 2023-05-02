package com.cassiobruzasco.vntcomposetechtalk.data.remote.repository

import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.AirPollutionResponseModel
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.LocationResponseModel
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.WeatherResponseItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface WeatherRepository {
    suspend fun getWeather(lat: String, lon: String, count: Int): Flow<Response<WeatherResponseItem>>
    suspend fun getLocation(location: String): Flow<Response<LocationResponseModel>>
    suspend fun getAirPollution(lat: String, lon: String): Flow<Response<AirPollutionResponseModel>>


}