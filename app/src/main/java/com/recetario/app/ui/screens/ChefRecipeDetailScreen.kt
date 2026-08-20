package com.recetario.app.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.recetario.app.viewmodel.AppViewModelProvider
import com.recetario.app.viewmodel.ChefRecipeDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChefRecipeDetailScreen(
    recipeId: String,
    onBack: () -> Unit,
    onTakePhoto: () -> Unit,
    viewModel: ChefRecipeDetailViewModel = viewModel(factory = AppViewModelProvider.chefRecipeDetailFactory(recipeId))
) {
    val recipe by viewModel.recipe.collectAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val pickImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            coroutineScope.launch {
                val copiedPath = withContext(Dispatchers.IO) {
                    runCatching {
                        val file = File(context.filesDir, "chef_recipe_gallery_${System.currentTimeMillis()}.jpg")
                        context.contentResolver.openInputStream(uri)?.use { input ->
                            file.outputStream().use { output -> input.copyTo(output) }
                        }
                        file.absolutePath
                    }.getOrNull()
                }
                if (copiedPath != null) viewModel.updatePhoto(copiedPath)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe?.name ?: "Receta", maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    if (recipe != null) {
                        IconButton(onClick = { viewModel.toggleFavorite() }) {
                            Icon(
                                imageVector = if (recipe?.isFavorite == true) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favorito",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { padding ->
        val current = recipe
        if (current == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Text(text = "Receta no encontrada.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    if (current.imagePath != null) {
                        AsyncImage(
                            model = File(current.imagePath),
                            contentDescription = current.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.RestaurantMenu,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize()
                                .padding(64.dp)
                        )
                    }
                }

                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text = current.name, style = MaterialTheme.typography.headlineLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row {
                        AssistChip(onClick = {}, enabled = false, label = { Text(current.category) })
                        Spacer(modifier = Modifier.width(8.dp))
                        AssistChip(
                            onClick = {},
                            enabled = false,
                            leadingIcon = { Icon(Icons.Filled.Schedule, contentDescription = null) },
                            label = { Text("${current.prepTimeMinutes} min") }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        AssistChip(onClick = {}, enabled = false, label = { Text(current.difficulty.label) })
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.PhotoCamera, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Foto del plato", style = MaterialTheme.typography.titleMedium)
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(onClick = onTakePhoto, modifier = Modifier.fillMaxWidth()) {
                                Icon(Icons.Filled.PhotoCamera, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(if (current.imagePath != null) "Tomar otra foto" else "📷 Tomar foto del plato")
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedButton(
                                onClick = {
                                    pickImageLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(Icons.Filled.Image, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("🖼 Seleccionar imagen de galería")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Ingredientes", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            current.ingredients.forEach { ingredient ->
                                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                    Text("• ", color = MaterialTheme.colorScheme.primary)
                                    Text("${ingredient.name} — ${ingredient.quantity}")
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text("Preparación", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            current.steps.forEachIndexed { index, step ->
                                Row(modifier = Modifier.padding(vertical = 6.dp)) {
                                    Text(
                                        text = "${index + 1}.",
                                        color = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.width(28.dp)
                                    )
                                    Text(step)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
