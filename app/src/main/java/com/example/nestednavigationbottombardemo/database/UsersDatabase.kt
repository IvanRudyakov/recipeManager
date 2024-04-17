package com.example.nestednavigationbottombardemo.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UsersEntity::class], version = 2)
abstract class UsersDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao?
}