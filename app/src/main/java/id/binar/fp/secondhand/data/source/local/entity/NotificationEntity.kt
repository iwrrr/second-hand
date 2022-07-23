package id.binar.fp.secondhand.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.binar.fp.secondhand.domain.model.Notification

@Entity(tableName = "notifications")
data class NotificationEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "product_id")
    val productId: Int? = null,

    @ColumnInfo(name = "product_name")
    val productName: String? = null,

    @ColumnInfo(name = "base_price")
    val basePrice: String? = null,

    @ColumnInfo(name = "bid_price")
    val bidPrice: Int? = null,

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,

    @ColumnInfo(name = "transaction_date")
    val transactionDate: String? = null,

    @ColumnInfo(name = "status")
    val status: String? = null,

    @ColumnInfo(name = "seller_name")
    val sellerName: String? = null,

    @ColumnInfo(name = "buyer_name")
    val buyerName: String? = null,

    @ColumnInfo(name = "receiver_id")
    val receiverId: Int? = null,

    @ColumnInfo(name = "read")
    val read: Boolean = false,

    @ColumnInfo(name = "notification_type")
    val notificationType: String? = null,

    @ColumnInfo(name = "order_id")
    val orderId: Int? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null,

    @ColumnInfo(name = "product")
    val product: ProductEntity? = null,

    @ColumnInfo(name = "user")
    val user: UserEntity? = null,
) {

    fun toDomain(): Notification {
        return Notification(
            id,
            productId,
            productName,
            basePrice,
            bidPrice,
            imageUrl,
            transactionDate,
            status,
            sellerName,
            buyerName,
            receiverId,
            read,
            notificationType,
            orderId,
            createdAt,
            updatedAt,
            product?.toDomain(),
            user?.toDomain(),
        )
    }
}
