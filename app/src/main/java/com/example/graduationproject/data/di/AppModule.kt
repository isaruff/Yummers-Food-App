package com.example.graduationproject.data.di

import android.content.Context
import androidx.room.Room
import com.example.graduationproject.data.datasource.FoodCartDataSource
import com.example.graduationproject.data.datasource.FoodDataSource
import com.example.graduationproject.data.repository.ApiRepository
import com.example.graduationproject.data.repository.RoomRepository
import com.example.graduationproject.data.room.FoodCartDao
import com.example.graduationproject.data.room.FoodCartDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRepository(fds: FoodDataSource): ApiRepository{
        return ApiRepository(fds)
    }

    @Singleton
    @Provides
    fun provideDataSource(): FoodDataSource{
        return FoodDataSource()
    }

    @Singleton
    @Provides
    fun provideRoomRepository(fcds: FoodCartDataSource): RoomRepository{
        return RoomRepository(fcds)
    }

    @Singleton
    @Provides
    fun provideRoomDataSource(fcDao: FoodCartDao): FoodCartDataSource{
        return FoodCartDataSource(fcDao)
    }

    @Singleton
    @Provides
    fun provideFoodCartDao(@ApplicationContext context: Context): FoodCartDao{
        val db = Room.databaseBuilder(context, FoodCartDatabase::class.java, "foodCart.db").build()
        return db.foodDao()

    }
}