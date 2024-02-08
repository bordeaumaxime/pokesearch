package com.pokesearch.ui.screen.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.pokesearch.data.model.Pokemon
import com.pokesearch.data.model.PokemonType
import com.pokesearch.ui.screen.DataUiState
import com.pokesearch.ui.screen.SearchUiState
import com.pokesearch.ui.theme.PokeSearchTheme

@Composable
fun SearchScreenInternal(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        SearchTextField(
            search = uiState.query,
            onSearchChanged = onQueryChange,
            onClearButtonClick = { onQueryChange("") },
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedContent(
            targetState = uiState.dataUiState,
            label = "SearchScreenContent", // only for preview purposes
            modifier = Modifier.fillMaxSize()
        ) { targetState ->
            val contentModifier = Modifier.fillMaxSize()
            // one component for each DataUiState: Loading, Data (with the actual Pokemon), Empty and Error
            when (targetState) {
                DataUiState.Empty -> {
                    SearchScreenContentEmpty(modifier = contentModifier)
                }

                DataUiState.Loading -> {
                    SearchScreenContentLoading(modifier = contentModifier)
                }

                is DataUiState.Data -> {
                    SearchScreenContentData(targetState, contentModifier)
                }

                is DataUiState.Error -> SearchScreenContentError(
                    targetState,
                    modifier = contentModifier
                )

            }
        }
    }
}

class SearchUiStateParameterProvider : PreviewParameterProvider<SearchUiState> {
    override val values = sequenceOf(
        SearchUiState(query = "", DataUiState.Empty),
        SearchUiState(query = "bulbasaur", DataUiState.Loading),
        SearchUiState(
            query = "bulbasaur", dataUiState = DataUiState.Data(
                data = Pokemon(
                    id = 1,
                    name = "bulbasaur",
                    heightDecimeters = 7f,
                    weightHectoGrams = 69f,
                    types = listOf(
                        PokemonType("grass"),
                        PokemonType("poison")
                    ),
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                    imageGifUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/1.gif"
                )
            )
        ),
        SearchUiState(
            query = "",
            DataUiState.Error(DataUiState.Error.ErrorType.NOT_FOUND)
        ),
    )
}


@Preview
@Composable
private fun PreviewSearchScreenInternal(
    @PreviewParameter(SearchUiStateParameterProvider::class) searchUiState: SearchUiState
) {
    PokeSearchTheme {
        Surface {
            SearchScreenInternal(
                uiState = searchUiState,
                onQueryChange = {},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}