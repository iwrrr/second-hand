package id.binar.fp.secondhand.domain.model

import id.binar.fp.secondhand.data.source.local.entity.NotificationEntity

data class Notification(
    val id: Int,
    val productId: Int? = null,
    val productName: String? = null,
    val basePrice: String? = null,
    val bidPrice: Int? = null,
    val imageUrl: String? = null,
    val transactionDate: String? = null,
    val status: String? = null,
    val sellerName: String? = null,
    val buyerName: String? = null,
    val receiverId: Int? = null,
    var read: Boolean = false,
    val notificationType: String? = null,
    val orderId: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val product: Product? = null,
    val user: User? = null,
) : BaseType {

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
