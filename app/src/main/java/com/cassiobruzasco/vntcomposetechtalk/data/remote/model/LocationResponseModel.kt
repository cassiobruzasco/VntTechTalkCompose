package com.cassiobruzasco.vntcomposetechtalk.data.remote.model

import com.google.gson.annotations.SerializedName

data class LocationResponseModel(
    @SerializedName("coord") val coord: CoordinateModel
)

data class CoordinateModel(
    @SerializedName("lon") val lon: Float,
    @SerializedName("lat") val lat: Float,
)