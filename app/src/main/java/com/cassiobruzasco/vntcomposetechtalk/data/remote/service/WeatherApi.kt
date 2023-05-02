package com.cassiobruzasco.vntcomposetechtalk.data.remote.service

import com.cassiobruzasco.vntcomposetechtalk.BuildConfig
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.AirPollutionResponseModel
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.LocationResponseModel
import com.cassiobruzasco.vntcomposetechtalk.data.remote.model.WeatherResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("mode") mode: String = "json",
        @Query("units") units: String = "metric",
        @Query("appid") apiKey: String = BuildConfig.weatherKey,
    ): Response<WeatherResponseItem>

    @GET("weather")
    suspend fun getLocation(
        @Query("q") location: String,
        @Query("appid") apiKey: String = BuildConfig.weatherKey,
    ): Response<LocationResponseModel>

    @GET("air_pollution")
    suspend fun getAirPollutionAtLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("mode") mode: String = "json",
        @Query("appid") apiKey: String = BuildConfig.weatherKey,
    ): Response<AirPollutionResponseModel>
}