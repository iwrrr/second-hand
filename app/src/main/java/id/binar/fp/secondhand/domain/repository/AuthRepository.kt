package id.binar.fp.secondhand.domain.repository

import androidx.lifecycle.LiveData
import id.binar.fp.secondhand.domain.model.User
import id.binar.fp.secondhand.util.Result
import okhttp3.RequestBody

interface AuthRepository {

    fun register(
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String,
        city: String,
        address: String,
    ): LiveData<Result<User>>

    fun login(
        email: String,
        password: String,
    ): LiveData<Result<User>>

    fun getUser(): LiveData<Result<User>>

    fun updateProfile(
        body: RequestBody,
    ): LiveData<Result<User>>

    fun logout(): LiveData<Unit>

    fun getToken(): LiveData<String?>
}