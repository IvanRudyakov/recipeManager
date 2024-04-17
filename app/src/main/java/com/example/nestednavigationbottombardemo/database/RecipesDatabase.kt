package com.example.nestednavigationbottombardemo.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [RecipesEntity::class], version = 1)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao?
}