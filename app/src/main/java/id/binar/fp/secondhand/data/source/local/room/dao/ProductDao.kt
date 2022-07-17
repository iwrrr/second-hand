package id.binar.fp.secondhand.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.binar.fp.secondhand.data.source.local.entity.BannerEntity
import id.binar.fp.secondhand.data.source.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBanner(banner: List<BannerEntity>)

    @Query("SELECT * FROM banner")
    suspend fun getBanner(): List<BannerEntity>

    @Query("DELETE FROM banner")
    suspend fun deleteBanner()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSellerProducts(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSellerProduct(product: ProductEntity)

    @Query("SELECT * FROM products")
    suspend fun getSellerProduct(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getSellerProductById(id: Int): ProductEntity?

    @Query("DELETE FROM products")
    suspend fun deleteSellerProduct()

    @Query("DELETE FROM products WHERE id = :id")
    suspend fun deleteSellerProductById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuyerProducts(products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBuyerProduct(product: ProductEntity)

    @Query("SELECT * FROM products WHERE name LIKE '%' || :query || '%'")
    suspend fun searchProducts(query: String): List<ProductEntity>

    @Query("SELECT * FROM products ORDER BY RANDOM()")
    suspend fun getBuyerProduct(): List<ProductEntity>

    @Query("SELECT * FROM products WHERE id = :id")
    suspend fun getBuyerProductById(id: Int): ProductEntity?

    @Query("DELETE FROM products")
    suspend fun deleteBuyerProduct()
}