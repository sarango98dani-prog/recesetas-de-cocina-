package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recetario.app.domain.usecase.AddChefRecipeUseCase
import com.recetario.app.domain.usecase.GetChefRecipeByIdUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ChefCameraViewModel(
    private val getChefRecipeByIdUseCase: GetChefRecipeByIdUseCase,
    private val addChefRecipeUseCase: AddChefRecipeUseCase,
    private val recipeId: String
) : ViewModel() {

    fun onPhotoCaptured(photoPath: String) {
        viewModelScope.launch {
            val recipe = getChefRecipeByIdUseCase(recipeId).first() ?: return@launch
            addChefRecipeUseCase(recipe.copy(imagePath = photoPath))
        }
    }
}
