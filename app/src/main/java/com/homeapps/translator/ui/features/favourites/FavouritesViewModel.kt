package com.homeapps.translator.ui.features.favourites

import androidx.lifecycle.ViewModel
import com.homeapps.translator.data.TranslationRepository

class FavouritesViewModel(repository: TranslationRepository): ViewModel() {
    val favouriteTranslations = repository.getFavouriteTranslations()
}