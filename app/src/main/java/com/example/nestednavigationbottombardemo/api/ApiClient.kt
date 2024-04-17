package com.example.nestednavigationbottombardemo.api

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("prod/random")
    fun fetchRecipes(): Call<JsonElement>

    @GET("prod/random")
    fun fetchUsers(): Call<JsonElement>

    @POST("prod/random")
    fun postRecipe(recipe: RecipeApi): Call<Void>

}