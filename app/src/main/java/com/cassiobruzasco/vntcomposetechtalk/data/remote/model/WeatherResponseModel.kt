package com.cassiobruzasco.vntcomposetechtalk.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherResponseItem(
    @SerializedName("list") val list: MutableList<DayModel>
)

data class DayModel(
    @SerializedName("temp") val temperature: TemperatureModel,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("weather") val weather: List<ForeCastModel>,
)

data class TemperatureModel(
    @SerializedName("day") val day: Double
)

data class ForeCastModel(
    @SerializedName("icon") val icon: String
)