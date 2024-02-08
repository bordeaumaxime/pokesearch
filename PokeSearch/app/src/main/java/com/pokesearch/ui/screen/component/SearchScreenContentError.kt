package com.pokesearch.ui.screen.component

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.pokesearch.R
import com.pokesearch.ui.screen.DataUiState
import com.pokesearch.ui.theme.PokeSearchTheme

@Composable
fun SearchScreenContentError(
    uiState: DataUiState.Error,
    modifier: Modifier
) {
    Box(
        modifier = modifier.padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = when (uiState.errorType) {
                DataUiState.Error.ErrorType.NETWORK -> stringResource(R.string.search_error_network)
                DataUiState.Error.ErrorType.NOT_FOUND -> stringResource(R.string.search_error_not_found)
                DataUiState.Error.ErrorType.UNKNOWN -> stringResource(R.string.search_error_unknown)
            },
            textAlign = TextAlign.Center
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewSearchScreenContentError() {
    PokeSearchTheme {
        Surface {
            SearchScreenContentError(
                uiState = DataUiState.Error(errorType = DataUiState.Error.ErrorType.NOT_FOUND),
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSearchScreenContentErrorNetwork() {
    PokeSearchTheme {
        Surface {
            SearchScreenContentError(
                uiState = DataUiState.Error(errorType = DataUiState.Error.ErrorType.NETWORK),
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSearchScreenContentErrorUnknown() {
    PokeSearchTheme {
        Surface {
            SearchScreenContentError(
                uiState = DataUiState.Error(errorType = DataUiState.Error.ErrorType.UNKNOWN),
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}