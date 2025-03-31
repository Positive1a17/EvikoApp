package com.eviko.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val categoryId: String,
    val imageUrls: List<String>,
    val model3dUrl: String? = null,
    val specifications: Map<String, String> = emptyMap(),
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val isFavorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) 