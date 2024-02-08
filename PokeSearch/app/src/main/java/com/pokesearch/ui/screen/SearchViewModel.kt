package com.pokesearch.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokesearch.data.PokemonRepository
import com.pokesearch.data.commons.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    coroutineDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState: MutableStateFlow<SearchUiState> =
        MutableStateFlow(
            SearchUiState(
                query = "",
                dataUiState = DataUiState.Empty
            )
        )
    val uiState: StateFlow<SearchUiState> = _uiState

    private val searchQueryState = MutableStateFlow("")

    init {
        viewModelScope.launch(coroutineDispatcher) {
            searchQueryState.onEach {
                _uiState.update {
                    it.copy(dataUiState = DataUiState.Loading)
                }
            }.map { query ->
                if (query.isEmpty()) {
                    DataUiState.Empty
                } else {
                    pokemonRepository.getPokemon(query).toDataUiState()
                }
            }.collect { uiState ->
                _uiState.update {
                    it.copy(dataUiState = uiState)
                }
            }
        }
    }

    fun search(query: String) {
        _uiState.update { it.copy(query = query) }
        searchQueryState.value = query
    }
}

private fun <T : Any> DataResult<T>.toDataUiState(): DataUiState<T> {
    return when (this) {
        is DataResult.Error -> {
            DataUiState.Error(
                when (this.errorType) {
                    DataResult.ErrorType.NOT_FOUND -> DataUiState.Error.ErrorType.NOT_FOUND
                    DataResult.ErrorType.NETWORK -> DataUiState.Error.ErrorType.NETWORK
                    DataResult.ErrorType.UNKNOWN -> DataUiState.Error.ErrorType.UNKNOWN
                }
            )
        }

        is DataResult.Success -> DataUiState.Data(this.data)
    }
}
