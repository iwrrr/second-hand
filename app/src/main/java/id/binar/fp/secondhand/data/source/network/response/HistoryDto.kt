package id.binar.fp.secondhand.data.source.network.response

import com.google.gson.annotations.SerializedName

data class HistoryDto(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("product_name")
    val productName: String? = null,

    @SerializedName("price")
    val price: Int? = null,

    @SerializedName("category")
    val category: String? = null,

    @SerializedName("transaction_date")
    val transactionDate: String? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("user_id")
    val userId: Int? = null,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null
)
