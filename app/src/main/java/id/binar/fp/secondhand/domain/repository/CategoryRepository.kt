package id.binar.fp.secondhand.domain.repository

import androidx.lifecycle.LiveData
import id.binar.fp.secondhand.data.source.network.response.CategoryDto
import id.binar.fp.secondhand.util.Result

interface CategoryRepository {

    suspend fun getCategory(): LiveData<Result<List<CategoryDto>>>

    suspend fun getCategoryById(id: Int): LiveData<Result<CategoryDto>>
}