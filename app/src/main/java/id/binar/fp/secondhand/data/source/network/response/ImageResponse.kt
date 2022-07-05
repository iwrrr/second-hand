package id.binar.fp.secondhand.data.source.network.response

import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @field:SerializedName("error")
    val error: Boolean = false,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("image")
    val imageUrl: UserDto
)