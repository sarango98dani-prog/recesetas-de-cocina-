package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recetario.app.domain.model.ChefRecipe
import com.recetario.app.domain.usecase.GetChefRecipesUseCase
import com.recetario.app.domain.usecase.SeedChefRecipesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ChefRecipesViewModel(
    getChefRecipesUseCase: GetChefRecipesUseCase,
    seedChefRecipesUseCase: SeedChefRecipesUseCase
) : ViewModel() {

    val recipes: StateFlow<List<ChefRecipe>> = getChefRecipesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        viewModelScope.launch { seedChefRecipesUseCase() }
    }
}
