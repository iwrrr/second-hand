package id.binar.fp.secondhand.domain.repository

import androidx.lifecycle.LiveData
import id.binar.fp.secondhand.data.source.network.response.MessageDto
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.util.Result
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface ProductRepository {

    fun addSellerProduct(
        body: RequestBody
    ): LiveData<Result<Product>>

    fun getSellerProduct(): LiveData<Result<List<Product>>>

    fun getSellerProductById(id: Int): LiveData<Result<Product>>

    fun updateSellerProduct(
        name: String,
        description: String,
        basePrice: String,
        categoryIds: List<Int>,
        location: String,
        image: MultipartBody.Part,
    ): LiveData<Result<Product>>

    fun deleteSellerProductById(id: Int): LiveData<Result<MessageDto>>

    fun getBuyerProduct(
        categoryId: Int? = null,
        search: String? = null
    ): LiveData<Result<List<Product>>>

    fun getBuyerProductById(id: Int): LiveData<Result<Product>>

    fun searchProduct(query: String): Flow<Result<List<Product>>>
}