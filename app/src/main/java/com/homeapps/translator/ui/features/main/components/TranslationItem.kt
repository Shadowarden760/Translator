package com.homeapps.translator.ui.features.main.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homeapps.translator.R
import com.homeapps.translator.data.database.models.TranslationDBO

@Composable
fun TranslationItem(
    translation: TranslationDBO,
    onFavouriteChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = translation.word,
            modifier = Modifier.weight(0.3f)
        )
        Text(
            text = translation.translation,
            modifier = Modifier.weight(0.3f)
        )
        IconButton(onClick = { onFavouriteChange(!translation.favourite) }) {
            Icon(
                painter =
                    if (translation.favourite)
                        painterResource(R.drawable.ic_favourite_check)
                    else
                        painterResource(R.drawable.ic_favourite),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .weight(0.2f)
            )
        }
        IconButton(onClick = onDelete) {
            Icon(
                painter = painterResource(R.drawable.ic_remove),
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
                    .weight(0.2f)
            )
        }
    }
}

@Preview
@Composable
private fun TranslationItemPreview() {
    TranslationItem(
        translation = TranslationDBO(1, "cat", "cat", false, 123L),
        onFavouriteChange = {},
        onDelete = {}
    )
}