package id.binar.fp.secondhand.data.source.network.response

import com.google.gson.annotations.SerializedName

data class CategoryDto(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null
)
