package com.pokesearch.ui.screen.search

import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextReplacement
import com.pokesearch.data.remote.PokeApiService
import com.pokesearch.data.remote.model.RemoteOrderedPokemonType
import com.pokesearch.data.remote.model.RemotePokemon
import com.pokesearch.data.remote.model.RemotePokemonSprites
import com.pokesearch.data.remote.model.RemotePokemonType
import com.pokesearch.di.RemoteDataModule
import com.pokesearch.ui.MainActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@HiltAndroidTest
@UninstallModules(RemoteDataModule::class)
class SearchIntegrationTest : BaseComposeTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    override val composeTestRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(PokeApiService.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val successResponse = Response.success(
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
            sprites = RemotePokemonSprites(
                frontDefaultUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                other = RemotePokemonSprites.Other(
                    showdown = RemotePokemonSprites.Other.Showdown(
                        frontDefaultUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/showdown/132.gif"
                    )
                )
            )
        )
    )

    private val failureResponse = Response.error<RemotePokemon>(
        404, "".toResponseBody()
    )

    @BindValue
    @JvmField
    val pokeApiService: PokeApiService = mock {
        onBlocking { getPokemon("bulbasaur") } doReturn successResponse
        onBlocking { getPokemon("bulb") } doReturn failureResponse
    }

    @Before
    fun before() {
        hiltRule.inject()
    }

    @Test
    fun testSearchSuccess() {
        assertTextDisplayed("Type Pokemon name or number").assertIsFocused()
        assertTextDisplayed("Type the Pokemon name or number to find it !")

        composeTestRule.onNodeWithText("Type Pokemon name or number")
            .performTextReplacement("bulbasaur")

        assertTextDisplayed("Bulbasaur")
        assertContentDescDisplayed("Image for bulbasaur")
        assertTextDisplayed("Bulbasaur")
        assertTextDisplayed("Height: 0.70 m")
        assertTextDisplayed("Weight: 6.90 kg")
        assertTextDisplayed("Types:")
        assertTextDisplayed("Grass")
        assertTextDisplayed("Poison")
    }

    @Test
    fun testSearchFailure() {
        assertTextDisplayed("Type Pokemon name or number").assertIsFocused()
        assertTextDisplayed("Type the Pokemon name or number to find it !")

        composeTestRule.onNodeWithText("Type Pokemon name or number")
            .performTextReplacement("bulb")

        assertTextDisplayed("Pokemon not found")
    }


}