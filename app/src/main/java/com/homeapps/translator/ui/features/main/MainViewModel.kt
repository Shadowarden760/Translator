package com.homeapps.translator.ui.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homeapps.translator.data.TranslationRepository
import com.homeapps.translator.data.database.models.TranslationDBO
import com.homeapps.translator.data.toTranslationDBO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val repository: TranslationRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {
    private val _translationState = MutableStateFlow<TranslationStatus>(TranslationStatus.Default)
    val translationState = _translationState.asStateFlow()
    private val _wordToTranslate = MutableStateFlow("")
    val wordToTranslate = _wordToTranslate.asStateFlow()
    val translations = repository.getAllTranslations()

    fun updateWordToTranslate(newWord: String) {
        _wordToTranslate.value = newWord
    }

    fun updateUiState(newState: TranslationStatus) {
        _translationState.value = newState
    }

    fun requestNewTranslation(word: String) = viewModelScope.launch {
        if (word.isEmpty()) return@launch
        updateUiState(newState = TranslationStatus.Loading)
        val response = withContext(dispatcher) {
            repository.requestTranslation(word = word)
        }
        when {
            response.isSuccess -> {
                response.getOrNull()?.let { translations ->
                    val translation = translations.firstOrNull()
                    if (translation == null) {
                        updateUiState(newState = TranslationStatus.TranslationSuccess(translationFound = false))
                    } else {
                        repository.insertTranslation(translation = translation.toTranslationDBO())
                        updateUiState(newState = TranslationStatus.TranslationSuccess(translationFound = true))
                    }
                }
            }
            response.isFailure -> {
                updateUiState(newState = TranslationStatus.TranslationFailure)
            }
        }
    }

    fun deleteTranslation(translation: TranslationDBO) = CoroutineScope(dispatcher).launch {
        repository.deleteTranslation(translation = translation)
    }

    fun changeTranslationFavourite(translation: TranslationDBO, favourite: Boolean) = CoroutineScope(dispatcher).launch {
        val favouriteTranslation = translation.copy(favourite = favourite)
        repository.insertTranslation(translation = favouriteTranslation)
    }


    sealed class TranslationStatus() {
        object Default: TranslationStatus()
        object Loading: TranslationStatus()
        class TranslationSuccess(val translationFound: Boolean): TranslationStatus()
        object TranslationFailure: TranslationStatus()
    }
}