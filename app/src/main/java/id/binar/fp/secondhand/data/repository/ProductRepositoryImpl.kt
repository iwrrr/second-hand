package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.data.source.network.response.MessageDto
import id.binar.fp.secondhand.data.source.network.response.ProductDto
import id.binar.fp.secondhand.domain.repository.ProductRepository
import id.binar.fp.secondhand.util.Result
import okhttp3.MultipartBody
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    override fun addSellerProduct(
        name: String,
        description: String,
        basePrice: String,
        categoryIds: List<Int>,
        location: String,
        image: MultipartBody.Part
    ): LiveData<Result<ProductDto>> = liveData {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override fun getBuyerProductById(id: Int): LiveData<Result<ProductDto>> = liveData {
        TODO("Not yet implemented")
    }
}