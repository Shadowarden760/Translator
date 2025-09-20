package com.homeapps.translator.ui.features.favourites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.homeapps.translator.R
import com.homeapps.translator.ui.features.favourites.components.FavouriteItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavouritesScreen(viewModel: FavouritesViewModel = koinViewModel()) {
    val favouriteTranslations = viewModel.favouriteTranslations.collectAsStateWithLifecycle(listOf())
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 24.dp, top = 16.dp, end = 24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.favourite_text_your_favourite_translations),
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.weight(0.8f)
            )
            Card(
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .weight(0.2f)
            ) {
                Text(
                    text = favouriteTranslations.value.size.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
        if (favouriteTranslations.value.isEmpty()) {
            Text(
                text = stringResource(R.string.favourite_text_there_are_no_favourite_translations),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
        LazyColumn {
            items(favouriteTranslations.value) {
                FavouriteItem(translation = it)
                HorizontalDivider(
                    thickness = DividerDefaults.Thickness,
                    color = DividerDefaults.color
                )
            }
        }
    }
}