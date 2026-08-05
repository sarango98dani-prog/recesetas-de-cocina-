package com.recetario.app.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.recetario.app.data.local.ChefRecipeDao
import com.recetario.app.data.local.ChefRecipeEntity
import com.recetario.app.domain.model.ChefRecipe
import com.recetario.app.domain.model.Difficulty
import com.recetario.app.domain.model.Ingredient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChefRecipeRepository(private val dao: ChefRecipeDao) {

    fun getAllChefRecipes(): Flow<List<ChefRecipe>> =
        dao.getAll().map { entities -> entities.map { it.toDomain() } }

    fun getChefRecipeById(id: String): Flow<ChefRecipe?> =
        dao.getById(id).map { it?.toDomain() }

    suspend fun saveChefRecipe(recipe: ChefRecipe) {
        dao.upsert(recipe.toEntity())
    }

    // Precarga el catálogo de la chef la primera vez que se usa la app.
    suspend fun seedIfEmpty() {
        if (dao.count() == 0) {
            dao.upsertAll(ChefRecipeSeedData.recipes.map { it.toEntity() })
        }
    }
}

private val gson = Gson()
private val ingredientListType = object : TypeToken<List<Ingredient>>() {}.type
private val stringListType = object : TypeToken<List<String>>() {}.type

private fun ChefRecipeEntity.toDomain() = ChefRecipe(
    id = id,
    name = name,
    category = category,
    imagePath = imagePath,
    ingredients = gson.fromJson(ingredientsJson, ingredientListType),
    steps = gson.fromJson(stepsJson, stringListType),
    prepTimeMinutes = prepTimeMinutes,
    difficulty = Difficulty.valueOf(difficulty),
    isFavorite = isFavorite,
    isUserCreated = isUserCreated
)

private fun ChefRecipe.toEntity() = ChefRecipeEntity(
    id = id,
    name = name,
    category = category,
    imagePath = imagePath,
    ingredientsJson = gson.toJson(ingredients),
    stepsJson = gson.toJson(steps),
    prepTimeMinutes = prepTimeMinutes,
    difficulty = difficulty.name,
    isFavorite = isFavorite,
    isUserCreated = isUserCreated
)
