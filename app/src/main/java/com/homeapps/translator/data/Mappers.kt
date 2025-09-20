package com.homeapps.translator.data

import com.homeapps.translator.data.database.models.TranslationDBO
import com.homeapps.translator.data.network.models.TranslationDTOItem

fun TranslationDTOItem.toTranslationDBO(): TranslationDBO {
    return TranslationDBO(
        id = this.id,
        word = this.text,
        translation = this.meanings[0].translation.text,
        favourite = false,
        createdAt = System.currentTimeMillis()
    )
}