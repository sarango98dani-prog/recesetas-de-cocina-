package com.recetario.app.domain.usecase

import com.recetario.app.data.repository.RecipeRepository
import com.recetario.app.domain.model.Recipe

class SearchRecipesUseCase(private val repository: RecipeRepository) {
    suspend operator fun invoke(query: String): List<Recipe> = repository.searchRecipes(query)
}
