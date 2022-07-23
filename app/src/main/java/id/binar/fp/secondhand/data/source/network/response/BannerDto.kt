package id.binar.fp.secondhand.data.source.network.response

import com.google.gson.annotations.SerializedName
import id.binar.fp.secondhand.data.source.local.entity.BannerEntity
import id.binar.fp.secondhand.domain.model.Banner

data class BannerDto(

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("image_url")
    val imageUrl: String,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null
) {

    fun toEntity(): BannerEntity {
        return BannerEntity(
            id, name, imageUrl, createdAt, updatedAt
        )
    }

    fun toDomain(): Banner {
        return Banner(
            id, name, imageUrl, createdAt, updatedAt
        )
    }
}
