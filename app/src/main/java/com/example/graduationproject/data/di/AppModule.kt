package com.example.graduationproject.data.di

import com.example.graduationproject.data.datasource.FoodDataSource
import com.example.graduationproject.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRepository(fds: FoodDataSource): Repository{
        return Repository(fds)
    }

    @Singleton
    @Provides
    fun provideDataSource(): FoodDataSource{
        return FoodDataSource()
    }
}