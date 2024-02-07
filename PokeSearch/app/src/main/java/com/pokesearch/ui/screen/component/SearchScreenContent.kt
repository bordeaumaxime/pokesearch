package com.pokesearch.ui.screen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.pokesearch.ui.theme.PokeSearchTheme

@Composable
fun SearchScreenContent(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Text(text = "Search Screen")
    }
}

@Preview
@Composable
fun PreviewSearchScreenContent() {
    PokeSearchTheme {
        Surface {
            SearchScreenContent(modifier = Modifier.fillMaxSize())
        }
    }
}