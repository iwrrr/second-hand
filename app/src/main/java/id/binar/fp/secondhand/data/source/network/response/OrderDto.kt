package id.binar.fp.secondhand.data.source.network.response

import com.google.gson.annotations.SerializedName
import id.binar.fp.secondhand.domain.model.Order

data class OrderDto(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("product_id")
    val productId: Int? = null,

    @SerializedName("buyer_id")
    val buyerId: Int? = null,

    @SerializedName("price")
    val price: Int? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("Product")
    val product: ProductDto? = null,

    @SerializedName("User")
    val user: UserDto? = null,
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