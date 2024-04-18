package com.example.nestednavigationbottombardemo.repository

import com.example.nestednavigationbottombardemo.Recipe
import com.example.nestednavigationbottombardemo.api.ApiService
import com.example.nestednavigationbottombardemo.api.RecipeApi
import com.example.nestednavigationbottombardemo.database.RecipesDao
import com.example.nestednavigationbottombardemo.database.RecipesEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesRepositoryImpl(val apiService: ApiService, val dao: RecipesDao, val isOnline: () -> Boolean): RecipesRepository {

    suspend override fun getRecipes(): List<Recipe> {
        if (isOnline()) {
            val response = apiService.fetchRecipes();
            val listType = object : TypeToken<List<RecipeApi>>() {}.type
            val recipesApi: List<RecipeApi> = Gson().fromJson(response, listType)
            val recipes = recipesApi.map {r -> Recipe(r.name, r.authors.get(0), r.ingredients, r.steps)}
            setdb(recipes)
            return recipes
        }
        else {
            return getRecipesFromDb()
        }
    }

    suspend override fun addRecipe(recipe: Recipe) {
        apiService.postRecipe(RecipeApi(
            recipe.name,
            listOf(recipe.author),
            recipe.ingredients,
            recipe.steps
        ));
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
        val recipes = dao.getAll();
        return recipes?.map {
            r -> Recipe(r?.title ?: "", r?.author ?: "", r?.ingredients ?: "", r?.steps ?: "")
        } ?: listOf()
    }

    private suspend fun setdb(recipes: List<Recipe>) {
        if (dao != null) {
            dao.clearAndInsert(recipes)
        }
    }
}