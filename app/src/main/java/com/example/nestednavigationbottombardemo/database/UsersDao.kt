package com.example.nestednavigationbottombardemo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.nestednavigationbottombardemo.User


@Dao
interface UsersDao {
    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UsersEntity?>?

    @Insert
    suspend fun insertAll(product: List<UsersEntity>)

    @Query("DELETE FROM users")
    suspend fun clearUsers()

    suspend fun clearAndInsert(users: List<User>) {
        clearUsers();
        val entities = users.map {
            val entity = UsersEntity();
            entity.username = it.username;
            entity.recipeCount = it.recipeCount;
            entity
        };
        insertAll(entities);
    }
}