package id.binar.fp.secondhand.domain.repository

import androidx.lifecycle.LiveData
import id.binar.fp.secondhand.data.source.network.response.OrderDto
import id.binar.fp.secondhand.util.Result

interface OrderRepository {

    fun getSellerOrder() // TODO: Response Body Not Yet Implemented

    fun getSellerOrderById(id: Int) // TODO: Response Body Not Yet Implemented

    fun updateSellerOrderById(id: Int) // TODO: Response Body Not Yet Implemented

    fun getSellerOrderByProductId(id: Int) // TODO: Response Body Not Yet Implemented

    fun addBuyerOrder(
        productId: Int,
        bidPrice: Int,
    ): LiveData<Result<OrderDto>>

    fun getBuyerOrder(): LiveData<Result<List<OrderDto>>>

    fun getBuyerOrderById(id: Int): LiveData<Result<OrderDto>>

    fun updateBuyerOrderById(
        id: Int,
        productId: Int,
        bidPrice: Int,
    ): LiveData<Result<OrderDto>>

    fun deleteBuyerOrderById(id: Int) // TODO: Response Body Not Yet Implemented
}