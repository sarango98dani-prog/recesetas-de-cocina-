package com.recetario.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.recetario.app.viewmodel.AppViewModelProvider
import com.recetario.app.viewmodel.RecipeDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    mealId: String,
    onBack: () -> Unit,
    viewModel: RecipeDetailViewModel = viewModel(factory = AppViewModelProvider.detailFactory(mealId))
) {
    val recipe by viewModel.recipe.collectAsState()
    var notes by remember { mutableStateOf("") }

    LaunchedEffect(recipe?.notes) {
        recipe?.notes?.let { notes = it }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe?.name ?: "Receta") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        val current = recipe
        if (current == null) {
            Text(
                text = "Receta no encontrada.",
                modifier = Modifier.padding(padding).padding(16.dp)
            )
        } else {
            Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
                Text(text = current.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "${current.category} · ${current.area}")
                // TODO(Semana 4): agregar foto propia del plato usando la cámara.
                TextField(
                    value = notes,
                    onValueChange = {
                        notes = it
                        viewModel.updateNotes(it)
                    },
                    label = { Text("Mis notas") },
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                )
            }
        }
    }
}
