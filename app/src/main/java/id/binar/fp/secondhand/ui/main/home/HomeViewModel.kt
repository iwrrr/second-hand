package id.binar.fp.secondhand.ui.main.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.binar.fp.secondhand.data.repository.CategoryRepositoryImpl
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CategoryRepositoryImpl
) : ViewModel() {

    fun getCategory() = repository.getCategory()

}