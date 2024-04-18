package com.example.nestednavigationbottombardemo.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nestednavigationbottombardemo.Recipe
import com.example.nestednavigationbottombardemo.repository.RecipesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class RecipeViewModel() : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _recipeList = MutableStateFlow<List<Recipe>>(listOf())
    val recipeList: StateFlow<List<Recipe>> = _recipeList

    private val _errors = MutableStateFlow<String?>(null)
    val errors: StateFlow<String?> = _errors

    private lateinit var recipesRepository: RecipesRepository;
    fun setRecipesRepository(recipesRepository: RecipesRepository) {
        this.recipesRepository = recipesRepository
    }

    fun refresh() {
        _isLoading.value = true;
        viewModelScope.launch {
            try {
                _recipeList.value = recipesRepository.getRecipes()
            }
            catch (e: Exception) {
                _recipeList.value = listOf();
                _errors.value = e.message ?: "";
            }
            _isLoading.value = false;
        }
    }

    fun addRecipe(recipe: Recipe) {
        viewModelScope.launch {
            recipesRepository.addRecipe(recipe)
        }
    }
}
