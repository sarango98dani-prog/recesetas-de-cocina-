package com.recetario.app.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.recetario.app.ui.components.CategoryFolderCard
import com.recetario.app.ui.components.ChefRecipeCard
import com.recetario.app.viewmodel.AppViewModelProvider
import com.recetario.app.viewmodel.ChefRecipesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChefRecipesScreen(
    onRecipeClick: (String) -> Unit,
    onAddRecipeClick: () -> Unit,
    viewModel: ChefRecipesViewModel = viewModel(factory = AppViewModelProvider.ChefRecipesFactory)
) {
    val recipes by viewModel.recipes.collectAsState()
    var selectedCategory by rememberSaveable { mutableStateOf<String?>(null) }

    BackHandler(enabled = selectedCategory != null) { selectedCategory = null }

    val categories = remember(recipes) {
        recipes.groupBy { it.category }.toSortedMap()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(selectedCategory ?: "Recetas de la Chef", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    if (selectedCategory != null) {
                        IconButton(onClick = { selectedCategory = null }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        floatingActionButton = {
            if (selectedCategory == null) {
                FloatingActionButton(onClick = onAddRecipeClick) {
                    Icon(Icons.Filled.Add, contentDescription = "Agregar nueva receta")
                }
            }
        }
    ) { padding ->
        val category = selectedCategory
        if (category == null) {
            if (categories.isEmpty()) {
                EmptyCatalogState(padding)
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        top = padding.calculateTopPadding() + 16.dp,
                        bottom = padding.calculateBottomPadding() + 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(categories.keys.toList()) { cat ->
                        CategoryFolderCard(
                            category = cat,
                            recipeCount = categories[cat]?.size ?: 0,
                            onClick = { selectedCategory = cat }
                        )
                    }
                }
            }
        } else {
            val recipesInCategory = categories[category].orEmpty()
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = padding.calculateTopPadding() + 16.dp,
                    bottom = padding.calculateBottomPadding() + 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(recipesInCategory, key = { it.id }) { recipe ->
                    ChefRecipeCard(recipe = recipe, onClick = { onRecipeClick(recipe.id) })
                }
            }
        }
    }
}

@Composable
private fun EmptyCatalogState(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Todavía no hay recetas en el catálogo.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
