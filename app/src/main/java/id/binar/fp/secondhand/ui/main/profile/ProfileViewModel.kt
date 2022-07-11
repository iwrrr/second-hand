package id.binar.fp.secondhand.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun editProfile(
        fullName: String,
        phoneNumber: String,
        city: String,
        address: String,
        image: File?
    ): LiveData<Result<User>> {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("full_name", fullName)
            .addFormDataPart("phone_number", phoneNumber)
            .addFormDataPart("city", city)
            .addFormDataPart("address", address)

        if (image != null) {
            val requestImageFile = image.asRequestBody("image/jpeg".toMediaType())
            requestBody.addFormDataPart("image", image.name, requestImageFile)
        }

        return repository.updateUser(requestBody.build())
    }
}