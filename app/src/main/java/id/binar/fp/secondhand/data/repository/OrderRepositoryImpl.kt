package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.data.source.network.response.OrderDto
import id.binar.fp.secondhand.domain.repository.OrderRepository
import id.binar.fp.secondhand.util.Result
import retrofit2.HttpException
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : OrderRepository {

    override fun getSellerOrder() {
        TODO("Not yet implemented")
    }

    override fun getSellerOrderById(id: Int) {
        TODO("Not yet implemented")
    }

    override fun updateSellerOrderById(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getSellerOrderByProductId(id: Int) {
        TODO("Not yet implemented")
    }

    override fun addBuyerOrder(productId: Int, bidPrice: String): LiveData<Result<OrderDto>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.addBuyerOrder(productId, bidPrice)
                emit(Result.Success(response))
            } catch (e: HttpException) {
                if (e.code() == 400) {
                    emit(Result.Error("Produk ini memiliki pesanan maksimum"))
                } else {
                    emit(Result.Error(e.message()))
                }
            } catch (e: NullPointerException) {
                emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
            } catch (e: Exception) {
                emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
            }
        }

    override fun getBuyerOrder(): LiveData<Result<List<OrderDto>>> = liveData {
        TODO("Not yet implemented")
    }

    override fun getBuyerOrderById(id: Int): LiveData<Result<OrderDto>> = liveData {
        TODO("Not yet implemented")
    }

    override fun updateBuyerOrderById(
        id: Int,
        productId: Int,
        bidPrice: Int
    ): LiveData<Result<OrderDto>> = liveData {
        TODO("Not yet implemented")
    }

    override fun deleteBuyerOrderById(id: Int) {
        TODO("Not yet implemented")
    }
}