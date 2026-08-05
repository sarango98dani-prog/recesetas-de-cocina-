package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recetario.app.domain.usecase.GetChefRecipeByIdUseCase
import com.recetario.app.domain.usecase.ToggleChefRecipeFavoriteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChefRecipeDetailViewModel(
    getChefRecipeByIdUseCase: GetChefRecipeByIdUseCase,
    private val toggleFavoriteUseCase: ToggleChefRecipeFavoriteUseCase,
    recipeId: String
) : ViewModel() {

    val recipe = getChefRecipeByIdUseCase(recipeId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun toggleFavorite() {
        val current = recipe.value ?: return
        viewModelScope.launch { toggleFavoriteUseCase(current) }
    }
}
