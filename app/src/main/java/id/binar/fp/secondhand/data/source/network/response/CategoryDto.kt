package id.binar.fp.secondhand.data.source.network.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import id.binar.fp.secondhand.domain.model.Category
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDto(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null
) : Parcelable {

    fun toDomain(): Category {
        return Category(
            id,
            name,
            createdAt,
            updatedAt
        )
    }
}
