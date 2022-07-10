package id.binar.fp.secondhand.data.source.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import id.binar.fp.secondhand.domain.model.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductDto(

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("base_price")
    val basePrice: Int = 0,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("image_name")
    val imageName: String? = null,

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("User")
    val user: UserDto? = null,

    @SerializedName("status")
    val status: String? = null,

    @SerializedName("Categories")
    val categories: List<CategoryDto>? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null,
) : Parcelable {

    fun toDomain(): Product {
        val categories = categories?.map { it.toDomain() }
        return Product(
            id,
            name,
            basePrice,
            description,
            imageUrl,
            imageName,
            location,
            user?.toDomain(),
            status,
            categories,
            createdAt,
            updatedAt
        )
    }
}