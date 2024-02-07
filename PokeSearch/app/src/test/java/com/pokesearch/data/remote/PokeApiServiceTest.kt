package com.pokesearch.data.remote

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.pokesearch.data.remote.model.RemoteOrderedPokemonType
import com.pokesearch.data.remote.model.RemotePokemon
import com.pokesearch.data.remote.model.RemotePokemonSprites
import com.pokesearch.data.remote.model.RemotePokemonType
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okhttp3.mockwebserver.SocketPolicy
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class PokeApiServiceTest {

    private lateinit var retrofitService: PokeApiService
    private val mockWebServer = MockWebServer()

    @Before
    fun before() {
        retrofitService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/api/v2/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(PokeApiService::class.java)
    }

    @After
    fun after() {
        mockWebServer.shutdown()
    }

    @Test
    fun `test getPokemon returns parsed models`() = runTest {
        val mockResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(
                """
                {
                  "height": 7,
                  "id": 1,
                  "name": "bulbasaur",
                  "sprites": {
                    "front_default": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                    "front_female": null,
                    "front_shiny": "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/1.png",
                    "front_shiny_female": null
                  },
                  "types": [
                    {
                      "slot": 1,
                      "type": {
                        "name": "grass",
                        "url": "https://pokeapi.co/api/v2/type/12/"
                      }
                    },
                    {
                      "slot": 2,
                      "type": {
                        "name": "poison",
                        "url": "https://pokeapi.co/api/v2/type/4/"
                      }
                    }
                  ],
                  "weight": 69
                }
            """.trimIndent()
            )
        mockWebServer.enqueue(mockResponse)

        val response =
            retrofitService.getPokemon("1")

        val request: RecordedRequest = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo("/api/v2/pokemon/1")
        assertThat(request.body.readUtf8()).isEmpty()
        assertThat(response.body()).isEqualTo(
            RemotePokemon(
                id = 1,
                name = "bulbasaur",
                heightDecimeters = 7f,
                weightHectoGrams = 69f,
                types = listOf(
                    RemoteOrderedPokemonType(
                        slot = 1,
                        RemotePokemonType(name = "grass")
                    ),
                    RemoteOrderedPokemonType(
                        slot = 2,
                        RemotePokemonType(name = "poison")
                    )
                ),
                sprites = RemotePokemonSprites(frontDefaultUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png")
            )
        )
        assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_OK)
    }

    @Test
    fun testNotFoundPokemonReturns404() = runTest {
        val mockResponse =
            MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockWebServer.enqueue(mockResponse)

        val response = retrofitService.getPokemon(idOrName = "1")

        assertThat(response.body()).isNull()
        assertThat(response.code()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND)
    }

    @Test(expected = IOException::class)
    fun testGetPokemonNetworkErrorThrows() = runTest {
        mockWebServer.enqueue(MockResponse().setSocketPolicy(SocketPolicy.DISCONNECT_AT_START))
        retrofitService.getPokemon(idOrName = "1")
    }
}