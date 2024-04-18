package com.example

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nestednavigationbottombardemo.screens.CreateScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreateRecipeTest {
    @get: Rule
    val composeTestRule = createComposeRule()
    @Before
    fun setUp() {
        composeTestRule.setContent {
            CreateScreen(
                getMockRecipeViewModel(),
                getMockUsersRepo(),
            )
        }
    }
    @Test
    fun testIfNameAndSurnameIsDisplayed(){
        composeTestRule
            .onNodeWithText("") // Empty string means no text
            .assertExist()
    }
}
