package id.binar.fp.secondhand.domain.model

import id.binar.fp.secondhand.data.source.network.response.OrderDto

data class Order(
    val id: Int? = null,
    val productId: Int? = null,
    val buyerId: Int? = null,
    val price: Int? = null,
    val status: String? = null,
    val product: Product? = null,
    val user: User? = null,
) {

    fun toDto(): OrderDto {
        return OrderDto(
            id,
            productId,
            buyerId,
            price,
            status,
            product?.toDto(),
            user?.toDto()
        )
    }
}