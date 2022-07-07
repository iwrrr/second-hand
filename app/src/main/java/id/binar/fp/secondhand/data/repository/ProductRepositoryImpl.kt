package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.data.source.network.response.MessageDto
import id.binar.fp.secondhand.data.source.network.response.ProductDto
import id.binar.fp.secondhand.data.source.network.response.UserDto
import id.binar.fp.secondhand.domain.repository.ProductRepository
import id.binar.fp.secondhand.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    override fun addSellerProduct(
        name: String,
        description: String,
        basePrice: Int,
        categoryIds: List<Int>,
        location: String,
        userId: Int,
        image: File
    ): LiveData<Result<ProductDto>> = liveData {
        emit(Result.Loading)
        try {
            apiService.addSellerProduct(name, description, basePrice, categoryIds, location, image)
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun getUser(): LiveData<Result<UserDto>> = liveData {
        emit(Result.Loading)
        try {
            val user = apiService.getUser()
            emit(Result.Success(user))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun getSellerProduct(): LiveData<Result<List<ProductDto>>> = liveData {
        TODO("Not yet implemented")
    }

    override fun getSellerProductById(id: Int): LiveData<Result<ProductDto>> = liveData {
        TODO("Not yet implemented")
    }

    override fun updateSellerProduct(
        name: String,
        description: String,
        basePrice: String,
        categoryIds: List<Int>,
        location: String,
        image: MultipartBody.Part
    ): LiveData<Result<ProductDto>> = liveData {
        TODO("Not yet implemented")
    }

    override fun deleteSellerProductById(id: Int): LiveData<Result<MessageDto>> = liveData {
        TODO("Not yet implemented")
    }

    override fun getBuyerProduct(
        categoryId: Int?,
        search: String?
    ): LiveData<Result<List<ProductDto>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getBuyerProduct()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun getBuyerProductById(id: Int): LiveData<Result<ProductDto>> = liveData {
        emit(Result.Loading)
        try {
            emit(Result.Success(apiService.getBuyerProductById(id)))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun searchProduct(query: String): Flow<Result<List<ProductDto>>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getBuyerProduct(search = query)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }
}