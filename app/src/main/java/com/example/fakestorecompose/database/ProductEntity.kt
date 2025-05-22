package com.example.fakestorecompose.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val categoryName: String,
    val description: String,
    val images: List<String>,
    val price: Long,
    val slug: String,
    val title: String
)

class StringToListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return list.joinToString(",")
    }
}