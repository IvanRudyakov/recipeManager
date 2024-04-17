package com.example.nestednavigationbottombardemo.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.nestednavigationbottombardemo.Recipe
import com.example.nestednavigationbottombardemo.viewModels.RecipeViewModel
import com.example.nestednavigationbottombardemo.User
import com.example.nestednavigationbottombardemo.isOnline
import com.example.nestednavigationbottombardemo.viewModels.UsersViewModel

@Composable
fun ScreenContent(name: String, usersViewModel: UsersViewModel, recipeViewModel: RecipeViewModel, onClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (name) {
            "HOME" -> HomeScreen(recipeViewModel, onClick)
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

@Composable
fun HomeScreen(recipeViewModel: RecipeViewModel, onClick: () -> Unit) {
    var searchQuery by remember { mutableStateOf("") }
    val recipes by recipeViewModel.recipeList.collectAsState();

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



@Composable
fun UsersScreen(usersViewModel: UsersViewModel, onClick: () -> Unit) {
    // Sample data - assume this is your user data model list
    val users by usersViewModel.userList.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Search for users") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn {
                items(users.filter { it.name.contains(searchQuery, ignoreCase = true) }) { user ->
                    UserCard(user)
                }
            }
        }
    }
}

@Composable
fun UserCard(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.h6
            )
            Text(
                text = "${user.numberOfRecipes} recipes",
                style = MaterialTheme.typography.body1
            )
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateScreen(recipeViewModel: RecipeViewModel, usersViewModel: UsersViewModel) {
    val curUser = usersViewModel.user;
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    var recipeName by remember { mutableStateOf("") }
    var ingredients = remember { mutableStateListOf<String>("") }
    var steps by remember { mutableStateOf("") }

    fun clearAllFields() {
        recipeName = ""
        ingredients.clear()
        steps = ""
        ingredients.add("")  // Start with an empty field for authors
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Create New Recipe", fontSize = MaterialTheme.typography.h4.fontSize, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = recipeName,
            onValueChange = { recipeName = it },
            label = { Text("Recipe Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        for (index in ingredients.indices) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = ingredients[index],
                    onValueChange = { ingredients[index] = it },
                    modifier = Modifier.weight(1f),
                    label = { Text("Ingredient") },
                    singleLine = true
                )
                IconButton(onClick = {
                    if (ingredients.size > 1) {
                        ingredients.removeAt(index)
                    } else {
                        ingredients[index] = ""  // Clear the field if it's the only one
                    }
                }) {
                    Icon(Icons.Filled.Close, contentDescription = "Remove author")
                }
                if (index == ingredients.size - 1 && ingredients.size < 3) {
                    IconButton(onClick = { if (ingredients.size < 3) ingredients.add("") }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add another author")
                    }
                }
            }
        }
        OutlinedTextField(
            value = steps,
            onValueChange = { steps = it },
            label = { Text("Cooking Steps") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (isOnline(context)) {
                    if (recipeName.isNotEmpty() && ingredients.isNotEmpty() && steps.isNotEmpty()) {
                        recipeViewModel.addRecipe(Recipe(recipeName, curUser ?: "", ingredients.joinToString(), steps))
                        clearAllFields()
                        keyboardController?.hide()
                        Toast.makeText(context, "Recipe Created", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "No internet connection. Cannot create recipe.", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = recipeName.isNotEmpty() && ingredients.isNotEmpty() && steps.isNotEmpty()
        ) {
            Text("Create Recipe")
        }
    }
}


@Composable
fun ProfileScreen(recipeViewModel: RecipeViewModel, usersViewModel: UsersViewModel) {

    val recipes by recipeViewModel.recipeList.collectAsState();
    val curUser = usersViewModel.user ?: "";

    val ownRecipes = recipes.filter { r -> r.author == curUser };

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

@Composable
fun RecipeDetailCard(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(recipe.name, style = MaterialTheme.typography.h6)
            Text("Author: ${recipe.author}", style = MaterialTheme.typography.body2)
            Text("Ingredients: ${recipe.ingredients}", style = MaterialTheme.typography.body2)
            Text("Steps: ${recipe.steps}", style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}