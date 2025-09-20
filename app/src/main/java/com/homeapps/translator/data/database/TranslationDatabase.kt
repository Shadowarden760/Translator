package com.homeapps.translator.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.homeapps.translator.data.database.dao.TranslationDao
import com.homeapps.translator.data.database.models.TranslationDBO

@Database(
    entities = [TranslationDBO::class],
    exportSchema = true,
    version = 1
)
abstract class TranslationDatabase: RoomDatabase() {

    abstract fun translationDao(): TranslationDao
}

fun createTranslationDatabase(appContext: Context): TranslationDatabase {
    return Room.databaseBuilder(
        context = appContext,
        klass = TranslationDatabase::class.java,
        name = "translation_db"
    ).build()
}