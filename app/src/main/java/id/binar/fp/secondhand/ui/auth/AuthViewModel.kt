package id.binar.fp.secondhand.ui.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.binar.fp.secondhand.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    fun getToken() = repository.getToken()

    fun getUser() = repository.getUser()

    fun register(
        name: String,
        email: String,
        password: String,
        phoneNumber: String,
        city: String,
        address: String
    ) = repository.register(name, email, password, phoneNumber, city, address)

    fun login(email: String, password: String) = repository.login(email, password)

    fun logout() = repository.logout()
}