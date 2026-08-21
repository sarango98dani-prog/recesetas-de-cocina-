package com.recetario.app.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.recetario.app.domain.model.ChefRecipe
import com.recetario.app.domain.model.Difficulty
import com.recetario.app.domain.model.Ingredient
import com.recetario.app.viewmodel.AddChefRecipeViewModel
import com.recetario.app.viewmodel.AppViewModelProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

private val CATEGORIES = listOf("Entradas", "Platos principales", "Postres", "Bebidas", "Cocina tradicional")

private data class IngredientField(val name: String = "", val quantity: String = "")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChefRecipeScreen(
    onBack: () -> Unit,
    onSaved: () -> Unit,
    onTakePhoto: () -> Unit,
    capturedPhotoPath: String?,
    onCapturedPhotoConsumed: () -> Unit,
    viewModel: AddChefRecipeViewModel = viewModel(factory = AppViewModelProvider.AddChefRecipeFactory)
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(CATEGORIES.first()) }
    var selectedDifficulty by remember { mutableStateOf(Difficulty.FACIL) }
    var prepTime by remember { mutableStateOf("") }
    var imagePath by remember { mutableStateOf<String?>(null) }
    val ingredients = remember { mutableStateListOf(IngredientField()) }
    val steps = remember { mutableStateListOf("") }

    LaunchedEffect(capturedPhotoPath) {
        if (capturedPhotoPath != null) {
            imagePath = capturedPhotoPath
            onCapturedPhotoConsumed()
        }
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            coroutineScope.launch {
                val copiedPath = withContext(Dispatchers.IO) {
                    runCatching {
                        val file = File(context.filesDir, "chef_recipe_${System.currentTimeMillis()}.jpg")
                        context.contentResolver.openInputStream(uri)?.use { input ->
                            file.outputStream().use { output -> input.copyTo(output) }
                        }
                        file.absolutePath
                    }.getOrNull()
                }
                if (copiedPath != null) imagePath = copiedPath
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nueva receta") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre de la receta") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Categoría", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CATEGORIES.forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("Fotografía", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            if (imagePath != null) {
                AsyncImage(
                    model = File(imagePath!!),
                    contentDescription = "Foto de la receta",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            Button(
                onClick = onTakePhoto,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Filled.PhotoCamera, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (imagePath != null) "Tomar otra foto" else "📷 Tomar foto con la cámara")
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
                Text(if (imagePath != null) "Cambiar foto" else "🖼 Elegir foto de la galería")
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Ingredientes", style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = { ingredients.add(IngredientField()) }) {
                    Icon(Icons.Filled.AddCircle, contentDescription = "Agregar ingrediente", tint = MaterialTheme.colorScheme.primary)
                }
            }
            ingredients.forEachIndexed { index, field ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = field.name,
                        onValueChange = { ingredients[index] = field.copy(name = it) },
                        label = { Text("Ingrediente") },
                        modifier = Modifier.weight(2f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    OutlinedTextField(
                        value = field.quantity,
                        onValueChange = { ingredients[index] = field.copy(quantity = it) },
                        label = { Text("Cantidad") },
                        modifier = Modifier.weight(1f)
                    )
                    if (ingredients.size > 1) {
                        IconButton(onClick = { ingredients.removeAt(index) }) {
                            Icon(Icons.Filled.RemoveCircle, contentDescription = "Quitar", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Preparación", style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = { steps.add("") }) {
                    Icon(Icons.Filled.AddCircle, contentDescription = "Agregar paso", tint = MaterialTheme.colorScheme.primary)
                }
            }
            steps.forEachIndexed { index, step ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${index + 1}.", modifier = Modifier.padding(end = 8.dp))
                    OutlinedTextField(
                        value = step,
                        onValueChange = { steps[index] = it },
                        label = { Text("Paso") },
                        modifier = Modifier.weight(1f)
                    )
                    if (steps.size > 1) {
                        IconButton(onClick = { steps.removeAt(index) }) {
                            Icon(Icons.Filled.RemoveCircle, contentDescription = "Quitar", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = prepTime,
                onValueChange = { value -> if (value.all { it.isDigit() }) prepTime = value },
                label = { Text("Tiempo de preparación (min)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("Dificultad", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Difficulty.entries.forEach { difficulty ->
                    FilterChip(
                        selected = selectedDifficulty == difficulty,
                        onClick = { selectedDifficulty = difficulty },
                        label = { Text(difficulty.label) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
            Button(
                onClick = {
                    val recipe = ChefRecipe(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        category = selectedCategory,
                        imagePath = imagePath,
                        ingredients = ingredients
                            .filter { it.name.isNotBlank() }
                            .map { Ingredient(it.name, it.quantity) },
                        steps = steps.filter { it.isNotBlank() },
                        prepTimeMinutes = prepTime.toIntOrNull() ?: 0,
                        difficulty = selectedDifficulty,
                        isUserCreated = true
                    )
                    viewModel.saveRecipe(recipe, onSaved = onSaved)
                },
                enabled = name.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Guardar receta", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
