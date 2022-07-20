package id.binar.fp.secondhand.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BuyerOrder(
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
) : Parcelable, BaseType