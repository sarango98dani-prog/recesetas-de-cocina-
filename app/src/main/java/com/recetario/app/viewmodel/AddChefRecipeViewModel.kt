package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recetario.app.domain.model.ChefRecipe
import com.recetario.app.domain.usecase.AddChefRecipeUseCase
import kotlinx.coroutines.launch

class AddChefRecipeViewModel(
    private val addChefRecipeUseCase: AddChefRecipeUseCase
) : ViewModel() {

    fun saveRecipe(recipe: ChefRecipe, onSaved: () -> Unit) {
        viewModelScope.launch {
            addChefRecipeUseCase(recipe)
            onSaved()
        }
    }
}
