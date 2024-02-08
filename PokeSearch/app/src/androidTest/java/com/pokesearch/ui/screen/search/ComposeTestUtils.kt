package com.pokesearch.ui.screen.search

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText

interface BaseComposeTest {
    abstract val composeTestRule: ComposeTestRule
}

fun BaseComposeTest.assertTextDisplayed(text: String): SemanticsNodeInteraction {
    return composeTestRule.onNodeWithText(text).assertIsDisplayed()
}

fun BaseComposeTest.assertTagDisplayed(tag: String): SemanticsNodeInteraction {
    return composeTestRule.onNodeWithTag(tag).assertIsDisplayed()
}

fun BaseComposeTest.assertContentDescDisplayed(contentDesc: String): SemanticsNodeInteraction {
    return composeTestRule.onNodeWithContentDescription(contentDesc)
        .assertIsDisplayed()
}