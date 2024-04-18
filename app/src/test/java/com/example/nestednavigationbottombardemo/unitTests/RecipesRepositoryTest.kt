package com.example.nestednavigationbottombardemo.unitTests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nestednavigationbottombardemo.Recipe
import com.example.nestednavigationbottombardemo.mocks.getMockRecipesRepo
import junit.framework.Assert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RecipesRepositoryTest {

    private val underTestOnline = getMockRecipesRepo(isOnline = true);
    private val underTestOffline = getMockRecipesRepo(isOnline = false);

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }
    @Test
    fun testGetRecipesOnline() = runTest {

        val recipes = underTestOnline.getRecipes();

        Assert.assertEquals(
            recipes, listOf(
                Recipe("Chicken Curry", "Alice", "Chicken, Curry", "1. Cook chicken 2. Add curry"),
                Recipe("Pasta", "Alice", "Pasta", "1. Boil pasta")
            )
        )
    }

    @Test
    fun testGetRecipesOffline() = runTest {

        val recipes = underTestOffline.getRecipes();

        Assert.assertEquals(
            recipes, listOf(
                Recipe("title", "Sam", "ingredient1", "steps....")
            )
        )
    }

    @Test
    fun testAddRecipeOnline() = runTest {

        underTestOnline.addRecipe(Recipe("Pasta2", "Alice", "Pasta", "1. Boil pasta"));
        val recipes = underTestOnline.getRecipes();

        Assert.assertEquals(
            recipes, listOf(
                Recipe("Chicken Curry", "Alice", "Chicken, Curry", "1. Cook chicken 2. Add curry"),
                Recipe("Pasta", "Alice", "Pasta", "1. Boil pasta"),
                Recipe("Pasta2", "Alice", "Pasta", "1. Boil pasta")
            )
        )
    }

    @Test
    fun testAddRecipeOffline() = runTest {

        underTestOffline.addRecipe(Recipe("Pasta2", "Alice", "Pasta", "1. Boil pasta"));
        val recipes = underTestOffline.getRecipes();

        Assert.assertEquals(
            recipes, listOf(
                Recipe("title", "Sam", "ingredient1", "steps....")
            )
        )
    }

}