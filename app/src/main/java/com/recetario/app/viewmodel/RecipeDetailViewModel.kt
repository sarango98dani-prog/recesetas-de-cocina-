package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recetario.app.data.local.RecipeDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val dao: RecipeDao,
    mealId: String
) : ViewModel() {

    val recipe = dao.getById(mealId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun updateNotes(notes: String) {
        val current = recipe.value ?: return
        viewModelScope.launch { dao.upsert(current.copy(notes = notes)) }
    }
}
