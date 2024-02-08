package com.pokesearch.ui.screen

import com.pokesearch.data.PokemonRepository
import com.pokesearch.data.commons.DataResult
import com.pokesearch.data.model.Pokemon
import com.pokesearch.data.model.PokemonType
import com.pokesearch.ui.screen.search.DataUiState
import com.pokesearch.ui.screen.search.SearchUiState
import com.pokesearch.ui.screen.search.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val pokemonRepository = mock<PokemonRepository>()

    private val stubPokemon = Pokemon(
        id = 25,
        name = "pikachu",
        heightDecimeters = 3f,
        weightHectoGrams = 30f,
        types = listOf(
            PokemonType("electric")
        ),
        imageUrl = "ww.pokemon.image",
        imageGifUrl = "ww.pokemon.gif"
    )

    @Test
    fun `initial state is empty and loading`() = runTest {
        val viewModel = createViewModel()

        Assertions.assertThat(viewModel.uiState.value).isEqualTo(
            SearchUiState(
                query = "",
                dataUiState = DataUiState.Empty
            )
        )
    }

    @Test
    fun `when calling search and pokemon exists, updates state with data`() =
        runTest {
            val viewModel = createViewModel()

            whenever(pokemonRepository.getPokemon("pikachu")).thenReturn(
                DataResult.Success(stubPokemon)
            )

            viewModel.search("pikachu")
            advanceUntilIdle()

            Assertions.assertThat(viewModel.uiState.value).isEqualTo(
                SearchUiState(
                    query = "pikachu",
                    dataUiState = DataUiState.Data(stubPokemon)
                )
            )
        }

    @Test
    fun `when calling search with empty, updates state with empty data`() =
        runTest {
            val viewModel = createViewModel()


            whenever(pokemonRepository.getPokemon("pikachu")).thenReturn(
                DataResult.Success(stubPokemon)
            )

            viewModel.search("pikachu")
            advanceUntilIdle()
            viewModel.search("")
            advanceUntilIdle()

            Assertions.assertThat(viewModel.uiState.value).isEqualTo(
                SearchUiState(
                    query = "",
                    dataUiState = DataUiState.Empty
                )
            )
        }

    @Test
    fun `when repo returns network error, updates state with network error data`() =
        runTest {
            val viewModel = createViewModel()

            whenever(pokemonRepository.getPokemon("pikachu")).thenReturn(
                DataResult.Error(errorType = DataResult.ErrorType.NETWORK)
            )

            viewModel.search("pikachu")
            advanceUntilIdle()

            Assertions.assertThat(viewModel.uiState.value).isEqualTo(
                SearchUiState(
                    query = "pikachu",
                    dataUiState = DataUiState.Error(errorType = DataUiState.Error.ErrorType.NETWORK)
                )
            )
        }

    @Test
    fun `when repo returns unknown error, updates state with unknown error data`() =
        runTest {
            val viewModel = createViewModel()

            whenever(pokemonRepository.getPokemon("pikachu")).thenReturn(
                DataResult.Error(errorType = DataResult.ErrorType.UNKNOWN)
            )

            viewModel.search("pikachu")
            advanceUntilIdle()

            Assertions.assertThat(viewModel.uiState.value).isEqualTo(
                SearchUiState(
                    query = "pikachu",
                    dataUiState = DataUiState.Error(errorType = DataUiState.Error.ErrorType.UNKNOWN)
                )
            )
        }

    @Test
    fun `when repo returns not found error, updates state with not found error data`() =
        runTest {
            val viewModel = createViewModel()

            whenever(pokemonRepository.getPokemon("pikachu")).thenReturn(
                DataResult.Error(errorType = DataResult.ErrorType.NOT_FOUND)
            )

            viewModel.search("pikachu")
            advanceUntilIdle()

            Assertions.assertThat(viewModel.uiState.value).isEqualTo(
                SearchUiState(
                    query = "pikachu",
                    dataUiState = DataUiState.Error(errorType = DataUiState.Error.ErrorType.NOT_FOUND)
                )
            )
        }

    @Test
    fun `when calling search twice with same query, does not call repo twice`() =
        runTest {
            val viewModel = createViewModel()

            whenever(pokemonRepository.getPokemon("pikachu")).thenReturn(
                DataResult.Error(errorType = DataResult.ErrorType.NETWORK)
            )

            viewModel.search("pikachu")
            advanceUntilIdle()
            viewModel.search("pikachu")
            advanceUntilIdle()

            verify(pokemonRepository, times(1)).getPokemon("pikachu")
        }

    @Test
    fun `when calling search, updates query and set loading state, then updates with repo data`() =
        runTest {
            val viewModel = createViewModel()
            val searchUiStates = mutableListOf<SearchUiState>()
            whenever(pokemonRepository.getPokemon("pikachu")).thenReturn(
                DataResult.Success(stubPokemon)
            )
            whenever(pokemonRepository.getPokemon("pikach")).thenReturn(
                DataResult.Error(DataResult.ErrorType.NOT_FOUND)
            )

            // collect all the states
            val job =
                backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                    viewModel.uiState.toList(searchUiStates)
                }

            viewModel.search("pikachu")
            advanceUntilIdle()
            viewModel.search("pikach")
            advanceUntilIdle()

            Assertions.assertThat(searchUiStates).isEqualTo(
                listOf(
                    SearchUiState("", DataUiState.Empty),
                    SearchUiState("pikachu", DataUiState.Empty),
                    SearchUiState("pikachu", DataUiState.Loading),
                    SearchUiState("pikachu", DataUiState.Data(stubPokemon)),
                    SearchUiState("pikach", DataUiState.Data(stubPokemon)),
                    SearchUiState("pikach", DataUiState.Loading),
                    SearchUiState(
                        "pikach",
                        DataUiState.Error(DataUiState.Error.ErrorType.NOT_FOUND)
                    ),
                )

            )

            job.cancel()
        }

    private fun TestScope.createViewModel(): SearchViewModel {
        val dispatcher = StandardTestDispatcher(testScheduler)
        return SearchViewModel(
            pokemonRepository,
            dispatcher
        )
    }
}