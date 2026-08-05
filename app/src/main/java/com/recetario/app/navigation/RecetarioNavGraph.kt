package com.recetario.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.recetario.app.ui.screens.RecipeDetailScreen
import com.recetario.app.ui.screens.RecipeListScreen
import com.recetario.app.ui.screens.SettingsScreen

@Composable
fun RecetarioNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = RecetarioDestinations.RECIPE_LIST) {
        composable(RecetarioDestinations.RECIPE_LIST) {
            RecipeListScreen(
                onRecipeClick = { mealId -> navController.navigate(RecetarioDestinations.recipeDetail(mealId)) },
                onSettingsClick = { navController.navigate(RecetarioDestinations.SETTINGS) }
            )
        }
        composable(RecetarioDestinations.RECIPE_DETAIL) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString(RecetarioDestinations.MEAL_ID_ARG).orEmpty()
            RecipeDetailScreen(mealId = mealId, onBack = { navController.popBackStack() })
        }
        composable(RecetarioDestinations.SETTINGS) {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
    }
}
