package com.recetario.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.recetario.app.ui.components.RecetarioBottomBar
import com.recetario.app.ui.screens.CameraScreen
import com.recetario.app.ui.screens.FavoritesScreen
import com.recetario.app.ui.screens.RecipeDetailScreen
import com.recetario.app.ui.screens.RecipeListScreen
import com.recetario.app.ui.screens.SettingsScreen

private val BOTTOM_BAR_ROUTES = setOf(RecetarioDestinations.RECIPE_LIST, RecetarioDestinations.FAVORITES)

@Composable
fun RecetarioNavGraph(navController: NavHostController = rememberNavController()) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination?.route
    val showBottomBar = currentDestination != null && currentDestination in BOTTOM_BAR_ROUTES

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                RecetarioBottomBar(
                    currentRoute = currentDestination,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = RecetarioDestinations.RECIPE_LIST,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(RecetarioDestinations.RECIPE_LIST) {
                RecipeListScreen(
                    onRecipeClick = { mealId -> navController.navigate(RecetarioDestinations.recipeDetail(mealId)) },
                    onSettingsClick = { navController.navigate(RecetarioDestinations.SETTINGS) }
                )
            }
            composable(RecetarioDestinations.FAVORITES) {
                FavoritesScreen(
                    onRecipeClick = { mealId -> navController.navigate(RecetarioDestinations.recipeDetail(mealId)) },
                    onSettingsClick = { navController.navigate(RecetarioDestinations.SETTINGS) }
                )
            }
            composable(RecetarioDestinations.RECIPE_DETAIL) { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString(RecetarioDestinations.MEAL_ID_ARG).orEmpty()
                RecipeDetailScreen(
                    mealId = mealId,
                    onBack = { navController.popBackStack() },
                    onTakePhoto = { navController.navigate(RecetarioDestinations.camera(mealId)) }
                )
            }
            composable(RecetarioDestinations.CAMERA) { backStackEntry ->
                val mealId = backStackEntry.arguments?.getString(RecetarioDestinations.MEAL_ID_ARG).orEmpty()
                CameraScreen(
                    mealId = mealId,
                    onPhotoSaved = { navController.popBackStack() },
                    onBack = { navController.popBackStack() }
                )
            }
            composable(RecetarioDestinations.SETTINGS) {
                SettingsScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
