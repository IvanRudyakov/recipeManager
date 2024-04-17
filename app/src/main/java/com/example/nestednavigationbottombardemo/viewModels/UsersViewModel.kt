package com.example.nestednavigationbottombardemo.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nestednavigationbottombardemo.Recipe
import com.example.nestednavigationbottombardemo.User
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

class UsersViewModel : ViewModel() {
    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _userList = MutableStateFlow<List<User>>(listOf())
    val userList: StateFlow<List<User>> = _userList

    private val _errors = MutableStateFlow<String?>(null)
    val errors: StateFlow<String?> = _errors

    fun refresh() {
        val call = RetrofitClient.instance.fetchRecipes();
        _isLoading.value = true;
        call.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    _isLoading.value = false;
                    val users = processData(data)
                    if (users.size == 0) {
                        _userList.value = listOf()
                        _errors.value = "No Users Available"
                    } else {
                        setdb(users)
                        _userList.value = users
                        _errors.value = null;
                    }
                } else {
                    _isLoading.value = false;
                    _errors.value = "Unknown Error";
                    _userList.value = listOf();
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                _isLoading.value = false;

                // Assuming user is not connected to internet.
                setUsersFromDb();
            }
        })
    }

    private fun processData(json: JsonElement?): List<User> {
        val listType = object : TypeToken<List<User>>() {}.type
        val users: List<User> = Gson().fromJson(json, listType)
        return users; //filterProducts(products)
    }

    private fun setUsersFromDb() {
        viewModelScope.launch {
            val users = DatabaseProvider.getUsersDatabase()?.usersDao()?.getAll();
            if (users != null && users.size > 0) {
                _userList.value = users.map {
                        u -> User(u?.username ?: "", u?.recipeCount ?: 0)
                }
                _errors.value = null;
            }
            else {
                _errors.value = "Not connected to internet";
            }
        }
    }

    private fun setdb(users: List<User>) {
        val dao = DatabaseProvider.getUsersDatabase()?.usersDao()
        if (dao != null) {
            viewModelScope.launch {
                dao.clearAndInsert(users)
            }
        }
    }
}