package id.binar.fp.secondhand.data.source.network.response

import com.google.gson.annotations.SerializedName

data class ProductDto(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("base_price")
    val basePrice: Int? = null,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("image_name")
    val imageName: String? = null,

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("user")
    val user: UserDto? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("Categories")
    val categories: List<CategoryDto>? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,
)
