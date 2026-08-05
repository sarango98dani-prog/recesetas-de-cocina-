package com.recetario.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.recetario.app.viewmodel.AppViewModelProvider
import com.recetario.app.viewmodel.RecipeListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeListScreen(
    onRecipeClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: RecipeListViewModel = viewModel(factory = AppViewModelProvider.RecipeListFactory)
) {
    val recipes by viewModel.recipes.collectAsState()
    var showAddDialog by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis recetas") },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(Icons.Filled.Settings, contentDescription = "Ajustes")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar receta")
            }
        }
    ) { padding ->
        if (recipes.isEmpty()) {
            EmptyState(padding)
        } else {
            LazyColumn(contentPadding = padding) {
                items(recipes, key = { it.mealId }) { recipe ->
                    ListItem(
                        headlineContent = { Text(recipe.name) },
                        supportingContent = { Text("${recipe.category} · ${recipe.area}") },
                        trailingContent = {
                            IconButton(onClick = { viewModel.deleteRecipe(recipe) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                            }
                        },
                        modifier = Modifier.clickable { onRecipeClick(recipe.mealId) }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        AddRecipeDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { name ->
                viewModel.addSampleRecipe(name)
                showAddDialog = false
            }
        )
    }
}

@Composable
private fun EmptyState(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Aún no guardaste recetas.\nToca + para agregar una.",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun AddRecipeDialog(onDismiss: () -> Unit, onConfirm: (String) -> Unit) {
    var name by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva receta") },
        text = {
            Column {
                Text("Semana 1: guardado local con Room. En la Semana 2 esto vendrá de una búsqueda remota.")
                TextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(name) }) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun EmptyStatePreview() {
    EmptyState(PaddingValues())
}
