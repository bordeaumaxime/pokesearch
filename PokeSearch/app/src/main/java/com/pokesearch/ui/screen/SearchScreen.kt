package com.pokesearch.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pokesearch.ui.screen.component.SearchScreenInternal

@Composable
fun SearchScreen(viewModel: SearchViewModel, modifier: Modifier = Modifier) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SearchScreenInternal(
        uiState = uiState,
        onQueryChange = {
            viewModel.search(it)
        },
        modifier = modifier
    )
}