package com.recetario.app.domain.usecase

import com.recetario.app.data.repository.ChefRecipeRepository
import com.recetario.app.domain.model.ChefRecipe
import kotlinx.coroutines.flow.Flow

class GetChefRecipesUseCase(private val repository: ChefRecipeRepository) {
    operator fun invoke(): Flow<List<ChefRecipe>> = repository.getAllChefRecipes()
}
