package com.recetario.app.domain.usecase

import com.recetario.app.data.repository.RecipeRepository
import com.recetario.app.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

class GetSavedRecipesUseCase(private val repository: RecipeRepository) {
    operator fun invoke(): Flow<List<Recipe>> = repository.getSavedRecipes()
}
