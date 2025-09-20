package com.homeapps.translator.ui.features.favourites.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homeapps.translator.data.database.models.TranslationDBO

@Composable
fun FavouriteItem(translation: TranslationDBO) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
    ) {
        Text(
            text = translation.word,
            modifier = Modifier
                .weight(0.45f)
        )
        Spacer(modifier = Modifier.weight(0.05f))
        Text(
            text = translation.translation,
            modifier = Modifier.weight(0.5f)
        )
    }
}

@Preview
@Composable
private fun FavouriteItemPreview() {
    FavouriteItem(
        translation = TranslationDBO(1, "cat", "cat", false, 123L)
    )
}