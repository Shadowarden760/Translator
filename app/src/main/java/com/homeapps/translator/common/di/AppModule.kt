package com.homeapps.translator.common.di


import com.homeapps.translator.BuildConfig
import com.homeapps.translator.data.TranslationRepository
import com.homeapps.translator.data.database.TranslationDatabase
import com.homeapps.translator.data.database.createTranslationDatabase
import com.homeapps.translator.data.network.TranslationApi
import com.homeapps.translator.data.network.createTranslationApi
import com.homeapps.translator.ui.features.favourites.FavouritesViewModel
import com.homeapps.translator.ui.features.main.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TranslationApi> { createTranslationApi(baseUrl = BuildConfig.BASE_URL) }
    single<TranslationDatabase> { createTranslationDatabase(appContext = androidContext()) }

    single {
        val translationDatabase: TranslationDatabase = get()
        TranslationRepository(
            translationApi = get(),
            translationDao = translationDatabase.translationDao()
        )
    }

    viewModel { MainViewModel(repository = get()) }
    viewModel { FavouritesViewModel(repository = get()) }
}