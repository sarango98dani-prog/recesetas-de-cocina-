package com.recetario.app.domain.usecase

import com.recetario.app.data.repository.ChefRecipeRepository
import com.recetario.app.domain.model.ChefRecipe
import kotlinx.coroutines.flow.Flow

class GetChefRecipeByIdUseCase(private val repository: ChefRecipeRepository) {
    operator fun invoke(id: String): Flow<ChefRecipe?> = repository.getChefRecipeById(id)
}
