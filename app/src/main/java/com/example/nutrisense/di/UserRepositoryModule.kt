package com.example.nutrisense.di

import com.example.nutrisense.data.repositories.UserRepository
import com.example.nutrisense.data.resource.retrofit.ApiService
import com.example.nutrisense.data.storage.UserPreferenceStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserRepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        apiService: ApiService,
        userPreferenceStore: UserPreferenceStore
    ): UserRepository {
        return UserRepository(apiService, userPreferenceStore)
    }
}
