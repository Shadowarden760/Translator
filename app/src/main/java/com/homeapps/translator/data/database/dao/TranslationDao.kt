package com.homeapps.translator.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.homeapps.translator.data.database.models.TranslationDBO
import kotlinx.coroutines.flow.Flow

@Dao
interface TranslationDao {

    @Upsert
    suspend fun upsertTranslation(translation: TranslationDBO)

    @Query("SELECT * FROM Translations ORDER BY created_at DESC")
    fun getAllTranslationsFlow(): Flow<List<TranslationDBO>>

    @Query("SELECT * FROM Translations WHERE translation_favourite ORDER BY created_at DESC")
    fun getFavouriteTranslationsFlow(): Flow<List<TranslationDBO>>

    @Delete
    suspend fun deleteTranslation(translation: TranslationDBO)
}