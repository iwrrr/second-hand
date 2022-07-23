package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.local.room.dao.CategoryDao
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.domain.model.Category
import id.binar.fp.secondhand.domain.repository.CategoryRepository
import id.binar.fp.secondhand.util.Result
import retrofit2.HttpException
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override fun getCategory(): LiveData<Result<List<Category>>> = liveData {
        emit(Result.Loading())

        val oldCategory = categoryDao.getCategory().map { it.toDomain() }
        emit(Result.Loading(data = oldCategory))

        try {
            val data = apiService.getCategory().map { it.toEntity() }
            categoryDao.deleteCategories()
            categoryDao.insertCategories(data)
        } catch (e: HttpException) {
            emit(Result.Error(e.message(), oldCategory))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found", oldCategory))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error", oldCategory))
        }

        val newCategory = categoryDao.getCategory().map { it.toDomain() }
        emit(Result.Success(newCategory))
    }
}