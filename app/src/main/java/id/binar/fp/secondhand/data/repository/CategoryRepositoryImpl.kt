package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.domain.model.Category
import id.binar.fp.secondhand.domain.repository.CategoryRepository
import id.binar.fp.secondhand.util.Result
import retrofit2.HttpException
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : CategoryRepository {

    override fun getCategory(): LiveData<Result<List<Category>>> = liveData {
        emit(Result.Loading)
        try {
            val data = apiService.getCategory()
            emit(Result.Success(data.map { it.toDomain() }))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override suspend fun getCategoryById(id: Int): LiveData<Result<Category>> = liveData {
        TODO("Not yet implemented")
    }
}