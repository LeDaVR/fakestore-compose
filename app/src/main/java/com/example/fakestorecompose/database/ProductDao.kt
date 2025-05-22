package com.example.fakestorecompose.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    suspend fun getAllProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(user: List<ProductEntity>)

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProductByID(id: Int): ProductEntity
}
