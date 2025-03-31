package com.eviko.app.ui.screens.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eviko.app.data.model.Category
import com.eviko.app.data.model.Product
import com.eviko.app.data.repository.CategoryRepository
import com.eviko.app.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CatalogUiState>(CatalogUiState.Loading)
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    init {
        loadCategories()
        loadProducts()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categoryRepository.getAllCategories()
                .catch { e ->
                    _uiState.value = CatalogUiState.Error(e.message ?: "Unknown error")
                }
                .collect { categories ->
                    _uiState.value = CatalogUiState.Success(
                        categories = categories,
                        products = emptyList()
                    )
                }
        }
    }

    private fun loadProducts() {
        viewModelScope.launch {
            productRepository.getAllProducts()
                .catch { e ->
                    _uiState.value = CatalogUiState.Error(e.message ?: "Unknown error")
                }
                .collect { products ->
                    _uiState.value = when (val currentState = _uiState.value) {
                        is CatalogUiState.Success -> currentState.copy(products = products)
                        else -> CatalogUiState.Success(categories = emptyList(), products = products)
                    }
                }
        }
    }

    fun selectCategory(category: Category?) {
        _selectedCategory.value = category
    }

    fun toggleFavorite(productId: String) {
        viewModelScope.launch {
            productRepository.toggleFavorite(productId)
        }
    }
}

sealed class CatalogUiState {
    object Loading : CatalogUiState()
    data class Success(
        val categories: List<Category>,
        val products: List<Product>
    ) : CatalogUiState()
    data class Error(val message: String) : CatalogUiState()
} 