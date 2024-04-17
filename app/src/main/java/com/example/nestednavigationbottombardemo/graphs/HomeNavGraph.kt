package com.example.nestednavigationbottombardemo.graphs

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nestednavigationbottombardemo.BottomBarScreen
import com.example.nestednavigationbottombardemo.RecipeViewModel
import com.example.nestednavigationbottombardemo.screens.ScreenContent

@Composable
fun HomeNavGraph(navController: NavHostController, recipeViewModel: RecipeViewModel = viewModel()) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            ScreenContent(
                name = BottomBarScreen.Home.route,
                recipeViewModel = recipeViewModel,
                onClick = { navController.navigate(Graph.DETAILS) }
            )
        }
        composable(route = BottomBarScreen.Profile.route) {
            ScreenContent(
                name = BottomBarScreen.Profile.route,
                recipeViewModel = recipeViewModel,
                onClick = { /* Handle onClick if needed */ }
            )
        }
        composable(route = BottomBarScreen.Create.route) {
            ScreenContent(
                name = BottomBarScreen.Create.route,
                recipeViewModel = recipeViewModel,
                onClick = { /* Handle onClick if needed */ }
            )
        }
        composable(route = BottomBarScreen.Users.route) {
            ScreenContent(
                name = BottomBarScreen.Users.route,
                recipeViewModel = recipeViewModel,
                onClick = { /* Handle onClick if needed */ }
            )
        }
        detailsNavGraph(navController, recipeViewModel)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController, recipeViewModel: RecipeViewModel) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route
    ) {
        composable(route = DetailsScreen.Information.route) {
            ScreenContent(
                name = DetailsScreen.Information.route,
                recipeViewModel = recipeViewModel,
                onClick = { navController.navigate(DetailsScreen.Overview.route) }
            )
        }
        composable(route = DetailsScreen.Overview.route) {
            ScreenContent(
                name = DetailsScreen.Overview.route,
                recipeViewModel = recipeViewModel,
                onClick = { navController.popBackStack() }
            )
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen(route = "INFORMATION")
    object Overview : DetailsScreen(route = "OVERVIEW")
}
