package com.example.nestednavigationbottombardemo.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.nestednavigationbottombardemo.RecipeViewModel

@Composable
fun LoginContent(recipeViewModel: RecipeViewModel, onClick: () -> Unit) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(if (passwordVisibility) Icons.Filled.Check else Icons.Filled.Check, contentDescription = "Toggle password visibility")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (username == "admin" && password == "admin") {
                    recipeViewModel.loginUser(username)
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    onClick()
                } else {
                    Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }
    }
}

private fun validateLogin(username: String, password: String, viewModel: RecipeViewModel, onClick: () -> Unit, context: Context) {
    if (username == "admin" && password == "admin") {
        viewModel.loginUser(username)
        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
        onClick()
    } else {
        Toast.makeText(context, "Incorrect username or password", Toast.LENGTH_SHORT).show()
    }
}