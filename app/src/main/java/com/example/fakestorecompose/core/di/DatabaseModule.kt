package com.example.fakestorecompose.core.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.fakestorecompose.database.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val BD_NAME = "products-db"

    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ProductDatabase::class.java, BD_NAME).build();

    @Singleton
    @Provides
    fun providesDao(db: ProductDatabase) = db.productDao();
}