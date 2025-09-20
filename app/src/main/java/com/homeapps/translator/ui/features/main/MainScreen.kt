package com.homeapps.translator.ui.features.main

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.homeapps.translator.R
import com.homeapps.translator.ui.features.main.components.Header
import com.homeapps.translator.ui.features.main.components.TranslationItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {
    val context = LocalContext.current
    val translationState = viewModel.translationState.collectAsStateWithLifecycle()
    val translations = viewModel.translations.collectAsStateWithLifecycle(listOf())
    val word = viewModel.wordToTranslate.collectAsStateWithLifecycle()

    LaunchedEffect(translationState.value) {
        when(translationState.value) {
            MainViewModel.TranslationStatus.Default -> { /* do nothing */ }
            MainViewModel.TranslationStatus.Loading -> { /* do nothing */ }
            MainViewModel.TranslationStatus.TranslationFailure -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.main_text_error),
                    Toast.LENGTH_LONG
                ).show()
                viewModel.updateUiState(MainViewModel.TranslationStatus.Default)
            }
            is MainViewModel.TranslationStatus.TranslationSuccess -> {
                if (!(translationState.value as MainViewModel.TranslationStatus.TranslationSuccess).translationFound) {
                    Toast.makeText(
                        context,
                        context.getString(R.string.main_text_no_translation_found),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    viewModel.updateWordToTranslate("")
                }
                viewModel.updateUiState(MainViewModel.TranslationStatus.Default)
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 16.dp, end = 24.dp)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        )
        Header(
            query = word.value,
            onQueryChange = { newQuery -> viewModel.updateWordToTranslate(newQuery) },
            translateEnabled = translationState.value !is MainViewModel.TranslationStatus.Loading,
            onTranslate = { viewModel.requestNewTranslation(word.value) }
        )
        if (translations.value.isEmpty()) {
            Text(
                text = stringResource(R.string.main_text_no_translation_history),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(translations.value) { translation ->
                TranslationItem(
                    translation = translation,
                    onFavouriteChange = {
                        viewModel.changeTranslationFavourite(
                            translation = translation, favourite = it
                        )
                    },
                    onDelete = { viewModel.deleteTranslation(translation = translation) }
                )
                HorizontalDivider(
                    thickness = DividerDefaults.Thickness,
                    color = DividerDefaults.color
                )
            }
        }
    }
}