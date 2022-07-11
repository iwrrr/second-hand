package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.data.source.network.response.MessageDto
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.domain.repository.ProductRepository
import id.binar.fp.secondhand.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    override fun addSellerProduct(
        body: RequestBody
    ): LiveData<Result<Product>> = liveData {
        emit(Result.Loading)
        try {
            apiService.addSellerProduct(body)
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
        TODO("Not yet implemented")
    }

    override fun updateSellerProduct(
        name: String,
        description: String,
        basePrice: String,
        categoryIds: List<Int>,
        location: String,
        image: MultipartBody.Part
    ): LiveData<Result<Product>> = liveData {
        TODO("Not yet implemented")
    }

    override fun deleteSellerProductById(id: Int): LiveData<Result<MessageDto>> = liveData {
        TODO("Not yet implemented")
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