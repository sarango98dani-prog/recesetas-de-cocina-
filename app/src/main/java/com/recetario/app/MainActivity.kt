package com.recetario.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.recetario.app.navigation.RecetarioNavGraph
import com.recetario.app.ui.theme.RecetarioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RecetarioTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RecetarioNavGraph()
                }
            }
        }
    }
}
