package com.example.nestednavigationbottombardemo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nestednavigationbottombardemo.mocks.getMockRecipeViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecipesViewModelTest {

    private val underTest = getMockRecipeViewModel()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }
    @Test
    fun tesRefresh() = runTest {

        val liveData = underTest.recipeList
        underTest.refresh()

        val value = liveData.value
        assertEquals(
            value, listOf(
                Recipe("Chicken Curry", "Alice", "Chicken, Curry", "1. Cook chicken 2. Add curry"),
                Recipe("Pasta", "Alice", "Pasta", "1. Boil pasta")
            )
        )
    }

}