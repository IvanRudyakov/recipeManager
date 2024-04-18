package com.example.nestednavigationbottombardemo.repository

import com.example.nestednavigationbottombardemo.Recipe

interface RecipesRepository {
    suspend fun getRecipes(): List<Recipe>

    suspend fun addRecipe(recipe: Recipe)
}