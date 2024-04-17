package com.example.nestednavigationbottombardemo.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.Room
@SuppressLint("StaticFieldLeak")
object DatabaseProvider {

    private var recipesDatabase: RecipesDatabase? = null
    private var context: Context? = null;

    fun setContext(context: Context) {
        this.context = context;
    }

    fun getRecipesDatabase(): RecipesDatabase? {
        if (recipesDatabase == null && context != null) {
            recipesDatabase = Room.databaseBuilder(
                context!!,
                RecipesDatabase::class.java, "recipes_database"
            ).build();
        }
        return recipesDatabase
    }
}