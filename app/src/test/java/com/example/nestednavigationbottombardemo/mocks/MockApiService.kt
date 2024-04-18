package com.example.nestednavigationbottombardemo.mocks

import com.example.nestednavigationbottombardemo.api.ApiService
import com.example.nestednavigationbottombardemo.api.RecipeApi
import com.example.nestednavigationbottombardemo.database.RecipesEntity
import com.google.gson.JsonElement
import com.google.gson.JsonParser

class MockApiService : ApiService {

    override suspend fun fetchRecipes(): JsonElement {
        return JsonParser().parse("[\n" +
                "    {\n" +
                "        \"id\": \"1\",\n" +
                "        \"name\": \"Chicken Curry\",\n" +
                "        \"authors\": [\n" +
                "            \"Alice\",\n" +
                "            \"Bob\"\n" +
                "        ],\n" +
                "        \"ingredients\": \"Chicken, Curry\",\n" +
                "        \"steps\": \"1. Cook chicken 2. Add curry\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"2\",\n" +
                "        \"name\": \"Pasta\",\n" +
                "        \"authors\": [\n" +
                "            \"Alice\"\n" +
                "        ],\n" +
                "        \"ingredients\": \"Pasta\",\n" +
                "        \"steps\": \"1. Boil pasta\"\n" +
                "    }\n" +
                "]")
    }

    override suspend fun postRecipe(recipe: RecipeApi) {
        //TODO("Not yet implemented")
    }

}