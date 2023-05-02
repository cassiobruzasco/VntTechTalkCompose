package com.cassiobruzasco.vntcomposetechtalk.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherResponseItem(
    @SerializedName("weather") val weather: List<ForeCastModel>,
    @SerializedName("main") val main: DayModel,
)

data class DayModel(
    @SerializedName("temp") val temperature: Double,
    @SerializedName("humidity") val humidity: Int,
)

data class ForeCastModel(
    @SerializedName("icon") val icon: String
)