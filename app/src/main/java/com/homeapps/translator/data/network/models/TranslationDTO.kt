package com.homeapps.translator.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslationDTO(
    @SerialName("note") val note: String,
    @SerialName("text") val text: String,
)