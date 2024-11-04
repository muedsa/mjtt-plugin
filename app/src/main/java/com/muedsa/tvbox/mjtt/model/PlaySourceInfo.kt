package com.muedsa.tvbox.mjtt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaySourceInfo(
    @SerialName("Vod") val vod: List<String>,
    @SerialName("Data") val data: List<PlayUrlData>
)