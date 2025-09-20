package com.homeapps.translator.ui.features.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.homeapps.translator.ui.features.main.components.Header
import com.homeapps.translator.ui.features.main.components.TranslationItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel()) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val translations = viewModel.translations.collectAsStateWithLifecycle(listOf())
    val word = remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 16.dp, end = 24.dp)
    ) {
        Header(
            query = word.value,
            onQueryChange = { newQuery -> word.value = newQuery },
            translateVisible = state.value !is MainViewModel.UiState.Loading,
            onTranslate = { viewModel.requestNewTranslation(word.value) }
        )
        if (translations.value.isEmpty()) {
            Text(
                text = "No translation history",
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
            }
        }
    }
}