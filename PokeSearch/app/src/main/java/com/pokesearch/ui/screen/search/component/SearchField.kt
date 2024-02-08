package com.pokesearch.ui.screen.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.pokesearch.R
import com.pokesearch.ui.theme.PokeSearchTheme

@Composable
fun SearchTextField(
    search: String,
    onSearchChanged: (String) -> Unit,
    onClearButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    OutlinedTextField(
        value = search,
        placeholder = {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.search_field_placeholder),
                    modifier = Modifier.alpha(0.4f)
                )
            }
        },
        onValueChange = onSearchChanged,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.search_pokemon)
            )
        },
        trailingIcon = {
            IconButton(onClick = onClearButtonClick) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = stringResource(R.string.clear_search)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        modifier = modifier.focusRequester(focusRequester)
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@PreviewLightDark
@Composable
private fun SearchTextFieldPreview() {
    PokeSearchTheme {
        Surface {
            SearchTextField(
                search = "bulbasaur",
                onSearchChanged = {},
                onClearButtonClick = {},
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun SearchTextFieldEmptyPreview() {
    PokeSearchTheme {
        Surface(modifier = Modifier.height(64.dp)) {
            SearchTextField(
                search = "",
                onSearchChanged = {},
                onClearButtonClick = {},
            )
        }
    }
}