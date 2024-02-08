package com.pokesearch.ui.screen.component

import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.pokesearch.R
import com.pokesearch.data.model.Pokemon
import com.pokesearch.data.model.PokemonType
import com.pokesearch.ui.theme.PokeSearchTheme

@Composable
fun SearchImage(pokemon: Pokemon, modifier: Modifier = Modifier) {
    AsyncImage(
        model = pokemon.imageGifUrl,
        error = painterResource(R.drawable.image_error),
        contentDescription = stringResource(
            R.string.pokemon_image,
            pokemon.name
        ),
        contentScale = ContentScale.Fit,
        modifier = modifier
            .padding(4.dp)

    )
}

@Preview
@Composable
fun PreviewSearchImage() {
    PokeSearchTheme {
        Surface {
            SearchImage(
                pokemon = Pokemon(
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
                ),
                modifier = Modifier.requiredSize(48.dp)
            )
        }
    }
}