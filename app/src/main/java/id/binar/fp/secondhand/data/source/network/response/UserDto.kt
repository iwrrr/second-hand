package id.binar.fp.secondhand.data.source.network.response

import com.google.gson.annotations.SerializedName

data class UserDto(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("full_name")
    val fullName: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("password")
    val password: String? = null,

    @SerializedName("phone_number")
    val phoneNumber: Long? = null,

    @SerializedName("city")
    val city: String? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("image_url")
    val imageUrl: String? = null,

    @SerializedName("createdAt")
    val createdAt: String? = null,

    @SerializedName("updatedAt")
    val updatedAt: String? = null,

    @SerializedName("access_token")
    val accessToken: String? = null,
)
