package com.eviko.app.ui.screens.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eviko.app.data.model.Product
import com.eviko.app.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AdminUiState {
    object Loading : AdminUiState()
    data class Success(val products: List<Product>) : AdminUiState()
    data class Error(val message: String) : AdminUiState()
}

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AdminUiState>(AdminUiState.Loading)
    val uiState: StateFlow<AdminUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.value = AdminUiState.Loading
            try {
                productRepository.getAllProducts()
                    .collect { products ->
                        _uiState.value = AdminUiState.Success(products)
                    }
            } catch (e: Exception) {
                _uiState.value = AdminUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            try {
                productRepository.insertProduct(product)
            } catch (e: Exception) {
                _uiState.value = AdminUiState.Error(e.message ?: "Failed to add product")
            }
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            try {
                productRepository.updateProduct(product)
            } catch (e: Exception) {
                _uiState.value = AdminUiState.Error(e.message ?: "Failed to update product")
            }
        }
    }

    fun deleteProduct(productId: String) {
        viewModelScope.launch {
            try {
                productRepository.getProductById(productId)?.let { product ->
                    productRepository.deleteProduct(product)
                }
            } catch (e: Exception) {
                _uiState.value = AdminUiState.Error(e.message ?: "Failed to delete product")
            }
        }
    }
} 