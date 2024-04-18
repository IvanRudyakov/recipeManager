package com.example.nestednavigationbottombardemo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nestednavigationbottombardemo.Recipe

@Composable
fun RecipeDetailCard(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            LoadImage("https://th.bing.com/th/id/OIP.g9meiANJcKP0ekLeCy4laQHaE7?rs=1&pid=ImgDetMain")
            Column(modifier = Modifier.padding(16.dp)) {
                Text(recipe.name, style = MaterialTheme.typography.h6)
                Text("Author: ${recipe.author}", style = MaterialTheme.typography.body2)
                Text("Ingredients: ${recipe.ingredients}", style = MaterialTheme.typography.body2)
                Text("Steps: ${recipe.steps}", style = MaterialTheme.typography.body2)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}