package com.example.nestednavigationbottombardemo.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nestednavigationbottombardemo.User
import com.example.nestednavigationbottombardemo.api.RetrofitClient
import com.example.nestednavigationbottombardemo.database.DatabaseProvider
import com.example.nestednavigationbottombardemo.repository.RecipesRepository
import com.example.nestednavigationbottombardemo.repository.UsersRepository
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class UsersViewModel() : ViewModel() {
    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _userList = MutableStateFlow<List<User>>(listOf())
    val userList: StateFlow<List<User>> = _userList

    private val _errors = MutableStateFlow<String?>(null)
    val errors: StateFlow<String?> = _errors

    lateinit var user: String;

    private lateinit var usersRepository: UsersRepository;
    fun setUsersRepository(usersRepository: UsersRepository) {
        this.usersRepository = usersRepository
    }

    fun refresh() {
        Log.d("aaa", "REFRESH RECIPES");
        _isLoading.value = true;
        viewModelScope.launch {
            try {
                _userList.value = usersRepository.getUsers()
            }
            catch (e: Exception) {
                _userList.value = listOf();
                _errors.value = e.message ?: "";
            }
            _isLoading.value = false;
        }
    }
}