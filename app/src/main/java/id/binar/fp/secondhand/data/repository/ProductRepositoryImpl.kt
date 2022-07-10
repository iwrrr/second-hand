package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.data.source.network.response.MessageDto
import id.binar.fp.secondhand.data.source.network.response.ProductDto
import id.binar.fp.secondhand.data.source.network.response.SellerOrderDto
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
    ): LiveData<Result<ProductDto>> = liveData {
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

//    override fun getUser(): LiveData<Result<UserDto>> = liveData {
//        emit(Result.Loading)
//        try {
//            val user = apiService.getUser()
//            emit(Result.Success(user))
//        } catch (e: HttpException) {
//            emit(Result.Error(e.message()))
//        } catch (e: NullPointerException) {
//            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
//        } catch (e: Exception) {
//            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
//        }
//    }

    override fun getSellerProduct(): LiveData<Result<List<ProductDto>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getSellerProduct()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
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

    override fun getSellerOrder(): LiveData<Result<List<SellerOrderDto>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getSellerOrder()
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun getSellerOrderById(id: Int): LiveData<Result<SellerOrderDto>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getSellerOrderById(id)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
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