package com.recetario.app.domain.usecase

import com.recetario.app.data.repository.RecipeRepository
import com.recetario.app.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

class GetRecipeByIdUseCase(private val repository: RecipeRepository) {
    operator fun invoke(id: String): Flow<Recipe?> = repository.getRecipeById(id)
}
