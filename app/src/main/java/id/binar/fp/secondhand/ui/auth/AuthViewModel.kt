package id.binar.fp.secondhand.ui.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.binar.fp.secondhand.data.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    fun getToken() = repository.getToken()

    fun login(email: String, password: String) = repository.login(email, password)

    fun logout() = repository.logout()
}