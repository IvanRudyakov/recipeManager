package com.example.nestednavigationbottombardemo.mocks

import com.example.nestednavigationbottombardemo.viewModels.RecipeViewModel
import com.example.nestednavigationbottombardemo.viewModels.UsersViewModel

fun getMockRecipeViewModel(isOnline: Boolean = true): RecipeViewModel {
    val recipeViewModel = RecipeViewModel()
    recipeViewModel.setRecipesRepository(getMockRecipesRepo(isOnline))
    return recipeViewModel
}

fun getMockUserViewModel(isOnline: Boolean = true): UsersViewModel {
    val usersViewModel = UsersViewModel()
    usersViewModel.setUsersRepository(getMockUsersRepo(isOnline))
    return usersViewModel
}