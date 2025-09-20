package com.homeapps.translator.data

import com.homeapps.translator.data.database.dao.TranslationDao
import com.homeapps.translator.data.database.models.TranslationDBO
import com.homeapps.translator.data.network.TranslationApi
import com.homeapps.translator.data.network.models.TranslationResponseDTO

class TranslationRepository(
    private val translationApi: TranslationApi,
    private val translationDao: TranslationDao,
) {

    suspend fun requestTranslation(word: String): Result<TranslationResponseDTO> {
        return translationApi.requestTranslation(word = word)
    }

    suspend fun insertTranslation(translation: TranslationDBO) {
        translationDao.upsertTranslation(translation = translation)
    }

    fun getAllTranslations() = translationDao.getAllTranslationsFlow()

    fun getFavouriteTranslations() = translationDao.getFavouriteTranslationsFlow()

    suspend fun deleteTranslation(translation: TranslationDBO) {
        translationDao.deleteTranslation(translation = translation)
    }
}