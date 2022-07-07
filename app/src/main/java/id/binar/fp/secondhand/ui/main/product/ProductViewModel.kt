package id.binar.fp.secondhand.ui.main.product

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.binar.fp.secondhand.domain.repository.CategoryRepository
import id.binar.fp.secondhand.domain.repository.ProductRepository
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    fun getCategory() = categoryRepository.getCategory()

    fun addProduct(
        name: String,
        description: String,
        basePrice: Int,
        categoryIds: List<Int>,
        location: String,
        userId: Int,
        image: File
    ) = productRepository.addSellerProduct(
        name,
        description,
        basePrice,
        categoryIds,
        location,
        userId,
        image
    )


    fun getUser() = productRepository.getUser()

}
