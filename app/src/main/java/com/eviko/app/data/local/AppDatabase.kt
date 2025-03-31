package com.eviko.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eviko.app.data.model.Category
import com.eviko.app.data.model.Product
import com.eviko.app.data.model.User

@Database(
    entities = [
        User::class,
        Category::class,
        Product::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun productDao(): ProductDao
} 