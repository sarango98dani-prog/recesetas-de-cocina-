package com.recetario.app.domain.usecase

import com.recetario.app.data.repository.ChefRecipeRepository

class SeedChefRecipesUseCase(private val repository: ChefRecipeRepository) {
    suspend operator fun invoke() = repository.seedIfEmpty()
}
