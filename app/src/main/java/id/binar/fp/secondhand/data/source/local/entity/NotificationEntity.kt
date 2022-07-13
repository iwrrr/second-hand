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

    @ColumnInfo(name = "bid_price")
    val bidPrice: Int? = null,

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

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,

    @ColumnInfo(name = "read")
    val read: Boolean = false,

    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null,
) {

    fun toDomain(): Notification {
        return Notification(
            id,
            productId,
            bidPrice,
            transactionDate,
            status,
            sellerName,
            buyerName,
            receiverId,
            imageUrl,
            read,
            createdAt,
            updatedAt
        )
    }
}
