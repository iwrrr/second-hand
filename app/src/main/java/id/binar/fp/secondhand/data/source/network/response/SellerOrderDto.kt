package id.binar.fp.secondhand.data.source.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SellerOrderDto(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("product_id")
    val productId: Int? = null,

    @field:SerializedName("buyer_id")
    val buyerId: Int? = null,

    @field:SerializedName("price")
    val price: Int? = null,

    @field:SerializedName("transaction_date")
    val transactionDate: String? = null,

    @field:SerializedName("product_name")
    val productName: String? = null,

    @field:SerializedName("base_price")
    val basePrice: String? = null,

    @field:SerializedName("image_product")
    val imageProduct: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null,

    @field:SerializedName("Product")
    val product: ProductDto? = null,

    @field:SerializedName("User")
    val user: UserDto? = null
) : Parcelable