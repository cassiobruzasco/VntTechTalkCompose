package com.cassiobruzasco.vntcomposetechtalk.data.remote.model

import com.google.gson.annotations.SerializedName

data class AirPollutionResponseModel(
    @SerializedName("list") val list: List<AirPollutionResponseContent>
)

data class AirPollutionResponseContent(
    @SerializedName("components") val components: ComponentsModel
)
data class ComponentsModel(
    @SerializedName("co") val co: Float,
    @SerializedName("no") val no: Float,
    @SerializedName("no2") val no2: Float,
    @SerializedName("o3") val o3: Float,
)