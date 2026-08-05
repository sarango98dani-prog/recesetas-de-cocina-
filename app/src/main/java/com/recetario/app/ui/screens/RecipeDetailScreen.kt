package com.recetario.app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.recetario.app.viewmodel.AppViewModelProvider
import com.recetario.app.viewmodel.RecipeDetailViewModel
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    mealId: String,
    onBack: () -> Unit,
    onTakePhoto: () -> Unit,
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
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Text(text = current.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "${current.category} · ${current.area}")

                val photoPath = current.photoPath
                if (photoPath != null) {
                    AsyncImage(
                        model = File(photoPath),
                        contentDescription = "Foto propia del plato",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(top = 16.dp)
                    )
                }

                Button(
                    onClick = onTakePhoto,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Icon(Icons.Filled.PhotoCamera, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (photoPath != null) "Tomar otra foto" else "Tomar foto del plato")
                }

                TextField(
                    value = notes,
                    onValueChange = {
                        notes = it
                        viewModel.updateNotes(it)
                    },
                    label = { Text("Mis notas") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }
        }
    }
}
