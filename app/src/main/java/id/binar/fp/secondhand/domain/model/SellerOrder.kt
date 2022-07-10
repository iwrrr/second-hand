package id.binar.fp.secondhand.domain.model

import android.os.Parcelable
import id.binar.fp.secondhand.BaseType
import id.binar.fp.secondhand.data.source.network.response.SellerOrderDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellerOrder(
    val id: Int,
    val productId: Int? = null,
    val buyerId: Int? = null,
    val price: Int? = null,
    val transactionDate: String? = null,
    val productName: String? = null,
    val basePrice: Int? = null,
    val imageProduct: String? = null,
    val status: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val product: Product? = null,
    val user: User? = null
) : Parcelable, BaseType {

    fun toDto(): SellerOrderDto {
        return SellerOrderDto(
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
            product?.toDto(),
            user?.toDto()
        )
    }
}