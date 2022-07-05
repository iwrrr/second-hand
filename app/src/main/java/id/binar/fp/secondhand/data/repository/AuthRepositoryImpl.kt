package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.data.source.network.response.UserDto
import id.binar.fp.secondhand.domain.repository.AuthRepository
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.util.UserPreferences
import okhttp3.MultipartBody
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
    ): LiveData<Result<UserDto>> = liveData {
        emit(Result.Loading)
        try {
            apiService.register(fullName, email, password, phoneNumber, city, address)

            val user = apiService.login(email, password)
            user.accessToken?.let { prefs.login(it) }

            emit(Result.Success(user))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun login(email: String, password: String): LiveData<Result<UserDto>> = liveData {
        emit(Result.Loading)
        try {
            val user = apiService.login(email, password)
            user.accessToken?.let { prefs.login(it) }

            emit(Result.Success(user))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun getUser(): LiveData<Result<UserDto>> = liveData {
        emit(Result.Loading)
        try {
            val user = apiService.getUser()
            emit(Result.Success(user))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }


    override fun updateUser(
        fullName: String,
        phoneNumber: String,
        city: String,
        address: String,
        image: MultipartBody.Part
    ): LiveData<Result<UserDto>> = liveData {
        emit(Result.Loading)
        try {
            val user =
                apiService.updateUser(fullName, phoneNumber, city, address, image)
            emit(Result.Success(user))
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