package com.example.nestednavigationbottombardemo.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nestednavigationbottombardemo.viewModels.RecipeViewModel
import com.example.nestednavigationbottombardemo.screens.LoginContent
import com.example.nestednavigationbottombardemo.viewModels.UsersViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController, recipeViewModel: RecipeViewModel, usersViewModel: UsersViewModel) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {

            LoginContent(
                recipeViewModel = recipeViewModel,
                usersViewModel = usersViewModel,
                onClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                }
            )
        }

    }
}

sealed class AuthScreen(val route: String) {
    object Login : AuthScreen(route = "LOGIN")
    object SignUp : AuthScreen(route = "SIGN_UP")
    object Forgot : AuthScreen(route = "FORGOT")
}