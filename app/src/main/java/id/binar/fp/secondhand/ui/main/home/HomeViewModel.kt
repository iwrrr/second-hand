package id.binar.fp.secondhand.ui.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.binar.fp.secondhand.domain.repository.CategoryRepository
import id.binar.fp.secondhand.domain.repository.ProductRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val queryChannel = Channel<String>()
    val searchResult = queryChannel.receiveAsFlow()
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest {
            productRepository.searchProduct(it)
        }
        .asLiveData()

    fun getBanner() = productRepository.getBanner()

    fun getCategory() = categoryRepository.getCategory()

    fun getAllProduct() = productRepository.getBuyerProduct()
}