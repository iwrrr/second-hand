package id.binar.fp.secondhand.data.source.network.response

import com.google.gson.annotations.SerializedName
import id.binar.fp.secondhand.domain.model.Category

data class CategoryDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null
) {

    fun toDomain(): Category {
        return Category(
            id,
            name,
            createdAt,
            updatedAt
        )
    }
}
