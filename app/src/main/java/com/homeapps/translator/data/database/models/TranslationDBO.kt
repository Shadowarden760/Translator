package com.homeapps.translator.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Translations")
data class TranslationDBO(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("translation_id") val id: Int,
    @ColumnInfo("translation_word") val word: String,
    @ColumnInfo("translation_result") val translation: String,
    @ColumnInfo("translation_favourite") val favourite: Boolean,
    @ColumnInfo("created_at") val createdAt: Long,
)
