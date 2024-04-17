package com.example.nestednavigationbottombardemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.nestednavigationbottombardemo.database.DatabaseProvider
import com.example.nestednavigationbottombardemo.graphs.RootNavigationGraph
import com.example.nestednavigationbottombardemo.ui.theme.NestedNavigationBottomBarDemoTheme
import com.example.nestednavigationbottombardemo.viewModels.RecipeViewModel
import com.example.nestednavigationbottombardemo.viewModels.UsersViewModel

class MainActivity : ComponentActivity() {

    private val recipeViewModel: RecipeViewModel by viewModels()
    private val usersViewModel: UsersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseProvider.setContext(this)
        setContent {
            NestedNavigationBottomBarDemoTheme {
                RootNavigationGraph(
                    navController = rememberNavController(),
                    recipeViewModel = recipeViewModel,
                    usersViewModel = usersViewModel
                )
            }
        }
    }
}