package com.example.nestednavigationbottombardemo.repository

import com.example.nestednavigationbottombardemo.User

interface UsersRepository {
    suspend fun getUsers(): List<User>
}