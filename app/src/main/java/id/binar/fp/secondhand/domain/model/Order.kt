package id.binar.fp.secondhand.domain.model

data class Order(
    val id: Int? = null,
    val productId: Int? = null,
    val buyerId: Int? = null,
    val price: Int? = null,
    val status: String? = null,
    val product: Product? = null,
    val user: User? = null,
)