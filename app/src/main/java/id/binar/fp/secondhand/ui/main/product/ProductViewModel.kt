package id.binar.fp.secondhand.ui.main.product

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.binar.fp.secondhand.domain.repository.OrderRepository
import id.binar.fp.secondhand.domain.repository.ProductRepository
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    fun getDetailProduct(id: Int) = productRepository.getBuyerProductById(id)

    fun addBuyerOrder(productId: Int, bidPrice: String) =
        orderRepository.addBuyerOrder(productId, bidPrice)
}