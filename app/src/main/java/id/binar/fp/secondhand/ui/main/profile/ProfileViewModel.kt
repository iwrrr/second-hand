package id.binar.fp.secondhand.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel

import id.binar.fp.secondhand.data.source.network.response.UserDto
import id.binar.fp.secondhand.domain.repository.AuthRepository
import id.binar.fp.secondhand.util.Result
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository,
): ViewModel(){

    private val user = MutableLiveData<id.binar.fp.secondhand.util.Result<UserDto>>()

//    fun getProfile() = repository.getUser()

    fun editProfile(
        fullName: String,
        phoneNumber: String,
        city: String,
        address: String,
        image: File
    ): LiveData<Result<UserDto>>{
        viewModelScope.launch {
            val requestImageFile = image.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData (
                "photo",
                image.name,
                requestImageFile
            )

            val result = repository.updateUser(fullName, phoneNumber, city, address, imageMultipart)

            try {
                user.value = result.value
            } catch (e: Exception){
                user.value = id.binar.fp.secondhand.util.Result.Error(e.message.toString())
            }
        }
        return user
    }
}