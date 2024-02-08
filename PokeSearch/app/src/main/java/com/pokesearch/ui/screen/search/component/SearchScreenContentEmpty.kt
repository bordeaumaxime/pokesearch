package com.pokesearch.ui.screen.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.pokesearch.R
import com.pokesearch.ui.theme.PokeSearchTheme

@Composable
fun SearchScreenContentEmpty(modifier: Modifier) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.search_content_empty),
            textAlign = TextAlign.Center
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewSearchScreenContentEmpty() {
    PokeSearchTheme {
        Surface {
            SearchScreenContentEmpty(
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}