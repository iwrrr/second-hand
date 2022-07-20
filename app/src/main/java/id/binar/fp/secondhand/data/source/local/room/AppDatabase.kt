package id.binar.fp.secondhand.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.binar.fp.secondhand.data.source.local.entity.*
import id.binar.fp.secondhand.data.source.local.room.dao.*

@Database(
    entities = [
        UserEntity::class,
        CategoryEntity::class,
        HistoryEntity::class,
        NotificationEntity::class,
        OrderEntity::class,
        SellerOrderEntity::class,
        BuyerOrderEntity::class,
        ProductEntity::class,
        BannerEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun historyDao(): HistoryDao
    abstract fun notificationDao(): NotificationDao
    abstract fun sellerOrderDao(): SellerOrderDao
    abstract fun buyerOrderDao(): BuyerOrderDao
    abstract fun productDao(): ProductDao
}