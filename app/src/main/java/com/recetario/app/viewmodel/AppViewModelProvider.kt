package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.recetario.app.RecetarioApplication
import com.recetario.app.data.remote.RetrofitInstance
import com.recetario.app.data.repository.ChefRecipeRepository
import com.recetario.app.data.repository.RecipeRepository
import com.recetario.app.data.repository.UserPreferencesRepository
import com.recetario.app.domain.usecase.AddChefRecipeUseCase
import com.recetario.app.domain.usecase.AddToFavoritesUseCase
import com.recetario.app.domain.usecase.DeleteRecipeUseCase
import com.recetario.app.domain.usecase.GetChefRecipeByIdUseCase
import com.recetario.app.domain.usecase.GetChefRecipesUseCase
import com.recetario.app.domain.usecase.GetFavoriteChefRecipesUseCase
import com.recetario.app.domain.usecase.GetRecipeByIdUseCase
import com.recetario.app.domain.usecase.GetSavedRecipesUseCase
import com.recetario.app.domain.usecase.GetUnitSystemUseCase
import com.recetario.app.domain.usecase.SaveRecipeUseCase
import com.recetario.app.domain.usecase.SearchRecipesUseCase
import com.recetario.app.domain.usecase.SeedChefRecipesUseCase
import com.recetario.app.domain.usecase.SetUnitSystemUseCase
import com.recetario.app.domain.usecase.ToggleChefRecipeFavoriteUseCase

private fun CreationExtras.recetarioApplication(): RecetarioApplication =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as RecetarioApplication

private fun CreationExtras.recipeRepository(): RecipeRepository =
    RecipeRepository(
        dao = recetarioApplication().database.recipeDao(),
        api = RetrofitInstance.mealApiService
    )

private fun CreationExtras.chefRecipeRepository(): ChefRecipeRepository =
    ChefRecipeRepository(dao = recetarioApplication().database.chefRecipeDao())

object AppViewModelProvider {

    val RecipeListFactory = viewModelFactory {
        initializer {
            val repository = recipeRepository()
            RecipeListViewModel(
                searchRecipesUseCase = SearchRecipesUseCase(repository),
                addToFavoritesUseCase = AddToFavoritesUseCase(repository)
            )
        }
    }

    val FavoritesFactory = viewModelFactory {
        initializer {
            val repository = recipeRepository()
            val chefRepository = chefRecipeRepository()
            FavoritesViewModel(
                getSavedRecipesUseCase = GetSavedRecipesUseCase(repository),
                deleteRecipeUseCase = DeleteRecipeUseCase(repository),
                getFavoriteChefRecipesUseCase = GetFavoriteChefRecipesUseCase(chefRepository),
                toggleChefRecipeFavoriteUseCase = ToggleChefRecipeFavoriteUseCase(chefRepository)
            )
        }
    }

    val SettingsFactory = viewModelFactory {
        initializer {
            val repository = UserPreferencesRepository(recetarioApplication().userPreferences)
            SettingsViewModel(
                getUnitSystemUseCase = GetUnitSystemUseCase(repository),
                setUnitSystemUseCase = SetUnitSystemUseCase(repository)
            )
        }
    }

    fun detailFactory(mealId: String) = viewModelFactory {
        initializer {
            val repository = recipeRepository()
            RecipeDetailViewModel(
                getRecipeByIdUseCase = GetRecipeByIdUseCase(repository),
                saveRecipeUseCase = SaveRecipeUseCase(repository),
                recipeId = mealId
            )
        }
    }

    fun cameraFactory(mealId: String) = viewModelFactory {
        initializer {
            val repository = recipeRepository()
            CameraViewModel(
                getRecipeByIdUseCase = GetRecipeByIdUseCase(repository),
                saveRecipeUseCase = SaveRecipeUseCase(repository),
                recipeId = mealId
            )
        }
    }

    val ChefRecipesFactory = viewModelFactory {
        initializer {
            val repository = chefRecipeRepository()
            ChefRecipesViewModel(
                getChefRecipesUseCase = GetChefRecipesUseCase(repository),
                seedChefRecipesUseCase = SeedChefRecipesUseCase(repository)
            )
        }
    }

    val AddChefRecipeFactory = viewModelFactory {
        initializer {
            val repository = chefRecipeRepository()
            AddChefRecipeViewModel(addChefRecipeUseCase = AddChefRecipeUseCase(repository))
        }
    }

    fun chefRecipeDetailFactory(recipeId: String) = viewModelFactory {
        initializer {
            val repository = chefRecipeRepository()
            ChefRecipeDetailViewModel(
                getChefRecipeByIdUseCase = GetChefRecipeByIdUseCase(repository),
                toggleFavoriteUseCase = ToggleChefRecipeFavoriteUseCase(repository),
                addChefRecipeUseCase = AddChefRecipeUseCase(repository),
                recipeId = recipeId
            )
        }
    }

    fun chefCameraFactory(recipeId: String) = viewModelFactory {
        initializer {
            val repository = chefRecipeRepository()
            ChefCameraViewModel(
                getChefRecipeByIdUseCase = GetChefRecipeByIdUseCase(repository),
                addChefRecipeUseCase = AddChefRecipeUseCase(repository),
                recipeId = recipeId
            )
        }
    }
}
