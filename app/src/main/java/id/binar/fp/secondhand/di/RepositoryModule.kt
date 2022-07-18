package id.binar.fp.secondhand.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.binar.fp.secondhand.data.repository.*
import id.binar.fp.secondhand.data.source.local.room.dao.*
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.domain.repository.*
import id.binar.fp.secondhand.util.UserPreferences
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService,
        prefs: UserPreferences
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, prefs)
    }

    @Provides
    @Singleton
    fun provideProductRepository(apiService: ApiService, dao: ProductDao): ProductRepository {
        return ProductRepositoryImpl(apiService, dao)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(apiService: ApiService, dao: CategoryDao): CategoryRepository {
        return CategoryRepositoryImpl(apiService, dao)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(
        apiService: ApiService,
        orderDao: OrderDao,
        sellerOrderDao: SellerOrderDao,
        buyerOrderDao: BuyerOrderDao
    ): OrderRepository {
        return OrderRepositoryImpl(apiService, orderDao, sellerOrderDao, buyerOrderDao)
    }

    @Provides
    @Singleton
    fun provideHistoryRepository(apiService: ApiService, dao: HistoryDao): HistoryRepository {
        return HistoryRepositoryImpl(apiService, dao)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(
        apiService: ApiService,
        dao: NotificationDao
    ): NotificationRepository {
        return NotificationRepositoryImpl(apiService, dao)
    }
}