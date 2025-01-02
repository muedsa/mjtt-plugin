package com.muedsa.tvbox.mjtt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayUrlData(
    @SerialName("servername") val serverName: String? = null,
    @SerialName("playname") val playName: String,
    @SerialName("playurls") val playUrls: List<List<String>>, // [[name,source,pageUrl]]
)
