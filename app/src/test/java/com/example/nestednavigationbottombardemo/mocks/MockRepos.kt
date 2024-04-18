package com.example.nestednavigationbottombardemo.mocks

import com.example.nestednavigationbottombardemo.repository.RecipesRepository
import com.example.nestednavigationbottombardemo.repository.RecipesRepositoryImpl
import com.example.nestednavigationbottombardemo.repository.UsersRepository
import com.example.nestednavigationbottombardemo.repository.UsersRepositoryImpl

fun getMockRecipesRepo(isOnline: Boolean = true): RecipesRepository {
    return RecipesRepositoryImpl(MockApiService(), MockDao(), {isOnline})
}

fun getMockUsersRepo(isOnline: Boolean = true): UsersRepository {
    return UsersRepositoryImpl(getMockRecipesRepo(isOnline))
}