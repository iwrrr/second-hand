package id.binar.fp.secondhand.data.source.network.response

import com.google.gson.annotations.SerializedName

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
)