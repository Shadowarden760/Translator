package com.homeapps.translator.ui.features.main

import android.content.Context
import android.widget.Toast
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
    private val appContext: Context,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {
    private val _state = MutableStateFlow<UiState>(UiState.Default)
    val state = _state.asStateFlow()
    val translations = repository.getAllTranslations()

    fun updateUiState(newState: UiState) {
        _state.value = newState
    }

    fun requestNewTranslation(word: String) = viewModelScope.launch {
        if (word.isEmpty()) return@launch
        updateUiState(newState = UiState.Loading)
        val response = withContext(dispatcher) {
            repository.requestTranslation(word = word)
        }
        when {
            response.isSuccess -> {
                response.getOrNull()?.let { translations ->
                    val translation = translations.firstOrNull()
                    if (translation == null) {
                        Toast.makeText(appContext, "No translation found", Toast.LENGTH_LONG).show()
                    } else {
                        repository.insertTranslation(translation = translation.toTranslationDBO())
                    }
                }
                updateUiState(newState = UiState.TranslationSuccess)
            }
            response.isFailure -> {
                Toast.makeText(appContext, "Error", Toast.LENGTH_LONG).show()
                updateUiState(newState = UiState.TranslationFailure)
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


    sealed class UiState() {
        object Default: UiState()
        object Loading: UiState()
        object TranslationSuccess: UiState()
        object TranslationFailure: UiState()
    }
}