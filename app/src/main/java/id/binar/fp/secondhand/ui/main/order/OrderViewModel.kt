package id.binar.fp.secondhand.ui.main.order

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.binar.fp.secondhand.domain.repository.OrderRepository
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRepository: OrderRepository
) : ViewModel() {

    fun getBuyerOrder() = orderRepository.getBuyerOrder()
}