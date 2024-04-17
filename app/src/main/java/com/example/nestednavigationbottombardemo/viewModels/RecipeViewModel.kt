package com.example.nestednavigationbottombardemo.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nestednavigationbottombardemo.Recipe
import com.example.nestednavigationbottombardemo.api.RecipeApi
import com.example.nestednavigationbottombardemo.api.RetrofitClient
import com.example.nestednavigationbottombardemo.database.DatabaseProvider
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _recipeList = MutableStateFlow<List<Recipe>>(listOf())
    val recipeList: StateFlow<List<Recipe>> = _recipeList

    private val _errors = MutableStateFlow<String?>(null)
    val errors: StateFlow<String?> = _errors

    fun refresh() {
        Log.d("aaa", "REFRESH RECIPES");
        val call = RetrofitClient.instance.fetchRecipes();
        _isLoading.value = true;
        call.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    Log.d("aaa", "SUCCESS RECIPES");
                    val data = response.body()
                    _isLoading.value = false;
                    val recipes = processData(data)
                    if (recipes.size == 0) {
                        _recipeList.value = listOf()
                        _errors.value = "No Recipes Available"
                    } else {
                        setdb(recipes)
                        _recipeList.value = recipes
                        _errors.value = null;
                    }
                } else {
                    Log.d("aaa", "UNKNOWN ERROR");
                    _isLoading.value = false;
                    _errors.value = "Unknown Error";
                    _recipeList.value = listOf();
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d("aaa", "FAILURE RECIPES");
                Log.d("aaa", t?.message ?: "");
                _isLoading.value = false;

                // Assuming user is not connected to internet.
                setRecipesFromDb();
            }
        })
    }

    fun addRecipe(recipe: Recipe) {
        val call = RetrofitClient.instance.postRecipe(RecipeApi(
            recipe.name,
            listOf(recipe.author),
            recipe.ingredients,
            recipe.steps
        ));
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                val x = 3;
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                val y = 7;
            }
        });
    }

    private fun processData(json: JsonElement?): List<Recipe> {
        val listType = object : TypeToken<List<RecipeApi>>() {}.type
        val recipes: List<RecipeApi> = Gson().fromJson(json, listType)
        return recipes.map {r -> Recipe(r.name, r.authors.get(0), r.ingredients, r.steps)}; //filterProducts(products)
    }

    private fun setRecipesFromDb() {
        viewModelScope.launch {
            val recipes = DatabaseProvider.getRecipesDatabase()?.recipesDao()?.getAll();
            if (recipes != null && recipes.size > 0) {
                _recipeList.value = recipes.map {
                        r -> Recipe(r?.title ?: "", r?.author ?: "", r?.ingredients ?: "", r?.steps ?: "")
                }
                _errors.value = null;
            }
            else {
                _errors.value = "Not connected to internet";
            }
        }
    }

    private fun setdb(recipes: List<Recipe>) {
        val dao = DatabaseProvider.getRecipesDatabase()?.recipesDao()
        if (dao != null) {
            viewModelScope.launch {
                dao.clearAndInsert(recipes)
            }
        }
    }
}
