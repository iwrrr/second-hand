package id.binar.fp.secondhand.ui.main.seller

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.binar.fp.secondhand.domain.repository.ProductRepository
import javax.inject.Inject

@HiltViewModel
class SellerViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    fun getSellerProduct() = repository.getSellerProduct()

    fun getSellerOrder() = repository.getSellerOrder()
}