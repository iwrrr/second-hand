package id.binar.fp.secondhand.data.source.network.response

import com.google.gson.annotations.SerializedName
import id.binar.fp.secondhand.data.source.local.entity.NotificationEntity
import id.binar.fp.secondhand.domain.model.Notification

data class NotificationDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("product_id")
    val productId: Int? = null,

    @SerializedName("product_name")
    val productName: String? = null,

    @SerializedName("base_price")
    val basePrice: String? = null,

    @SerializedName("bid_price")
    val bidPrice: Int? = null,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("transaction_date")
    val transactionDate: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("seller_name")
    val sellerName: String? = null,

    @SerializedName("buyer_name")
    val buyerName: String? = null,

    @SerializedName("receiver_id")
    val receiverId: Int? = null,

    @SerializedName("read")
    val read: Boolean = false,

    @SerializedName("notification_type")
    val notificationType: String? = null,

    @SerializedName("order_id")
    val orderId: Int? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null,

    @SerializedName("Product")
    val product: ProductDto? = null,

    @SerializedName("User")
    val user: UserDto? = null,
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

    fun toEntity(): NotificationEntity {
        return NotificationEntity(
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
            product?.toEntity(),
            user?.toEntity(),
        )
    }
}
