package com.example.nestednavigationbottombardemo.repository

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewModelScope
import com.example.nestednavigationbottombardemo.Recipe
import com.example.nestednavigationbottombardemo.api.RecipeApi
import com.example.nestednavigationbottombardemo.api.RetrofitClient
import com.example.nestednavigationbottombardemo.database.DatabaseProvider
import com.example.nestednavigationbottombardemo.database.RecipesEntity
import com.example.nestednavigationbottombardemo.isOnline
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch

class RecipesRepository(private val context: Context) {

    suspend fun getRecipes(): List<Recipe> {
        if (true) {
            Log.d("aaa", "what");
            val response = RetrofitClient.instance.fetchRecipes();
            val listType = object : TypeToken<List<RecipeApi>>() {}.type
            val recipesApi: List<RecipeApi> = Gson().fromJson(response, listType)
            val recipes = recipesApi.map {r -> Recipe(r.name, r.authors.get(0), r.ingredients, r.steps)}
            Log.d("aaa", "what2");
            setdb(recipes)
            return recipes
        }
        else {
            return getRecipesFromDb()
        }
    }

    suspend fun addRecipe(recipe: Recipe) {
        RetrofitClient.instance.postRecipe(RecipeApi(
            recipe.name,
            listOf(recipe.author),
            recipe.ingredients,
            recipe.steps
        ));
        val dao = DatabaseProvider.getRecipesDatabase()?.recipesDao()
        val recipesEntity = RecipesEntity()
        recipesEntity.title = recipe.name;
        recipesEntity.author = recipe.author;
        recipesEntity.ingredients = recipe.ingredients;
        recipesEntity.steps = recipe.steps;
        if (dao != null) {
            dao.insert(recipesEntity)
        }
    }

    private suspend fun getRecipesFromDb(): List<Recipe> {
        val recipes = DatabaseProvider.getRecipesDatabase()?.recipesDao()?.getAll();
        return recipes?.map {
            r -> Recipe(r?.title ?: "", r?.author ?: "", r?.ingredients ?: "", r?.steps ?: "")
        } ?: listOf()
    }

    private suspend fun setdb(recipes: List<Recipe>) {
        val dao = DatabaseProvider.getRecipesDatabase()?.recipesDao()
        if (dao != null) {
            dao.clearAndInsert(recipes)
        }
    }
}