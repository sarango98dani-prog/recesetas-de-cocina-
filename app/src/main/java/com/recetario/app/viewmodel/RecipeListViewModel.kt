package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recetario.app.domain.model.Recipe
import com.recetario.app.domain.usecase.SaveRecipeUseCase
import com.recetario.app.domain.usecase.SearchRecipesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface RecipeListUiState {
    data object Loading : RecipeListUiState
    data class Success(val recipes: List<Recipe>) : RecipeListUiState
    data class Error(val message: String) : RecipeListUiState
}

class RecipeListViewModel(
    private val searchRecipesUseCase: SearchRecipesUseCase,
    private val saveRecipeUseCase: SaveRecipeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<RecipeListUiState>(RecipeListUiState.Success(emptyList()))
    val uiState: StateFlow<RecipeListUiState> = _uiState.asStateFlow()

    fun search(query: String) {
        if (query.isBlank()) return
        viewModelScope.launch {
            _uiState.value = RecipeListUiState.Loading
            _uiState.value = try {
                RecipeListUiState.Success(searchRecipesUseCase(query))
            } catch (e: IOException) {
                RecipeListUiState.Error("No hay conexión a Internet. Verificá tu red e intentá de nuevo.")
            } catch (e: HttpException) {
                RecipeListUiState.Error("Error del servidor (${e.code()}). Intentá más tarde.")
            }
        }
    }

    // Guarda la receta encontrada en Room para poder abrir su detalle y anotarle notas/foto.
    fun saveRecipe(recipe: Recipe) {
        viewModelScope.launch { saveRecipeUseCase(recipe) }
    }
}
