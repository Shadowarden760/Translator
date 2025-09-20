package com.homeapps.translator.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeaningDTO(
    @SerialName("id") val id: Int,
    @SerialName("imageUrl") val imageUrl: String,
    @SerialName("partOfSpeechCode") val partOfSpeechCode: String,
    @SerialName("previewUrl") val previewUrl: String,
    @SerialName("soundUrl") val soundUrl: String,
    @SerialName("transcription") val transcription: String,
    @SerialName("translation") val translation: TranslationDTO,
)