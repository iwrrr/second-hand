package id.binar.fp.secondhand.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import id.binar.fp.secondhand.domain.model.BuyerOrder

@Entity(tableName = "buyer_orders")
data class BuyerOrderEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "product_id")
    val productId: Int? = null,

    @ColumnInfo(name = "buyer_id")
    val buyerId: Int? = null,

    @ColumnInfo(name = "price")
    val price: Int? = null,

    @ColumnInfo(name = "transaction_date")
    val transactionDate: String? = null,

    @ColumnInfo(name = "product_name")
    val productName: String? = null,

    @ColumnInfo(name = "base_price")
    val basePrice: Int? = null,

    @ColumnInfo(name = "image_product")
    val imageProduct: String? = null,

    @ColumnInfo(name = "status")
    val status: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null,

    @Expose
    @ColumnInfo(name = "product")
    val product: ProductEntity? = null,

    @Expose
    @ColumnInfo(name = "user")
    val user: UserEntity? = null
) {

    fun toDomain(): BuyerOrder {
        return BuyerOrder(
            id,
            productId,
            buyerId,
            price,
            transactionDate,
            productName,
            basePrice,
            imageProduct,
            status,
            createdAt,
            updatedAt,
            product?.toDomain(),
            user?.toDomain()
        )
    }
}