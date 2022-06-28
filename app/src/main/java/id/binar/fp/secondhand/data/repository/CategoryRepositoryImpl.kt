package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.data.source.network.response.CategoryDto
import id.binar.fp.secondhand.domain.repository.CategoryRepository
import id.binar.fp.secondhand.util.Result
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CategoryRepository {

    override suspend fun getCategory(): LiveData<Result<List<CategoryDto>>> = liveData {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryById(id: Int): LiveData<Result<CategoryDto>> = liveData {
        TODO("Not yet implemented")
    }
}