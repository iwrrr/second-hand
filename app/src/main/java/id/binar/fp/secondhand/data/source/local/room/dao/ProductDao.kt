package id.binar.fp.secondhand.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.binar.fp.secondhand.data.source.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSellerProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products WHERE user_id = :userId")
    suspend fun getSellerProduct(userId: Int): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id AND user_id = :userId")
    suspend fun getSellerProductById(id: Int, userId: Int): ProductEntity

    @Query("DELETE FROM products WHERE user_id = :userId")
    suspend fun deleteSellerProduct(userId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuyerProducts(products: List<ProductEntity>)

    @Query("SELECT * FROM products")
    suspend fun getBuyerProduct(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getBuyerProductById(id: Int): List<ProductEntity>

    @Query("DELETE FROM products")
    suspend fun deleteBuyerProduct()
}