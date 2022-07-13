package id.binar.fp.secondhand.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import id.binar.fp.secondhand.domain.model.SellerOrder

@Entity(tableName = "seller_orders")
data class SellerOrderEntity(

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

    @ColumnInfo(name = "createdAt")
    val createdAt: String? = null,

    @ColumnInfo(name = "updatedAt")
    val updatedAt: String? = null,

    @Expose
    @ColumnInfo(name = "product")
    val product: ProductEntity? = null,

    @Expose
    @ColumnInfo(name = "user")
    val user: UserEntity? = null
) {

    fun toDomain(): SellerOrder {
        return SellerOrder(
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