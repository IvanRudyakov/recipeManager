package com.example.nestednavigationbottombardemo.api

data class RecipeApi (
    val name: String,
    val authors: List<String>,
    val ingredients: String,
    val steps: String
)
