package com.example.memomiles.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * HomeScreenTest.kt
 *
 * Instrumented UI tests for the HomeScreen.
 *
 * Scope of testing:
 * Ensure that UI elements are displayed correctly
 * Ensure that buttons exist and are clickable
 */
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupHomeScreen() {
        composeTestRule.setContent {
            // Fake NavController for UI interaction testing
            navController = TestNavHostController(composeTestRule.activity)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            // Render HomeScreen only
            HomeScreen(navController = navController)
        }
    }

    // UI VISIBILITY TESTS

    @Test
    fun teddyImage_isDisplayed() {
        composeTestRule
            .onNodeWithContentDescription("Teddy mascot holding a pencil")
            .assertIsDisplayed()
    }

    @Test
    fun welcomeText_appearsAfterAnimation() {
        composeTestRule.waitUntil(timeoutMillis = 1500) {
            composeTestRule
                .onAllNodesWithText("Welcome to MemoMiles!")
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule
            .onNodeWithText("Welcome to MemoMiles!")
            .assertIsDisplayed()
    }

    @Test
    fun settingsButton_existsAndIsClickable() {
        composeTestRule
            .onNodeWithContentDescription("Settings")
            .assertExists()
            .assertHasClickAction()
    }


    // BUTTON INTERACTION TESTS

    @Test
    fun personalEntryButton_isClickable() {
        composeTestRule
            .onNodeWithText("✏️ New Personal Entry")
            .assertExists()
            .assertHasClickAction()
            .performClick()
    }

    @Test
    fun travelEntryButton_isClickable() {
        composeTestRule
            .onNodeWithText("🌍 New Travel Entry")
            .assertExists()
            .assertHasClickAction()
            .performClick()
    }

    @Test
    fun archiveButton_isClickable() {
        composeTestRule
            .onNodeWithText("📚 View Archive")
            .assertExists()
            .assertHasClickAction()
            .performClick()
    }
}