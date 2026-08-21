package com.recetario.app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ChefRecipeDao {

    @Query("SELECT * FROM chef_recipes ORDER BY name ASC")
    fun getAll(): Flow<List<ChefRecipeEntity>>

    @Query("SELECT * FROM chef_recipes WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavorites(): Flow<List<ChefRecipeEntity>>

    @Query("SELECT * FROM chef_recipes WHERE id = :id")
    fun getById(id: String): Flow<ChefRecipeEntity?>

    @Query("SELECT COUNT(*) FROM chef_recipes")
    suspend fun count(): Int

    @Upsert
    suspend fun upsert(recipe: ChefRecipeEntity)

    @Upsert
    suspend fun upsertAll(recipes: List<ChefRecipeEntity>)

    @Delete
    suspend fun delete(recipe: ChefRecipeEntity)
}
