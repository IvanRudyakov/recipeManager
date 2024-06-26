package com.example.nestednavigationbottombardemo.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bottomnavbardemo.screens.home.WholeScreen
import com.example.nestednavigationbottombardemo.viewModels.RecipeViewModel
import com.example.nestednavigationbottombardemo.viewModels.UsersViewModel

@Composable
fun RootNavigationGraph(navController: NavHostController, usersViewModel: UsersViewModel, recipeViewModel: RecipeViewModel) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.AUTHENTICATION
    ) {
        authNavGraph(
            navController = navController,
            usersViewModel = usersViewModel,
            recipeViewModel = recipeViewModel
        )
        composable(route = Graph.HOME) {
            WholeScreen(
                usersViewModel = usersViewModel,
                recipeViewModel = recipeViewModel
            )
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
    const val AUTHENTICATION = "auth_graph"
    const val HOME = "home_graph"
    const val DETAILS = "details_graph"
}