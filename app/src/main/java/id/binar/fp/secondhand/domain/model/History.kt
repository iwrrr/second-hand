package id.binar.fp.secondhand.domain.model

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
) : BaseType
