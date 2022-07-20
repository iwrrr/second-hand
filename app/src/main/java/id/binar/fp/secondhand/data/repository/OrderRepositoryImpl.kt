package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.local.room.dao.BuyerOrderDao
import id.binar.fp.secondhand.data.source.local.room.dao.SellerOrderDao
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.domain.model.BuyerOrder
import id.binar.fp.secondhand.domain.model.Order
import id.binar.fp.secondhand.domain.model.SellerOrder
import id.binar.fp.secondhand.domain.repository.OrderRepository
import id.binar.fp.secondhand.util.Result
import retrofit2.HttpException
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val sellerOrderDao: SellerOrderDao,
    private val buyerOrderDao: BuyerOrderDao
) : OrderRepository {

    override fun getSellerOrder(): LiveData<Result<List<SellerOrder>>> = liveData {
        emit(Result.Loading())

        val oldOrder = sellerOrderDao.getSellerOrder().map { it.toDomain() }
        emit(Result.Loading(oldOrder))

        try {
            val data = apiService.getSellerOrder().map { it.toEntity() }
            sellerOrderDao.deleteSellerOrders()
            sellerOrderDao.insertSellerOrders(data)
        } catch (e: HttpException) {
            emit(Result.Error(e.message(), oldOrder))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error", oldOrder))
        }

        val newOrder = sellerOrderDao.getSellerOrder().map { it.toDomain() }
        emit(Result.Success(newOrder))
    }

    override fun getSellerOrderById(id: Int): LiveData<Result<SellerOrder>> = liveData {
        emit(Result.Loading())
        try {
            val order = sellerOrderDao.getSellerOrderById(id)?.toDomain()
            if (order != null) {
                emit(Result.Success(order))
            }
        } catch (e: NullPointerException) {
            emit(Result.Error("Order tidak ditemukan"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun updateSellerOrderById(id: Int, status: String): LiveData<Result<SellerOrder>> =
        liveData {
            emit(Result.Loading())
            try {
                val response = apiService.updateSellerOrderById(id, status).toDomain()
                emit(Result.Success(response))
            } catch (e: HttpException) {
                emit(Result.Error(e.message()))
            } catch (e: Exception) {
                emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
            }
        }

    override fun addBuyerOrder(productId: Int, bidPrice: String): LiveData<Result<Order>> =
        liveData {
            emit(Result.Loading())
            try {
                val response = apiService.addBuyerOrder(productId, bidPrice).toDomain()
                emit(Result.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(Result.Error("Produk ini memiliki pesanan maksimum"))
                    403 -> emit(Result.Error("Kamu sudah menawar barang ini"))
                    else -> emit(Result.Error(e.message()))
                }
            } catch (e: NullPointerException) {
                emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
            } catch (e: Exception) {
                emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
            }
        }

    override fun getBuyerOrder(): LiveData<Result<List<BuyerOrder>>> = liveData {
        emit(Result.Loading())

        val oldOrder = buyerOrderDao.getBuyerOrder().map { it.toDomain() }
        emit(Result.Loading(oldOrder))

        try {
            val data = apiService.getBuyerOrder().map { it.toEntity() }
            buyerOrderDao.deleteBuyerOrders()
            buyerOrderDao.insertBuyerOrders(data)
        } catch (e: HttpException) {
            emit(Result.Error(e.message(), oldOrder))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error", oldOrder))
        }

        val newOrder = buyerOrderDao.getBuyerOrder().map { it.toDomain() }
        emit(Result.Success(newOrder))
    }

    override fun getBuyerOrderById(id: Int): LiveData<Result<BuyerOrder>> = liveData {
        emit(Result.Loading())
        try {
            val order = buyerOrderDao.getBuyerOrderById(id)?.toDomain()
            if (order != null) {
                emit(Result.Success(order))
            }
        } catch (e: NullPointerException) {
            emit(Result.Error("Order tidak ditemukan"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun updateBuyerOrderById(
        id: Int,
        productId: Int,
        bidPrice: Int
    ): LiveData<Result<Order>> = liveData {
        TODO("Not yet implemented")
    }

    override fun deleteBuyerOrderById(id: Int) {
        TODO("Not yet implemented")
    }
}