package com.eviko.app.data.repository

import com.eviko.app.data.local.UserDao
import com.eviko.app.data.model.User
import com.eviko.app.data.model.UserRole
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    // TODO: В будущем добавить шифрование данных перед сохранением
    suspend fun registerUser(email: String, password: String, name: String? = null): Result<User> {
        return try {
            if (userDao.getUserByEmail(email) != null) {
                Result.failure(Exception("User with this email already exists"))
            } else {
                val user = User(
                    id = UUID.randomUUID().toString(),
                    email = email,
                    password = password, // TODO: Добавить хеширование
                    name = name
                )
                userDao.insertUser(user)
                Result.success(user)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<User> {
        return try {
            val user = userDao.getUserByCredentials(email, password)
            if (user != null) {
                // TODO: В будущем добавить обновление токена
                userDao.updateUser(user.copy(lastLoginAt = System.currentTimeMillis()))
                Result.success(user)
            } else {
                Result.failure(Exception("Invalid credentials"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getUserById(userId: String): Flow<User?> {
        return userDao.getUserById(userId)
    }

    suspend fun updateUser(user: User): Result<Unit> {
        return try {
            userDao.updateUser(user)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteUser(userId: String): Result<Unit> {
        return try {
            userDao.deleteUser(userId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUser(): Flow<User?> {
        return userDao.getCurrentUser()
    }

    suspend fun login(username: String, password: String): User? {
        return userDao.getUserByCredentials(username, password)
    }

    suspend fun register(user: User) {
        userDao.insertUser(user)
    }

    suspend fun logout() {
        userDao.clearCurrentUser()
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    suspend fun isAdmin(username: String, password: String): Boolean {
        return userDao.getUserByCredentials(username, password)?.role == UserRole.ADMIN
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

    suspend fun createUser(email: String, password: String, username: String): Result<User> {
        return try {
            val user = User(
                email = email,
                password = password,
                username = username,
                role = UserRole.USER,
                createdAt = System.currentTimeMillis()
            )
            userDao.insertUser(user)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateLastLogin(userId: String): Result<Unit> {
        return try {
            userDao.updateLastLogin(userId, System.currentTimeMillis())
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getCurrentUser(userId: String): Flow<User?> {
        return userDao.getUserById(userId)
    }

    suspend fun updateUser(user: User): Result<Unit> {
        return try {
            userDao.updateUser(user)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteUser(userId: String): Result<Unit> {
        return try {
            userDao.deleteUser(userId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
} 