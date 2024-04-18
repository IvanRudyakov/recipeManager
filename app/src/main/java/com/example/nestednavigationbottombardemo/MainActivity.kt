package com.example.nestednavigationbottombardemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.nestednavigationbottombardemo.api.RetrofitClient
import com.example.nestednavigationbottombardemo.database.DatabaseProvider
import com.example.nestednavigationbottombardemo.graphs.RootNavigationGraph
import com.example.nestednavigationbottombardemo.repository.RecipesRepositoryImpl
import com.example.nestednavigationbottombardemo.repository.UsersRepositoryImpl
import com.example.nestednavigationbottombardemo.ui.theme.NestedNavigationBottomBarDemoTheme
import com.example.nestednavigationbottombardemo.viewModels.RecipeViewModel
import com.example.nestednavigationbottombardemo.viewModels.UsersViewModel

class MainActivity : ComponentActivity() {

    private val recipeViewModel: RecipeViewModel by viewModels()
    private val usersViewModel: UsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseProvider.setContext(this)
        val recipesRepository = RecipesRepositoryImpl(RetrofitClient.instance, DatabaseProvider.getRecipesDatabase()!!.recipesDao()!!, {isOnline(this)})
        val usersRepository = UsersRepositoryImpl(recipesRepository)
        recipeViewModel.setRecipesRepository(recipesRepository)
        usersViewModel.setUsersRepository(usersRepository)
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