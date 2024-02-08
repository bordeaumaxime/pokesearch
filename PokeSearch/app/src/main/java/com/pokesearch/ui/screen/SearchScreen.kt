package com.pokesearch.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.pokesearch.ui.screen.component.SearchScreenInternal

@Composable
fun SearchScreen(modifier: Modifier = Modifier) {
    SearchScreenInternal(
        uiState = SearchUiState("", DataUiState.Loading),
        onQueryChange = {},
        modifier = modifier)
}