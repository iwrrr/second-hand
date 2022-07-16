package id.binar.fp.secondhand.ui.main.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.domain.repository.CategoryRepository
import id.binar.fp.secondhand.domain.repository.OrderRepository
import id.binar.fp.secondhand.domain.repository.ProductRepository
import id.binar.fp.secondhand.util.Result
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository,
    private val orderRepository: OrderRepository
) : ViewModel() {

    fun getCategory() = categoryRepository.getCategory()

    fun addProduct(
        name: String,
        description: String,
        basePrice: String,
        categoryIds: List<Int?>,
        location: String,
        image: File
    ): LiveData<Result<Product>> {
        val requestImageFile = image.asRequestBody("image/jpeg".toMediaType())

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", name)
            .addFormDataPart("description", description)
            .addFormDataPart("base_price", basePrice)
            .addFormDataPart("category_ids", categoryIds.toString())
            .addFormDataPart("location", location)
            .addFormDataPart("image", image.name, requestImageFile)
            .build()

        return productRepository.addSellerProduct(requestBody)
    }

    fun updateProduct(
        id: Int,
        name: String,
        description: String,
        basePrice: String,
        categoryIds: List<Int?>,
        location: String,
        image: File? = null
    ): LiveData<Result<Product>> {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", name)
            .addFormDataPart("description", description)
            .addFormDataPart("base_price", basePrice)
            .addFormDataPart("category_ids", categoryIds.toString())
            .addFormDataPart("location", location)

        if (image != null) {
            val requestImageFile = image.asRequestBody("image/jpeg".toMediaType())
            requestBody.addFormDataPart("image", image.name, requestImageFile)
        }

        return productRepository.updateSellerProductById(id, requestBody.build())
    }

    fun getSellerProductById(id: Int) = productRepository.getSellerProductById(id)

    fun deleteSellerProductById(id: Int) = productRepository.deleteSellerProductById(id)

    fun getDetailProduct(id: Int) = productRepository.getBuyerProductById(id)

    fun addBuyerOrder(productId: Int, bidPrice: String) =
        orderRepository.addBuyerOrder(productId, bidPrice)
}

