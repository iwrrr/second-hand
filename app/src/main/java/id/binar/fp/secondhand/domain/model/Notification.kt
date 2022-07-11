package id.binar.fp.secondhand.domain.model

import id.binar.fp.secondhand.data.source.network.response.NotificationDto

data class Notification(
    val id: Int,
    val productId: Int? = null,
    val bidPrice: Int? = null,
    val transactionDate: String? = null,
    val status: String? = null,
    val sellerName: String? = null,
    val buyerName: String? = null,
    val receiverId: Int? = null,
    val imageUrl: String? = null,
    val read: Boolean = false,
    val createdAt: String? = null,
    val updatedAt: String? = null,
) {

    fun toDto(): NotificationDto {
        return NotificationDto(
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
