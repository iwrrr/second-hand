package id.binar.fp.secondhand.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.binar.fp.secondhand.data.source.local.entity.BuyerOrderEntity

@Dao
interface BuyerOrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuyerOrders(orders: List<BuyerOrderEntity>)

    @Query("SELECT * FROM buyer_orders ORDER BY updated_at DESC")
    suspend fun getBuyerOrder(): List<BuyerOrderEntity>

    @Query("SELECT * FROM buyer_orders WHERE id = :id")
    suspend fun getBuyerOrderById(id: Int): BuyerOrderEntity?

    @Query("DELETE FROM buyer_orders")
    suspend fun deleteBuyerOrders()
}