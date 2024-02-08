package com.pokesearch.ui.screen.search

import com.pokesearch.data.model.Pokemon

data class SearchUiState(
    val query: String,
    val dataUiState: DataUiState<Pokemon>
)

sealed class DataUiState<out T: Any> {
    data object Empty : DataUiState<Nothing>()
    data class Data<out T: Any>(val data: T) : DataUiState<T>()
    data object Loading : DataUiState<Nothing>()
    data class Error(val errorType: ErrorType) : DataUiState<Nothing>() {

        enum class ErrorType {
            NETWORK, NOT_FOUND, UNKNOWN
        }
    }
}
