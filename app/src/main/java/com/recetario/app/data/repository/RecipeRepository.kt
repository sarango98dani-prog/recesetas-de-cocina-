package com.recetario.app.data.repository

import com.recetario.app.data.local.RecipeDao
import com.recetario.app.data.local.RecipeEntity
import com.recetario.app.data.remote.MealApiService
import com.recetario.app.data.remote.toDomain
import com.recetario.app.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecipeRepository(
    private val dao: RecipeDao,
    private val api: MealApiService
) {

    suspend fun searchRecipes(query: String): List<Recipe> {
        val response = api.searchMeals(query)
        return response.meals?.map { it.toDomain() } ?: emptyList()
    }

    fun getSavedRecipes(): Flow<List<Recipe>> =
        dao.getAll().map { entities -> entities.map { it.toDomain() } }

    fun getRecipeById(id: String): Flow<Recipe?> =
        dao.getById(id).map { it?.toDomain() }

    suspend fun saveRecipe(recipe: Recipe) {
        dao.upsert(recipe.toEntity())
    }

    suspend fun deleteRecipe(recipe: Recipe) {
        dao.delete(recipe.toEntity())
    }
}

private fun RecipeEntity.toDomain() = Recipe(
    id = mealId,
    name = name,
    thumbnailUrl = thumbnailUrl,
    category = category,
    area = area,
    notes = notes,
    photoPath = photoPath
)

private fun Recipe.toEntity() = RecipeEntity(
    mealId = id,
    name = name,
    thumbnailUrl = thumbnailUrl,
    category = category,
    area = area,
    notes = notes,
    photoPath = photoPath
)
