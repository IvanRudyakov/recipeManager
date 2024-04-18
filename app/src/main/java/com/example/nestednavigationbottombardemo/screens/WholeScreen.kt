package com.example.bottomnavbardemo.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nestednavigationbottombardemo.BottomBarScreen
import com.example.nestednavigationbottombardemo.graphs.HomeNavGraph
import com.example.nestednavigationbottombardemo.screens.CreateScreen
import com.example.nestednavigationbottombardemo.screens.ProfileScreen
import com.example.nestednavigationbottombardemo.screens.UsersScreen
import com.example.nestednavigationbottombardemo.viewModels.RecipeViewModel
import com.example.nestednavigationbottombardemo.viewModels.UsersViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WholeScreen(navController: NavHostController = rememberNavController(), usersViewModel: UsersViewModel, recipeViewModel: RecipeViewModel) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController, usersViewModel = usersViewModel, recipeViewModel = recipeViewModel) }
    ) {
        HomeNavGraph(navController = navController, usersViewModel, recipeViewModel)
    }
}

@Composable
fun BottomBar(navController: NavHostController, usersViewModel: UsersViewModel, recipeViewModel: RecipeViewModel) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Users,
        BottomBarScreen.Create,
        BottomBarScreen.Profile
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController,
                    usersViewModel = usersViewModel,
                    recipeViewModel = recipeViewModel
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController,
    usersViewModel: UsersViewModel,
    recipeViewModel: RecipeViewModel
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation Icon"
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            if (screen.route == "HOME" || screen.route == "PROFILE") {
                recipeViewModel.refresh()
            }
            if (screen.route == "USERS") {
                usersViewModel.refresh()
            }
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

@Composable
fun ScreenContent(name: String, usersViewModel: UsersViewModel, recipeViewModel: RecipeViewModel, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (name) {
            "HOME" -> com.example.nestednavigationbottombardemo.screens.HomeScreen(
                recipeViewModel,
                onClick
            )
            "USERS" -> UsersScreen(usersViewModel, onClick)
            "CREATE" -> CreateScreen(recipeViewModel, usersViewModel)
            "PROFILE" -> ProfileScreen(recipeViewModel, usersViewModel)
            else -> Text(
                modifier = Modifier.clickable { onClick() },
                text = name,
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}