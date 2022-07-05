package id.binar.fp.secondhand.ui.main.product

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.binar.fp.secondhand.domain.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class ProductViewModel @Inject constructor(
    private val detailProductRepository: ProductRepository,
) : ViewModel () {
    fun getDetailProduct(id:Int) = detailProductRepository.getBuyerProductById(id)
}