package id.binar.fp.secondhand.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.binar.fp.secondhand.data.repository.*
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.domain.repository.*
import id.binar.fp.secondhand.util.UserPreferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService, prefs: UserPreferences): AuthRepository {
        return AuthRepositoryImpl(apiService, prefs)
    }

    @Provides
    @Singleton
    fun provideProductRepository(apiService: ApiService): ProductRepository {
        return ProductRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(apiService: ApiService): CategoryRepository {
        return CategoryRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(apiService: ApiService): OrderRepository {
        return OrderRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(apiService: ApiService): HistoryRepository {
        return HistoryRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(apiService: ApiService): NotificationRepository {
        return NotificationRepositoryImpl(apiService)
    }
}