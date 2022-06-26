package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import id.binar.fp.secondhand.data.source.network.response.UserDto
import id.binar.fp.secondhand.util.Result
import okhttp3.MultipartBody

interface AuthRepository {

    fun register(
        fullName: String,
        email: String,
        password: String,
        phoneNumber: Int,
        city: String,
        address: String,
    ): LiveData<Result<UserDto>>

    fun login(
        email: String,
        password: String,
    ): LiveData<Result<UserDto>>

    fun getUser(): LiveData<Result<UserDto>>

    fun updateUser(
        fullName: String,
        email: String,
        password: String,
        phoneNumber: Int,
        city: String,
        address: String,
        image: MultipartBody.Part,
    ): LiveData<Result<UserDto>>

    fun logout(): LiveData<Unit>

    fun getToken(): LiveData<String?>
}