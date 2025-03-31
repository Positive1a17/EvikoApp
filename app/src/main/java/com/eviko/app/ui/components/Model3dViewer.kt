package com.eviko.app.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.filament.Engine
import com.google.android.filament.View
import com.google.android.filament.utils.ModelViewer
import com.google.android.filament.utils.ktx.createModelViewer
import com.google.android.filament.utils.ktx.destroyModelViewer
import com.google.android.filament.utils.ktx.loadGlb
import com.google.android.filament.utils.ktx.transformToUnitCube
import kotlinx.coroutines.launch

@Composable
fun Model3dViewer(
    modelUrl: String,
    modifier: Modifier = Modifier
) {
    var isDarkMode by remember { mutableStateOf(false) }
    var modelViewer by remember { mutableStateOf<ModelViewer?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(modelUrl) {
        scope.launch {
            modelViewer = createModelViewer().apply {
                loadGlb(context, modelUrl)
                transformToUnitCube()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            modelViewer?.destroyModelViewer()
        }
    }

    Box(modifier = modifier) {
        // TODO: Implement Filament View rendering
        
        IconButton(
            onClick = { isDarkMode = !isDarkMode },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
        ) {
            Icon(
                imageVector = if (isDarkMode) Icons.Default.DarkMode else Icons.Default.LightMode,
                contentDescription = if (isDarkMode) "Switch to light mode" else "Switch to dark mode",
                tint = if (isDarkMode) Color.White else Color.Black
            )
        }
    }
} 