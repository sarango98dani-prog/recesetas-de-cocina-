package com.recetario.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.recetario.app.viewmodel.AppViewModelProvider
import com.recetario.app.viewmodel.RecipeListUiState
import com.recetario.app.viewmodel.RecipeListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    onRecipeClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: RecipeListViewModel = viewModel(factory = AppViewModelProvider.RecipeListFactory)
) {
    val uiState by viewModel.uiState.collectAsState()
    var query by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Buscar recetas") },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Filled.Settings, contentDescription = "Ajustes")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Nombre de la receta") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { viewModel.search(query) }),
                trailingIcon = {
                    IconButton(onClick = { viewModel.search(query) }) {
                        Icon(Icons.Filled.Search, contentDescription = "Buscar")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            when (val state = uiState) {
                is RecipeListUiState.Loading -> LoadingState()
                is RecipeListUiState.Error -> ErrorState(state.message)
                is RecipeListUiState.Success -> {
                    if (state.recipes.isEmpty()) {
                        EmptyState()
                    } else {
                        LazyColumn {
                            items(state.recipes, key = { it.id }) { recipe ->
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
                                        IconButton(onClick = { viewModel.saveRecipe(recipe) }) {
                                            Icon(Icons.Filled.Favorite, contentDescription = "Guardar en favoritos")
                                        }
                                    },
                                    modifier = Modifier.clickable {
                                        viewModel.saveRecipe(recipe)
                                        onRecipeClick(recipe.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun EmptyState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "Buscá una receta por nombre para empezar.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyStatePreview() {
    EmptyState()
}
