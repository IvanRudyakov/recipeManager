package com.example.nestednavigationbottombardemo.screens

import androidx.compose.runtime.Composable
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoadImage(path: String) {
    GlideImage(
        model = path,
        contentDescription = "LoadImage")
}