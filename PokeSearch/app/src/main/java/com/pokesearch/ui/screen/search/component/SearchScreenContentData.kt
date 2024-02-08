package com.pokesearch.ui.screen.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.pokesearch.R
import com.pokesearch.data.model.Pokemon
import com.pokesearch.data.model.PokemonType
import com.pokesearch.ui.screen.search.DataUiState
import com.pokesearch.ui.theme.PokeSearchTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreenContentData(
    uiState: DataUiState.Data<Pokemon>,
    modifier: Modifier
) {
    val pokemon = uiState.data
    Column(modifier = modifier.padding(16.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = pokemon.name.capitalize(Locale.current),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            SearchImage(
                pokemon = uiState.data,
                Modifier
                    .requiredSize(96.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(
                id = R.string.height_m,
                pokemon.heightDecimeters.dmToM()
            ),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(
                id = R.string.weight_kg,
                pokemon.weightHectoGrams.hgToKg()
            ),
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
        ) {
            Text(
                text = stringResource(id = R.string.pokemon_types),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            pokemon.types.forEach {
                Text(
                    text = it.name.capitalize(Locale.current),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                )
            }
        }
    }
}

private fun Float.hgToKg() = this / 10
private fun Float.dmToM() = this / 10

@PreviewLightDark
@Composable
private fun SearchScreenContentDataPreview() {
    PokeSearchTheme {
        Surface {
            SearchScreenContentData(
                uiState = DataUiState.Data(
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
                ),
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}