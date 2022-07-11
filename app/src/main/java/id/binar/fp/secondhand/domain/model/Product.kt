package id.binar.fp.secondhand.domain.model

import android.os.Parcelable
import id.binar.fp.secondhand.data.source.network.response.ProductDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int = 0,
    val name: String? = null,
    val basePrice: Int = 0,
    val description: String? = null,
    val imageUrl: String? = null,
    val imageName: String? = null,
    val location: String? = null,
    val user: User? = null,
    val status: String? = null,
    val categories: List<Category>? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
) : Parcelable, BaseType {

    fun toDto(): ProductDto {
        val categories = categories?.map { it.toDto() }
        return ProductDto(
            id,
            name,
            basePrice,
            description,
            imageUrl,
            imageName,
            location,
            user?.toDto(),
            status,
            categories,
            createdAt,
            updatedAt
        )
    }
}