package com.example.nestednavigationbottombardemo

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

class RecipeViewModel : ViewModel() {
    // Shared recipes list
    val recipes = mutableStateListOf(
        Recipe("Tomato Soup", listOf("John Doe"), "Tomatoes, Basil, Salt", "Blend and cook for 20 mins"),
        Recipe("Grilled Cheese Sandwich", listOf("Jane Doe", "Jim Beam"), "Bread, Cheese, Butter", "Assemble and grill"),
        Recipe("Chocolate Cake", listOf("Julia Child"), "Flour, Cocoa, Sugar, Eggs", "Mix and bake for 30 mins")
    )

    // State for managing user login
    val username = mutableStateOf("")

    fun addRecipe(recipe: Recipe) {
        recipes.add(recipe)
    }

    fun removeRecipe(recipe: Recipe) {
        recipes.remove(recipe)
    }

    fun loginUser(user: String) {
        username.value = user
    }
}
