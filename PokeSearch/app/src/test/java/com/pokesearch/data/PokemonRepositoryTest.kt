package com.pokesearch.data

import com.pokesearch.data.commons.DataResult
import com.pokesearch.data.model.Pokemon
import com.pokesearch.data.model.PokemonType
import com.pokesearch.data.remote.PokeApiService
import com.pokesearch.data.remote.model.RemoteOrderedPokemonType
import com.pokesearch.data.remote.model.RemotePokemon
import com.pokesearch.data.remote.model.RemotePokemonSprites
import com.pokesearch.data.remote.model.RemotePokemonType
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.io.IOException

class PokemonRepositoryTest {

    private val pokeApiService = mock<PokeApiService>()
    private val pokemonRepository = PokemonRepository(pokeApiService)

    @Test
    fun testGetPokemonFoundReturnsPokemon() = runTest {
        whenever(
            pokeApiService.getPokemon(idOrName = "bulbasaur")
        ).thenReturn(
            Response.success(
                RemotePokemon(
                    id = 1,
                    name = "bulbasaur",
                    heightDecimeters = 7f,
                    weightHectoGrams = 69f,
                    types = listOf(
                        RemoteOrderedPokemonType(
                            slot = 2,
                            RemotePokemonType(name = "poison")
                        ),
                        RemoteOrderedPokemonType(
                            slot = 1,
                            RemotePokemonType(name = "grass")
                        )
                    ),
                    sprites = RemotePokemonSprites(
                        frontDefaultUrl = "randomUrl/1.png",
                        other = RemotePokemonSprites.Other(
                            showdown = RemotePokemonSprites.Other.Showdown(
                                frontDefaultUrl = "randomUrl/1.gif"
                            )
                        )
                    )
                )
            )
        )

        val result = pokemonRepository.getPokemon("bulbasaur")

        assertThat(result).isEqualTo(
            DataResult.Success(
                Pokemon(
                    id = 1,
                    name = "bulbasaur",
                    heightDecimeters = 7f,
                    weightHectoGrams = 69f,
                    types = listOf(
                        PokemonType("grass"),
                        PokemonType("poison")
                    ),
                    imageUrl = "randomUrl/1.png",
                    imageGifUrl = "randomUrl/1.gif"
                )
            )
        )

    }

    @Test
    fun testGetPokemonNotFoundReturnsNotFoundError() = runTest {
        val mockResponse = mock<Response<RemotePokemon>> {
            on { body() } doReturn null
            on { code() } doReturn 404
        }
        whenever(
            pokeApiService.getPokemon(idOrName = "bulbasaur")
        ).thenReturn(mockResponse)

        val result = pokemonRepository.getPokemon("bulbasaur")

        assertThat(result).isEqualTo(
            DataResult.Error(
                errorType = DataResult.ErrorType.NOT_FOUND
            )
        )
    }

    @Test
    fun testGetPokemonOtherHttpErrorCodeReturnsUnknownError() = runTest {
        val mockResponse = mock<Response<RemotePokemon>> {
            on { body() } doReturn null
            on { code() } doReturn 401
        }
        whenever(
            pokeApiService.getPokemon(idOrName = "bulbasaur")
        ).thenReturn(mockResponse)

        val result = pokemonRepository.getPokemon(idOrName = "bulbasaur")

        assertThat(result).isEqualTo(
            DataResult.Error(
                errorType = DataResult.ErrorType.UNKNOWN
            )
        )
    }

    @Test
    fun testGetPokemonIOExceptionReturnsNetwork() = runTest {
        whenever(pokeApiService.getPokemon(idOrName = "bulbasaur")).thenAnswer {
            throw IOException(
                "cannot reach server"
            )
        }

        val result = pokemonRepository.getPokemon(idOrName = "bulbasaur")

        assertThat(result).isEqualTo(
            DataResult.Error(
                errorType = DataResult.ErrorType.NETWORK
            )
        )
    }

}