package id.binar.fp.secondhand.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.binar.fp.secondhand.data.source.local.entity.OrderEntity

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(orders: List<OrderEntity>)

    @Query("SELECT * FROM orders")
    suspend fun getBuyerOrder(): List<OrderEntity>

    @Query("SELECT * FROM orders WHERE id = :id")
    suspend fun getBuyerOrderById(id: Int): OrderEntity

    @Query("DELETE FROM orders")
    suspend fun deleteOrders()
}