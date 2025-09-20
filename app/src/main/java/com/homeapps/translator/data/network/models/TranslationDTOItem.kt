package com.homeapps.translator.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranslationDTOItem(
    @SerialName("id") val id: Int,
    @SerialName("meanings") val meanings: List<MeaningDTO>,
    @SerialName("text") val text: String,
)