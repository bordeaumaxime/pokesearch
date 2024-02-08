package com.pokesearch.ui.screen.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.pokesearch.ui.theme.PokeSearchTheme

@Composable
fun SearchScreenContentLoading(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .testTag(LOADING_INDICATOR)
                .width(64.dp),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

const val LOADING_INDICATOR = "LOADING_INDICATOR"

@PreviewLightDark
@Composable
private fun PreviewSearchScreenContentLoading() {
    PokeSearchTheme {
        Surface {
            SearchScreenContentLoading(modifier = Modifier.fillMaxSize())
        }
    }
}