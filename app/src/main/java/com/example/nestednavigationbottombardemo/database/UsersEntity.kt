package com.example.nestednavigationbottombardemo.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UsersEntity {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var username: String? = null;
    var recipeCount: Int = 0;
}