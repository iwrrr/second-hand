package id.binar.fp.secondhand.data.source.network.response

import com.google.gson.annotations.SerializedName

data class MessageDto(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("message")
    val message: String? = null
)
