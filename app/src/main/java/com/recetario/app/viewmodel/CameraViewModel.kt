package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recetario.app.domain.usecase.GetRecipeByIdUseCase
import com.recetario.app.domain.usecase.SaveRecipeUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class CameraViewModel(
    private val getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    private val recipeId: String
) : ViewModel() {

    fun onPhotoCaptured(photoPath: String) {
        viewModelScope.launch {
            val recipe = getRecipeByIdUseCase(recipeId).first() ?: return@launch
            saveRecipeUseCase(recipe.copy(photoPath = photoPath))
        }
    }
}
