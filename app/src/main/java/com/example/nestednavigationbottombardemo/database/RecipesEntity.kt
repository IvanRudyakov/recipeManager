package com.example.nestednavigationbottombardemo.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "recipes")
class RecipesEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0;
    var title: String? = null;
    var authors: String? = null;
    var ingredients: String? = null;
    var steps: String? = null;
}
