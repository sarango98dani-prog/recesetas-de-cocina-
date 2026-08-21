package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recetario.app.domain.model.ChefRecipe
import com.recetario.app.domain.model.Recipe
import com.recetario.app.domain.usecase.DeleteRecipeUseCase
import com.recetario.app.domain.usecase.GetFavoriteChefRecipesUseCase
import com.recetario.app.domain.usecase.GetSavedRecipesUseCase
import com.recetario.app.domain.usecase.ToggleChefRecipeFavoriteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(
    getSavedRecipesUseCase: GetSavedRecipesUseCase,
    private val deleteRecipeUseCase: DeleteRecipeUseCase,
    getFavoriteChefRecipesUseCase: GetFavoriteChefRecipesUseCase,
    private val toggleChefRecipeFavoriteUseCase: ToggleChefRecipeFavoriteUseCase
) : ViewModel() {

    val favorites: StateFlow<List<Recipe>> = getSavedRecipesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val favoriteChefRecipes: StateFlow<List<ChefRecipe>> = getFavoriteChefRecipesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun deleteRecipe(recipe: Recipe) {
        viewModelScope.launch { deleteRecipeUseCase(recipe) }
    }

    fun removeChefRecipeFavorite(recipe: ChefRecipe) {
        viewModelScope.launch { toggleChefRecipeFavoriteUseCase(recipe) }
    }
}
