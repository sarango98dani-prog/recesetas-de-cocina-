package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recetario.app.domain.model.Recipe
import com.recetario.app.domain.usecase.AddToFavoritesUseCase
import com.recetario.app.domain.usecase.SearchRecipesUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
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
    private val addToFavoritesUseCase: AddToFavoritesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<RecipeListUiState>(RecipeListUiState.Success(emptyList()))
    val uiState: StateFlow<RecipeListUiState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    fun search(query: String) {
        if (query.isBlank()) return
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _uiState.value = RecipeListUiState.Loading
            _uiState.value = try {
                RecipeListUiState.Success(searchRecipesUseCase(query))
            } catch (e: CancellationException) {
                throw e
            } catch (e: IOException) {
                RecipeListUiState.Error("No hay conexión a Internet. Verificá tu red e intentá de nuevo.")
            } catch (e: HttpException) {
                RecipeListUiState.Error("Error del servidor (${e.code()}). Intentá más tarde.")
            } catch (e: Exception) {
                RecipeListUiState.Error("Ocurrió un error inesperado. Intentá de nuevo.")
            }
        }
    }

    // Guarda la receta encontrada en Room (si no era favorita ya) para poder
    // abrir su detalle y anotarle notas/foto sin pisar lo que hubiera guardado.
    fun saveRecipe(recipe: Recipe) {
        viewModelScope.launch { addToFavoritesUseCase(recipe) }
    }
}
