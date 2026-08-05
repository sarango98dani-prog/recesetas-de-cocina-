package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.recetario.app.RecetarioApplication

private fun CreationExtras.recetarioApplication(): RecetarioApplication =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RecetarioApplication

object AppViewModelProvider {

    val RecipeListFactory = viewModelFactory {
        initializer {
            RecipeListViewModel(recetarioApplication().database.recipeDao())
        }
    }

    val SettingsFactory = viewModelFactory {
        initializer {
            SettingsViewModel(recetarioApplication().userPreferences)
        }
    }

    fun detailFactory(mealId: String) = viewModelFactory {
        initializer {
            RecipeDetailViewModel(recetarioApplication().database.recipeDao(), mealId)
        }
    }
}
