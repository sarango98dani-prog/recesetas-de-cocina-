package com.recetario.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.recetario.app.viewmodel.AppViewModelProvider
import com.recetario.app.viewmodel.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onRecipeClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: FavoritesViewModel = viewModel(factory = AppViewModelProvider.FavoritesFactory)
) {
    val favorites by viewModel.favorites.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favoritos") },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Filled.Settings, contentDescription = "Ajustes")
                    }
                }
            )
        }
    ) { padding ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Todavía no guardaste recetas.\nBuscá una y tocala para guardarla.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(contentPadding = padding) {
                items(favorites, key = { it.id }) { recipe ->
                    ListItem(
                        leadingContent = {
                            AsyncImage(
                                model = recipe.thumbnailUrl,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp)
                            )
                        },
                        headlineContent = { Text(recipe.name) },
                        supportingContent = { Text("${recipe.category} · ${recipe.area}") },
                        trailingContent = {
                            IconButton(onClick = { viewModel.deleteRecipe(recipe) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                            }
                        },
                        modifier = Modifier.clickable { onRecipeClick(recipe.id) }
                    )
                }
            }
        }
    }
}
