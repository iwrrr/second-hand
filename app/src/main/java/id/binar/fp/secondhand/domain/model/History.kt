package id.binar.fp.secondhand.domain.model

import id.binar.fp.secondhand.data.source.network.response.HistoryDto

data class History(
    val id: Int,
    val productName: String? = null,
    val price: Int? = null,
    val category: String? = null,
    val transactionDate: String? = null,
    val status: String? = null,
    val userId: Int? = null,
    val imageUrl: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
) : BaseType {

    fun toDto(): HistoryDto {
        return HistoryDto(
            id,
            productName,
            price,
            category,
            transactionDate,
            status,
            userId,
            imageUrl,
            createdAt,
            updatedAt
        )
    }
}
