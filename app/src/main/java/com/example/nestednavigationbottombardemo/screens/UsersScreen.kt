package com.example.nestednavigationbottombardemo.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
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
import com.example.nestednavigationbottombardemo.User
import com.example.nestednavigationbottombardemo.viewModels.UsersViewModel

@Composable
fun UsersScreen(usersViewModel: UsersViewModel, onClick: () -> Unit) {
    // Sample data - assume this is your user data model list
    val users by usersViewModel.userList.collectAsState()
    val isLoading by usersViewModel.isLoading.collectAsState();
    val error by usersViewModel.errors.collectAsState();
    var searchQuery by remember { mutableStateOf("") }

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
                    label = { Text("Search for users") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn {
                    items(users.filter {
                        it.name.contains(
                            searchQuery,
                            ignoreCase = true
                        )
                    }) { user ->
                        UserCard(user)
                    }
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