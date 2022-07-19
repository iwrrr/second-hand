package id.binar.fp.secondhand.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.binar.fp.secondhand.data.source.local.entity.SellerOrderEntity

@Dao
interface SellerOrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSellerOrders(orders: List<SellerOrderEntity>)

    @Query("SELECT * FROM seller_orders ORDER BY updated_at DESC")
    suspend fun getSellerOrder(): List<SellerOrderEntity>

    @Query("SELECT * FROM seller_orders WHERE id = :id")
    suspend fun getSellerOrderById(id: Int): SellerOrderEntity?

    @Query("DELETE FROM seller_orders")
    suspend fun deleteSellerOrders()
}