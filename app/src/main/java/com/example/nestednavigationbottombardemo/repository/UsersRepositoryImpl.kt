package com.example.nestednavigationbottombardemo.repository

import com.example.nestednavigationbottombardemo.User

class UsersRepositoryImpl(private val recipesRepository: RecipesRepository): UsersRepository {
    suspend override fun getUsers(): List<User> {
        val allRecipes = recipesRepository.getRecipes();
        val usersMap: MutableMap<String, Int> = mutableMapOf()
        for (recipe in allRecipes) {
            if (usersMap.containsKey(recipe.author)) {
                usersMap[recipe.author] = usersMap[recipe.author]!! + 1;
            }
            else {
                usersMap[recipe.author] = 1;
            }
        }
        val users: MutableList<User> = mutableListOf();
        for (username in usersMap.keys) {
            users.add(User(username, usersMap[username] ?: 0))
        }
        return users
    }
}