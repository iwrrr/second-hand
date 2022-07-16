package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.data.source.network.response.MessageDto
import id.binar.fp.secondhand.domain.model.Banner
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.domain.repository.ProductRepository
import id.binar.fp.secondhand.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    override fun getBanner(): LiveData<Result<List<Banner>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getBanner().map { it.toDomain() }
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun addSellerProduct(body: RequestBody): LiveData<Result<Product>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.addSellerProduct(body)
            emit(Result.Success(response.toDomain()))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun getSellerProduct(): LiveData<Result<List<Product>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getSellerProduct()
            emit(Result.Success(response.map { it.toDomain() }))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun getSellerProductById(id: Int): LiveData<Result<Product>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getSellerProductById(id).toDomain()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun updateSellerProductById(id: Int, body: RequestBody): LiveData<Result<Product>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.updateSellerProductById(id, body)
                emit(Result.Success(response.toDomain()))
            } catch (e: HttpException) {
                emit(Result.Error(e.message()))
            } catch (e: NullPointerException) {
                emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
            } catch (e: Exception) {
                emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
            }
        }

    override fun updateSellerProductById(id: Int, status: String): LiveData<Result<Product>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.updateSellerProductById(id, status).toDomain()
                emit(Result.Success(response))
            } catch (e: HttpException) {
                emit(Result.Error(e.message()))
            } catch (e: Exception) {
                emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
            }
        }

    override fun deleteSellerProductById(id: Int): LiveData<Result<MessageDto>> = liveData {
        try {
            val response = apiService.deleteSellerProductById(id)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun getBuyerProduct(
        categoryId: Int?,
        search: String?
    ): LiveData<Result<List<Product>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getBuyerProduct()
            emit(Result.Success(response.map { it.toDomain() }))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun getBuyerProductById(id: Int): LiveData<Result<Product>> = liveData {
        emit(Result.Loading)
        try {
            val data = apiService.getBuyerProductById(id)
            emit(Result.Success(data.toDomain()))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun searchProduct(query: String): Flow<Result<List<Product>>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getBuyerProduct(search = query)
            emit(Result.Success(response.map { it.toDomain() }))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }
}