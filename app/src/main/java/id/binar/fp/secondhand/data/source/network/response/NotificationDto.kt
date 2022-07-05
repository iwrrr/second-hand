package id.binar.fp.secondhand.data.source.network.response

import com.google.gson.annotations.SerializedName

data class NotificationDto(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("product_id")
    val productId: Int? = null,

    @SerializedName("bid_price")
    val bidPrice: Int? = null,

    @SerializedName("transaction_date")
    val transactionDate: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("seller_name")
    val sellerName: String? = null,

    @SerializedName("buyer_name")
    val buyerName: String? = null,

    @SerializedName("receiver_id")
    val receiverId: Int? = null,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("read")
    val read: Boolean? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,
)
