package com.recetario.app.domain.usecase

import com.recetario.app.data.repository.ChefRecipeRepository
import com.recetario.app.domain.model.ChefRecipe

class ToggleChefRecipeFavoriteUseCase(private val repository: ChefRecipeRepository) {
    suspend operator fun invoke(recipe: ChefRecipe) =
        repository.saveChefRecipe(recipe.copy(isFavorite = !recipe.isFavorite))
}
