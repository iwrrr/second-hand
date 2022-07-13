package id.binar.fp.secondhand.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import id.binar.fp.secondhand.domain.model.Order

@Entity(tableName = "orders")
data class OrderEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "product_id")
    val productId: Int? = null,

    @ColumnInfo(name = "buyer_id")
    val buyerId: Int? = null,

    @ColumnInfo(name = "price")
    val price: Int? = null,

    @ColumnInfo(name = "status")
    val status: String? = null,

    @Expose
    @ColumnInfo(name = "product")
    val product: ProductEntity? = null,

    @Expose
    @ColumnInfo(name = "user")
    val user: UserEntity? = null,
) {

    fun toDomain(): Order {
        return Order(
            id,
            productId,
            buyerId,
            price,
            status,
            product?.toDomain(),
            user?.toDomain()
        )
    }
}