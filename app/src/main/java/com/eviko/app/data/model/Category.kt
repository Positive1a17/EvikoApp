package com.eviko.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val parentId: String? = null,
    val order: Int = 0
) 