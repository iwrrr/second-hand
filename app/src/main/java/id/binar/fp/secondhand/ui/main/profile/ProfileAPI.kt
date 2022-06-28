package id.binar.fp.secondhand.ui.main.profile

import retrofit2.Call
import retrofit2.http.GET

interface ProfileAPI {
    @GET("/auth/user")
    fun getProfile(): Call<List<DataProfile>>
}