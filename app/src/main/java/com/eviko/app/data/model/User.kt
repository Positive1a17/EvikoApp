package com.eviko.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class UserRole {
    GUEST,
    USER,
    ADMIN
}

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val id: String,
    val username: String,
    val password: String,
    val role: UserRole = UserRole.USER,
    val email: String? = null,
    val phone: String? = null,
    val isCurrentUser: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) 