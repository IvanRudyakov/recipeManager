package com.example.nestednavigationbottombardemo.api

import com.example.nestednavigationbottombardemo.Recipe
import com.example.nestednavigationbottombardemo.User
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("recipes")
    suspend fun fetchRecipes(): JsonElement;

    @POST("recipes")
    suspend fun postRecipe(@Body recipe: RecipeApi)

}