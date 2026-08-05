package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recetario.app.data.local.RecipeDao
import com.recetario.app.data.local.RecipeEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class RecipeListViewModel(private val dao: RecipeDao) : ViewModel() {

    val recipes: StateFlow<List<RecipeEntity>> = dao.getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    // TODO(Semana 2): reemplazar por resultados de búsqueda vía Retrofit + Repository.
    fun addSampleRecipe(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            dao.upsert(
                RecipeEntity(
                    mealId = UUID.randomUUID().toString(),
                    name = name,
                    thumbnailUrl = "",
                    category = "Sin categoría",
                    area = "Desconocida"
                )
            )
        }
    }

    fun deleteRecipe(recipe: RecipeEntity) {
        viewModelScope.launch { dao.delete(recipe) }
    }
}
