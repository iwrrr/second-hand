package id.binar.fp.secondhand.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.binar.fp.secondhand.data.source.network.response.MessageDto
import id.binar.fp.secondhand.domain.model.User
import id.binar.fp.secondhand.domain.repository.AuthRepository
import id.binar.fp.secondhand.util.Result
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository,
) : ViewModel() {

    fun updateProfile(
        fullName: String? = null,
        phoneNumber: String? = null,
        city: String? = null,
        address: String? = null,
        email: String? = null,
        image: File? = null
    ): LiveData<Result<User>> {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)

        if (fullName != null) {
            requestBody.addFormDataPart("full_name", fullName)
        }

        if (phoneNumber != null) {
            requestBody.addFormDataPart("phone_number", phoneNumber)
        }

        if (city != null) {
            requestBody.addFormDataPart("city", city)
        }

        if (address != null) {
            requestBody.addFormDataPart("address", address)
        }

        if (email != null) {
            requestBody.addFormDataPart("email", email)
        }

        if (image != null) {
            val requestImageFile = image.asRequestBody("image/jpeg".toMediaType())
            requestBody.addFormDataPart("image", image.name, requestImageFile)
        }

        return repository.updateProfile(requestBody.build())
    }

    fun changePassword(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): LiveData<Result<MessageDto>> {
        val requestBody = MultipartBody.Builder()
            .addFormDataPart("current_password", currentPassword)
            .addFormDataPart("new_password", newPassword)
            .addFormDataPart("confirm_password", confirmPassword)
            .setType(MultipartBody.FORM)
            .build()

        return repository.changePassword(requestBody)
    }
}