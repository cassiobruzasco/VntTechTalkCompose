package com.cassiobruzasco.vntcomposetechtalk.data.remote.repository

import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.AirPollutionResponseModel
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.LocationResponseModel
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.WeatherResponseItem
import com.cassiobruzasco.vntcomposetechtalk.data.remote.service.WeatherApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val api: WeatherApi) : WeatherRepository {
    override suspend fun getWeather(
        lat: String,
        lon: String,
    ): Flow<Response<WeatherResponseItem>> = flow {
        emit(
            api.getWeather(
                lat = lat,
                lon = lon,
            )
        )
    }

    override suspend fun getLocation(location: String): Flow<Response<LocationResponseModel>> = flow {
        emit(api.getLocation(location))
    }


    override suspend fun getAirPollution(
        lat: String,
        lon: String
    ): Flow<Response<AirPollutionResponseModel>> = flow {
        emit(api.getAirPollutionAtLocation(lat, lon))
    }
}