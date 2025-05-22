package com.example.fakestorecompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ProductEntity::class], version = 1)
@TypeConverters(StringToListConverter::class)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}