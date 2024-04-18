package com.example.nestednavigationbottombardemo.mocks

import com.example.nestednavigationbottombardemo.database.RecipesDao
import com.example.nestednavigationbottombardemo.database.RecipesEntity

class MockDao : RecipesDao {

    var recipeEntities: MutableList<RecipesEntity> = mutableListOf()

    override suspend fun getAll(): List<RecipesEntity?>? {
        return recipeEntities;
    }

    override suspend fun insertAll(recipes: List<RecipesEntity>) {
        recipeEntities.addAll(recipes)
    }

    override suspend fun insert(recipe: RecipesEntity) {
        recipeEntities.add(recipe)
    }

    override suspend fun clearRecipes() {
        recipeEntities = mutableListOf();
    }
}