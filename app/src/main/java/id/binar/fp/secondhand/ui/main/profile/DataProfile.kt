package id.binar.fp.secondhand.ui.main.profile

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DataProfile {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("full_name")
    @Expose
    var full_name: String? = null

    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("phone_number")
    @Expose
    var phone_number: Int? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("image_url")
    @Expose
    var image_url: Int? = null

    @SerializedName("city")
    @Expose
    var city: String? = null

    @SerializedName("createdAt")
    @Expose
    var createdAt: Int? = null

    @SerializedName("updateAt")
    @Expose
    var updateAt: Int? = null
}