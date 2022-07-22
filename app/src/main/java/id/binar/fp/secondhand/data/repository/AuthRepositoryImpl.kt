package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.data.source.network.response.MessageDto
import id.binar.fp.secondhand.domain.model.User
import id.binar.fp.secondhand.domain.repository.AuthRepository
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.util.UserPreferences
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val prefs: UserPreferences
) : AuthRepository {

    override fun register(
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String,
        city: String,
        address: String
    ): LiveData<Result<User>> = liveData {
        emit(Result.Loading())
        try {
            apiService.register(fullName, email, password, phoneNumber, city, address)

            val user = apiService.login(email, password)
            user.accessToken?.let { prefs.login(it) }

            emit(Result.Success(user.toDomain()))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun login(email: String, password: String): LiveData<Result<User>> = liveData {
        emit(Result.Loading())
        try {
            val user = apiService.login(email, password)
            user.accessToken?.let { prefs.login(it) }

            emit(Result.Success(user.toDomain()))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun getUser(): LiveData<Result<User>> = liveData {
        emit(Result.Loading())
        try {
            val user = apiService.getUser()
            emit(Result.Success(user.toDomain()))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }


    override fun updateProfile(
        body: RequestBody
    ): LiveData<Result<User>> = liveData {
        emit(Result.Loading())
        try {
            val user =
                apiService.updateUser(body)
            emit(Result.Success(user.toDomain()))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun changePassword(body: RequestBody): LiveData<Result<MessageDto>> = liveData {
        emit(Result.Loading())
        try {
            val data = apiService.changePassword(body)
            emit(Result.Success(data))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun logout(): LiveData<Unit> = liveData {
        emit(prefs.logout())
    }

    override fun getToken(): LiveData<String?> = liveData {
        emit(prefs.fetchToken())
    }
}