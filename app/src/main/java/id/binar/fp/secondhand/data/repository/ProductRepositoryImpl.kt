package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.local.room.dao.ProductDao
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
    private val apiService: ApiService,
    private val productDao: ProductDao
) : ProductRepository {

    override fun getBanner(): LiveData<Result<List<Banner>>> = liveData {
        emit(Result.Loading())

        val oldBanner = productDao.getBanner().map { it.toDomain() }
        emit(Result.Loading(oldBanner))

        try {
            val data = apiService.getBanner().map { it.toEntity() }
            productDao.deleteBanner()
            productDao.insertBanner(data)
        } catch (e: HttpException) {
            emit(Result.Error(e.message(), oldBanner))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found", oldBanner))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error", oldBanner))
        }

        val newBanner = productDao.getBanner().map { it.toDomain() }
        emit(Result.Success(newBanner))
    }

    override fun addSellerProduct(body: RequestBody): LiveData<Result<Product>> = liveData {
        emit(Result.Loading())
        try {
            val response = apiService.addSellerProduct(body)
            emit(Result.Success(response.toDomain()))
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> emit(Result.Error("Kamu sudah menerbitkan 5 produk"))
                else -> emit(Result.Error(e.message()))
            }
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun getSellerProduct(): LiveData<Result<List<Product>>> = liveData {
        emit(Result.Loading())

        val oldProduct = productDao.getSellerProduct().map { it.toDomain() }
        emit(Result.Loading(oldProduct))

        try {
            val data = apiService.getSellerProduct().map { it.toEntity() }
            productDao.deleteSellerProduct()
            productDao.insertSellerProducts(data)
        } catch (e: HttpException) {
            emit(Result.Error(e.message(), oldProduct))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error", oldProduct))
        }

        val newProduct = productDao.getSellerProduct().map { it.toDomain() }
        emit(Result.Success(newProduct))
    }

    override fun getSellerProductById(id: Int): LiveData<Result<Product>> = liveData {
        emit(Result.Loading())
        try {
            val oldProduct = productDao.getSellerProductById(id)?.toDomain()
            if (oldProduct != null) {
                emit(Result.Loading(oldProduct))
            }
        } catch (e: NullPointerException) {
            emit(Result.Error("Produk tidak ditemukan"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }

        val newProduct = productDao.getSellerProductById(id)?.toDomain()
        emit(Result.Success(newProduct))
    }

    override fun updateSellerProductById(id: Int, body: RequestBody): LiveData<Result<Product>> =
        liveData {
            emit(Result.Loading())
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
            emit(Result.Loading())
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
        emit(Result.Loading())

        productDao.deleteSellerProductById(id)

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
        emit(Result.Loading())

        val oldProduct = productDao.getBuyerProduct().map { it.toDomain() }
        emit(Result.Loading(oldProduct))

        try {
            val data = apiService.getBuyerProduct().map { it.toEntity() }
            productDao.deleteBuyerProduct()
            productDao.insertBuyerProducts(data)
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }

        val newProduct = productDao.getBuyerProduct().map { it.toDomain() }
        emit(Result.Success(newProduct))
    }

    override fun getBuyerProductById(id: Int): LiveData<Result<Product>> = liveData {
        emit(Result.Loading())
        try {
            val oldProduct = productDao.getBuyerProductById(id)?.toDomain()
            if (oldProduct != null) {
                emit(Result.Loading(oldProduct))
            }
        } catch (e: NullPointerException) {
            emit(Result.Error("Produk tidak ditemukan"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }

        val newProduct = productDao.getBuyerProductById(id)?.toDomain()
        emit(Result.Success(newProduct))
    }

    override fun searchProduct(query: String): Flow<Result<List<Product>>> = flow {
        emit(Result.Loading())

        try {
            val data = apiService.getBuyerProduct(search = query).map { it.toDomain() }
            emit(Result.Success(data))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }
}