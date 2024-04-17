package com.example.nestednavigationbottombardemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.nestednavigationbottombardemo.database.DatabaseProvider
import com.example.nestednavigationbottombardemo.graphs.RootNavigationGraph
import com.example.nestednavigationbottombardemo.ui.theme.NestedNavigationBottomBarDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseProvider.setContext(this)
        setContent {
            NestedNavigationBottomBarDemoTheme {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}