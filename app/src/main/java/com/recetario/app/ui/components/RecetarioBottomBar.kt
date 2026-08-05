package com.recetario.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.recetario.app.navigation.RecetarioDestinations

private data class BottomBarDestination(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val bottomBarDestinations = listOf(
    BottomBarDestination(RecetarioDestinations.RECIPE_LIST, "Buscar", Icons.Filled.Search),
    BottomBarDestination(RecetarioDestinations.FAVORITES, "Favoritos", Icons.Filled.Favorite)
)

@Composable
fun RecetarioBottomBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    NavigationBar {
        bottomBarDestinations.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = { onNavigate(destination.route) },
                icon = { Icon(destination.icon, contentDescription = destination.label) },
                label = { Text(destination.label) }
            )
        }
    }
}
