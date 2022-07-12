package id.binar.fp.secondhand.ui.main.seller

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.binar.fp.secondhand.domain.repository.OrderRepository
import id.binar.fp.secondhand.domain.repository.ProductRepository
import javax.inject.Inject

@HiltViewModel
class SellerViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    fun getSellerProduct() = productRepository.getSellerProduct()

    fun getSellerOrder() = orderRepository.getSellerOrder()

    fun deleteSelerProductById(id: Int) = productRepository.deleteSellerProductById(id)
}