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

sealed class CatalogUiState {
    object Initial : CatalogUiState()
    object Loading : CatalogUiState()
    data class Success(
        val categories: List<Category>,
        val products: List<Product>
    ) : CatalogUiState()
    data class Error(val message: String) : CatalogUiState()
}

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CatalogUiState>(CatalogUiState.Initial)
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Long?>(null)
    val selectedCategory: StateFlow<Long?> = _selectedCategory.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _uiState.value = CatalogUiState.Loading
            try {
                val categories = categoryRepository.getAllCategories()
                val products = productRepository.getAllProducts()
                _uiState.value = CatalogUiState.Success(categories, products)
            } catch (e: Exception) {
                _uiState.value = CatalogUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        // TODO: Implement search filtering
    }

    fun selectCategory(categoryId: Long) {
        _selectedCategory.value = categoryId
        // TODO: Implement category filtering
    }

    fun toggleFavorite(productId: Long, isFavorite: Boolean) {
        viewModelScope.launch {
            try {
                productRepository.updateProductFavoriteStatus(productId, isFavorite)
                loadData() // Reload data to update UI
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
} 