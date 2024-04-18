package com.example.nestednavigationbottombardemo.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nestednavigationbottombardemo.User
import com.example.nestednavigationbottombardemo.repository.UsersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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