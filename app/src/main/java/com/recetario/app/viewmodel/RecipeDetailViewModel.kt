package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recetario.app.domain.usecase.GetRecipeByIdUseCase
import com.recetario.app.domain.usecase.SaveRecipeUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val NOTES_SAVE_DEBOUNCE_MS = 400L

class RecipeDetailViewModel(
    getRecipeByIdUseCase: GetRecipeByIdUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase,
    recipeId: String
) : ViewModel() {

    val recipe = getRecipeByIdUseCase(recipeId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    private var notesSaveJob: Job? = null

    fun updateNotes(notes: String) {
        notesSaveJob?.cancel()
        notesSaveJob = viewModelScope.launch {
            delay(NOTES_SAVE_DEBOUNCE_MS)
            val current = recipe.value ?: return@launch
            saveRecipeUseCase(current.copy(notes = notes))
        }
    }

    fun updatePhoto(photoPath: String) {
        val current = recipe.value ?: return
        viewModelScope.launch { saveRecipeUseCase(current.copy(photoPath = photoPath)) }
    }
}
