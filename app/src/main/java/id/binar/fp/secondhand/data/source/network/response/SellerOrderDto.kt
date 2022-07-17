package id.binar.fp.secondhand.data.source.network.response

import com.google.gson.annotations.SerializedName
import id.binar.fp.secondhand.data.source.local.entity.SellerOrderEntity
import id.binar.fp.secondhand.domain.model.SellerOrder

data class SellerOrderDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("product_id")
    val productId: Int? = null,

    @SerializedName("buyer_id")
    val buyerId: Int? = null,

    @SerializedName("price")
    val price: Int? = null,

    @SerializedName("transaction_date")
    val transactionDate: String? = null,

    @SerializedName("product_name")
    val productName: String? = null,

    @SerializedName("base_price")
    val basePrice: Int? = null,

    @SerializedName("image_product")
    val imageProduct: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null,

    @SerializedName("Product")
    val product: ProductDto? = null,

    @SerializedName("User")
    val user: UserDto? = null
) {

    fun toDomain(): SellerOrder {
        return SellerOrder(
            id,
            productId,
            buyerId,
            price,
            transactionDate,
            productName,
            basePrice,
            imageProduct,
            status,
            createdAt,
            updatedAt,
            product?.toDomain(),
            user?.toDomain()
        )
    }

    fun toEntity(): SellerOrderEntity {
        return SellerOrderEntity(
            id,
            productId,
            buyerId,
            price,
            transactionDate,
            productName,
            basePrice,
            imageProduct,
            status,
            createdAt,
            updatedAt,
            product?.toEntity(),
            user?.toEntity()
        )
    }
}