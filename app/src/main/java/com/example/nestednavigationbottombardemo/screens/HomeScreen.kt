package com.example.nestednavigationbottombardemo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nestednavigationbottombardemo.viewModels.RecipeViewModel

@Composable
fun HomeScreen(recipeViewModel: RecipeViewModel, onClick: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val recipes by recipeViewModel.recipeList.collectAsState();
    val isLoading by recipeViewModel.isLoading.collectAsState();
    val error by recipeViewModel.errors.collectAsState();

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
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Search for recipes") },
                    singleLine = true
                )
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(recipes.filter { r -> r.name.contains(searchQuery, ignoreCase = true) }) { recipe ->
                        RecipeDetailCard(recipe)
                    }
                }
            }
        }
    }
}