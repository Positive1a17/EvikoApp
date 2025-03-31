package com.eviko.app.data.repository

import com.eviko.app.data.local.CategoryDao
import com.eviko.app.data.model.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao
) {
    fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories()
    }

    fun getRootCategories(): Flow<List<Category>> {
        return categoryDao.getRootCategories()
    }

    fun getSubCategories(parentId: String): Flow<List<Category>> {
        return categoryDao.getSubCategories(parentId)
    }

    suspend fun getCategoryById(categoryId: String): Category? {
        return categoryDao.getCategoryById(categoryId)
    }

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

    suspend fun insertCategories(categories: List<Category>) {
        categoryDao.insertCategories(categories)
    }

    suspend fun updateCategory(category: Category) {
        categoryDao.updateCategory(category)
    }

    suspend fun deleteCategory(category: Category) {
        categoryDao.deleteCategory(category)
    }

    suspend fun deleteAllCategories() {
        categoryDao.deleteAllCategories()
    }
} 