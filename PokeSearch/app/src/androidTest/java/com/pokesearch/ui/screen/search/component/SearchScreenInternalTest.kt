package com.pokesearch.ui.screen.search.component

import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import com.pokesearch.data.model.Pokemon
import com.pokesearch.data.model.PokemonType
import com.pokesearch.ui.screen.search.BaseComposeTest
import com.pokesearch.ui.screen.search.DataUiState
import com.pokesearch.ui.screen.search.SearchUiState
import com.pokesearch.ui.screen.search.assertContentDescDisplayed
import com.pokesearch.ui.screen.search.assertTagDisplayed
import com.pokesearch.ui.screen.search.assertTextDisplayed
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SearchScreenInternalTest: BaseComposeTest {

    @get:Rule
    override val composeTestRule = createComposeRule()

    private val onQueryChange = mock<(String) -> Unit>()

    @Test
    fun testEmptyState() {
        setContent(SearchUiState(query = "", dataUiState = DataUiState.Empty))

        assertTextDisplayed("Type Pokemon name or number").assertIsFocused()
        assertTextDisplayed("Type the Pokemon name or number to find it !")
    }

    @Test
    fun testLoadingState() {
        setContent(
            SearchUiState(
                query = "bulbasaur",
                dataUiState = DataUiState.Loading
            )
        )

        assertTextDisplayed("bulbasaur")
        assertTagDisplayed(LOADING_INDICATOR)
    }

    @Test
    fun testDataState() {
        setContent(
            SearchUiState(
                query = "bulbasaur", dataUiState = DataUiState.Data(
                    data = Pokemon(
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
                    )
                )
            )
        )

        assertTextDisplayed("bulbasaur")
        assertContentDescDisplayed("Image for bulbasaur")
        assertTextDisplayed("Bulbasaur")
        assertTextDisplayed("Height: 0.70 m")
        assertTextDisplayed("Weight: 6.90 kg")
        assertTextDisplayed("Types:")
        assertTextDisplayed("Grass")
        assertTextDisplayed("Poison")
    }

    @Test
    fun testErrorState() {
        setContent(
            SearchUiState(
                query = "bulbasaur",
                dataUiState = DataUiState.Error(errorType = DataUiState.Error.ErrorType.NOT_FOUND)
            )
        )

        assertTextDisplayed("bulbasaur")
        assertTextDisplayed("Pokemon not found")
    }

    @Test
    fun testSearchField() {
        setContent(
            SearchUiState(
                query = "bulbasaur",
                dataUiState = DataUiState.Empty
            )
        )

        // type text in search field
        composeTestRule.onNodeWithText("bulbasaur")
            .performTextReplacement("pikachu")
        // check the callback is called with the new text
        verify(onQueryChange).invoke("pikachu")

        // click on the "Clear" icon
        composeTestRule.onNodeWithContentDescription("Clear Search")
            .performClick()
        // check the callback is called with empty text
        verify(onQueryChange).invoke("")
    }

    private fun setContent(uiState: SearchUiState) {
        composeTestRule.setContent {
            SearchScreenInternal(
                uiState = uiState,
                onQueryChange = onQueryChange
            )
        }
    }
}