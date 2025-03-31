package com.eviko.app.data.local

import androidx.room.TypeConverter
import com.eviko.app.data.model.UserRole
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.eviko.app.data.model.Category
import com.eviko.app.data.model.Product

class Converters {
    private val gson = Gson()
    private val categoryListType = object : TypeToken<List<Category>>() {}.type
    private val productListType = object : TypeToken<List<Product>>() {}.type

    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromMap(value: String): Map<String, String> {
        val mapType = object : TypeToken<Map<String, String>>() {}.type
        return gson.fromJson(value, mapType)
    }

    @TypeConverter
    fun toMap(map: Map<String, String>): String {
        return gson.toJson(map)
    }

    @TypeConverter
    fun fromUserRole(role: UserRole): String {
        return role.name
    }

    @TypeConverter
    fun toUserRole(value: String): UserRole {
        return UserRole.valueOf(value)
    }

    @TypeConverter
    fun fromCategoryList(value: List<Category>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toCategoryList(value: String): List<Category> {
        return gson.fromJson(value, categoryListType)
    }

    @TypeConverter
    fun fromProductList(value: List<Product>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toProductList(value: String): List<Product> {
        return gson.fromJson(value, productListType)
    }
} 