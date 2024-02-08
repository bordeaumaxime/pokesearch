package com.pokesearch.ui.screen.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pokesearch.ui.screen.search.component.SearchScreenInternal

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