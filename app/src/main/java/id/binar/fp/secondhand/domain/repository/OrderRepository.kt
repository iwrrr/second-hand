package id.binar.fp.secondhand.domain.repository

import androidx.lifecycle.LiveData
import id.binar.fp.secondhand.domain.model.Order
import id.binar.fp.secondhand.domain.model.SellerOrder
import id.binar.fp.secondhand.util.Result

interface OrderRepository {

    fun getSellerOrder(): LiveData<Result<List<SellerOrder>>>

    fun getSellerOrderById(id: Int): LiveData<Result<SellerOrder>>

    fun updateSellerOrderById(id: Int, status: String): LiveData<Result<SellerOrder>>

    fun getSellerOrderByProductId(id: Int) // TODO: Response Body Not Yet Implemented

    fun addBuyerOrder(
        productId: Int,
        bidPrice: String,
    ): LiveData<Result<Order>>

    fun getBuyerOrder(): LiveData<Result<List<Order>>>

    fun getBuyerOrderById(id: Int): LiveData<Result<Order>>

    fun updateBuyerOrderById(
        id: Int,
        productId: Int,
        bidPrice: Int,
    ): LiveData<Result<Order>>

    fun deleteBuyerOrderById(id: Int) // TODO: Response Body Not Yet Implemented
}