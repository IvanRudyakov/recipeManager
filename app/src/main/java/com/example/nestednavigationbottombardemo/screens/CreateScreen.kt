package com.example.nestednavigationbottombardemo.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
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
import com.example.nestednavigationbottombardemo.isOnline
import com.example.nestednavigationbottombardemo.viewModels.RecipeViewModel
import com.example.nestednavigationbottombardemo.viewModels.UsersViewModel

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