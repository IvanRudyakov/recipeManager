package com.example.nestednavigationbottombardemo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nestednavigationbottombardemo.viewModels.RecipeViewModel
import com.example.nestednavigationbottombardemo.viewModels.UsersViewModel



@Composable
fun ProfileScreen(recipeViewModel: RecipeViewModel, usersViewModel: UsersViewModel) {

    val recipes by recipeViewModel.recipeList.collectAsState();
    val isLoading by recipeViewModel.isLoading.collectAsState();
    val error by recipeViewModel.errors.collectAsState();
    val curUser = usersViewModel.user ?: "";

    val ownRecipes = recipes.filter { r -> r.author == curUser };

    if (isLoading) {
        LoadingScreen()
    }
    else if (error != null) {
        Text(
            text = error ?: "",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold
        )
    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Profile Icon",
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = curUser,  // Displaying the dynamic username
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                "${ownRecipes.size} Recipes Created",
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(ownRecipes) { recipe ->
                    RecipeDetailCard(recipe)
                }
            }
        }
    }
}