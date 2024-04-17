package com.example.nestednavigationbottombardemo.database
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
    suspend fun insertAll(product: List<RecipesEntity>)

    @Query("DELETE FROM recipes")
    suspend fun clearRecipes()

    suspend fun clearAndInsert(recipes: List<Recipe>) {
        clearRecipes();
        val entities = recipes.map {
            val entity = RecipesEntity();
            entity.title = it.title;
            entity.author = it.authors.get(0);
            entity.ingredients = it.ingredients;
            entity.steps = it.steps;
            entity
        };
        insertAll(entities);
    }

}