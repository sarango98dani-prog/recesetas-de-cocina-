package com.recetario.app.domain.usecase

import com.recetario.app.data.repository.RecipeRepository
import com.recetario.app.domain.model.Recipe

class AddToFavoritesUseCase(private val repository: RecipeRepository) {
    suspend operator fun invoke(recipe: Recipe) = repository.saveNewRecipe(recipe)
}
