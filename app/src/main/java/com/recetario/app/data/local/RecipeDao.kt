package com.recetario.app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Query("SELECT * FROM saved_recipes ORDER BY name ASC")
    fun getAll(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM saved_recipes WHERE mealId = :mealId")
    fun getById(mealId: String): Flow<RecipeEntity?>

    @Query("SELECT EXISTS(SELECT 1 FROM saved_recipes WHERE mealId = :mealId)")
    fun isFavorite(mealId: String): Flow<Boolean>

    @Upsert
    suspend fun upsert(recipe: RecipeEntity)

    @Delete
    suspend fun delete(recipe: RecipeEntity)
}
