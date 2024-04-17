package com.example.nestednavigationbottombardemo.database
import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.nestednavigationbottombardemo.Recipe


@Dao
interface RecipesDao {
    @Query("SELECT * FROM recipes")
    suspend fun getAll(): List<RecipesEntity?>?

    @Insert
    suspend fun insertAll(recipes: List<RecipesEntity>)

    @Insert
    suspend fun insert(recipe: RecipesEntity)

    @Query("DELETE FROM recipes")
    suspend fun clearRecipes()

    suspend fun clearAndInsert(recipes: List<Recipe>) {
        Log.d("aaa", "clear recipes")
        clearRecipes();
        val entities = recipes.map {
            val entity = RecipesEntity();
            entity.title = it.name;
            entity.author = it.author;
            entity.ingredients = it.ingredients;
            entity.steps = it.steps;
            entity
        };

        insertAll(entities);
    }

}