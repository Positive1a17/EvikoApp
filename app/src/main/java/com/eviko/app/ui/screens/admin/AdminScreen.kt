package com.eviko.app.ui.screens.admin

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.eviko.app.R
import com.eviko.app.data.model.Product
import com.eviko.app.ui.components.ProductItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    navController: NavController,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.admin_panel)) },
                actions = {
                    IconButton(onClick = { showAddDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Add product")
                    }
                }
            )
        }
    ) { padding ->
        when (uiState) {
            is AdminUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is AdminUiState.Success -> {
                val state = uiState as AdminUiState.Success
                
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    items(state.products) { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                ProductItem(
                                    product = product,
                                    onClick = { selectedProduct = product },
                                    onFavoriteClick = { /* Not used in admin panel */ }
                                )
                                
                                Row {
                                    IconButton(onClick = { selectedProduct = product }) {
                                        Icon(Icons.Default.Edit, "Edit")
                                    }
                                    IconButton(onClick = { viewModel.deleteProduct(product.id) }) {
                                        Icon(Icons.Default.Delete, "Delete")
                                    }
                                }
                            }
                        }
                    }
                }
            }
            is AdminUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (uiState as AdminUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        ProductEditDialog(
            product = null,
            onDismiss = { showAddDialog = false },
            onSave = { product ->
                viewModel.addProduct(product)
                showAddDialog = false
            }
        )
    }

    selectedProduct?.let { product ->
        ProductEditDialog(
            product = product,
            onDismiss = { selectedProduct = null },
            onSave = { updatedProduct ->
                viewModel.updateProduct(updatedProduct)
                selectedProduct = null
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProductEditDialog(
    product: Product?,
    onDismiss: () -> Unit,
    onSave: (Product) -> Unit
) {
    var name by remember { mutableStateOf(product?.name ?: "") }
    var description by remember { mutableStateOf(product?.description ?: "") }
    var price by remember { mutableStateOf(product?.price?.toString() ?: "") }
    var categoryId by remember { mutableStateOf(product?.categoryId ?: "") }
    var imageUrls by remember { mutableStateOf(product?.imageUrls?.joinToString("\n") ?: "") }
    var model3dUrl by remember { mutableStateOf(product?.model3dUrl ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (product == null) "Add Product" else "Edit Product") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
                
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = categoryId,
                    onValueChange = { categoryId = it },
                    label = { Text("Category ID") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = imageUrls,
                    onValueChange = { imageUrls = it },
                    label = { Text("Image URLs (one per line)") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
                
                OutlinedTextField(
                    value = model3dUrl,
                    onValueChange = { model3dUrl = it },
                    label = { Text("3D Model URL") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(
                        Product(
                            id = product?.id ?: System.currentTimeMillis().toString(),
                            name = name,
                            description = description,
                            price = price.toDoubleOrNull() ?: 0.0,
                            categoryId = categoryId,
                            imageUrls = imageUrls.split("\n").filter { it.isNotBlank() },
                            model3dUrl = model3dUrl.takeIf { it.isNotBlank() }
                        )
                    )
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
} 