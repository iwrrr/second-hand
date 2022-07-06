package id.binar.fp.secondhand.domain.repository

import androidx.lifecycle.LiveData
import id.binar.fp.secondhand.data.source.network.response.MessageDto
import id.binar.fp.secondhand.data.source.network.response.ProductDto
import id.binar.fp.secondhand.util.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface ProductRepository {

    fun addSellerProduct(
        name: String,
        description: String,
        basePrice: String,
        categoryIds: List<Int>,
        location: String,
        image: MultipartBody.Part,
    ): LiveData<Result<ProductDto>>

    fun getSellerProduct(): LiveData<Result<List<ProductDto>?>>

    fun getSellerProductById(id: Int): LiveData<Result<ProductDto>>

    fun updateSellerProduct(
        name: String,
        description: String,
        basePrice: String,
        categoryIds: List<Int>,
        location: String,
        image: MultipartBody.Part,
    ): LiveData<Result<ProductDto>>

    fun deleteSellerProductById(id: Int): LiveData<Result<MessageDto>>

    fun getBuyerProduct(
        categoryId: Int? = null,
        search: String? = null
    ): LiveData<Result<List<ProductDto>>>

    fun getBuyerProductById(id: Int): LiveData<Result<ProductDto>>

    fun searchProduct(query: String): Flow<Result<List<ProductDto>>>
}