package id.binar.fp.secondhand.ui.main.product

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.binar.fp.secondhand.data.repository.CategoryRepositoryImpl
import id.binar.fp.secondhand.data.repository.OrderRepositoryImpl
import id.binar.fp.secondhand.data.repository.ProductRepositoryImpl
import id.binar.fp.secondhand.domain.model.Category
import id.binar.fp.secondhand.domain.model.Order
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.utils.DataDummy
import id.binar.fp.secondhand.utils.getOrAwaitValue
import okhttp3.RequestBody
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class ProductViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repoProduct: ProductRepositoryImpl
    @Mock
    private lateinit var repoCategory: CategoryRepositoryImpl
    @Mock
    private lateinit var repoOrder: OrderRepositoryImpl

    private lateinit var viewModel: ProductViewModel

    private val dummyCategory = DataDummy.generateCategories()
    private val dummyAddProduct = DataDummy.generateAddProducts()
    private val dummyAddBuyer = DataDummy.generateAddBuyerOrder()

    @Before
    fun setUp() {
        viewModel = ProductViewModel(repoProduct, repoCategory, repoOrder)
    }

    /**
     * CATEGORY
     */
    @Test
    fun `when category should not null and return success`(){
        val expectedValue = MutableLiveData<Result<List<Category>>>()
        expectedValue.value = Result.Success(dummyCategory)

        `when`(viewModel.getCategory()).thenReturn(expectedValue)

        val actualValue = viewModel.getCategory().getOrAwaitValue()
        verify(repoCategory).getCategory()
        Assert.assertNotNull(actualValue)
        Assert.assertTrue(actualValue is Result.Success)
        Assert.assertEquals(dummyCategory, (actualValue as Result.Success).data)
    }

    @Test
    fun `when category should null and return error`(){
        val expectedValue = MutableLiveData<Result<List<Category>>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.getCategory()).thenReturn(expectedValue)

        val actualValue = viewModel.getCategory().getOrAwaitValue()
        verify(repoCategory).getCategory()
        Assert.assertNotNull(actualValue)
        Assert.assertTrue(actualValue is Result.Error)
    }

    /**
     * ADD PRODUCT
     */
    @Test
    fun `when add product should not null and return success` (){
        val expectedValue = MutableLiveData<Result<Product>>()
        expectedValue.value = Result.Success(dummyAddProduct)

        val file = "string"
        `when`(
            viewModel.addProduct(
                dummyAddProduct.name.toString(),
                dummyAddProduct.description.toString(),
                dummyAddProduct.basePrice.toString(),
                listOf(),
                dummyAddProduct.location.toString(),
                file as File
            )).thenReturn(expectedValue)

        val actualValue = viewModel.addProduct(
            dummyAddProduct.name.toString(),
            dummyAddProduct.description.toString(),
            dummyAddProduct.basePrice.toString(),
            listOf(),
            dummyAddProduct.location.toString(),
            file as File
        ).getOrAwaitValue()

        val requestBody = file as RequestBody
        verify(repoProduct).addSellerProduct(requestBody)
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyAddProduct, (actualValue as Result.Success).data)
    }

    @Test
    fun `when add product should null and return error` (){
        val expectedValue = MutableLiveData<Result<Product>>()
        expectedValue.value = Result.Error("error")

        val file = "string"
        `when`(
            viewModel.addProduct(
                dummyAddProduct.name.toString(),
                dummyAddProduct.description.toString(),
                dummyAddProduct.basePrice.toString(),
                listOf(),
                dummyAddProduct.location.toString(),
                file as File
            )).thenReturn(expectedValue)

        val actualValue = viewModel.addProduct(
            dummyAddProduct.name.toString(),
            dummyAddProduct.description.toString(),
            dummyAddProduct.basePrice.toString(),
            listOf(),
            dummyAddProduct.location.toString(),
            file as File
        ).getOrAwaitValue()

        val requestBody = file as RequestBody
        verify(repoProduct).addSellerProduct(requestBody)
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }

    /**
     * UPDATE PRODUCT
     */
    @Test
    fun `when update product should not null and return success` (){
        val expectedValue = MutableLiveData<Result<Product>>()
        expectedValue.value = Result.Success(dummyAddProduct)

        val file = "string"
        `when`(
            viewModel.updateProduct(
                dummyAddProduct.id,
                dummyAddProduct.name.toString(),
                dummyAddProduct.description.toString(),
                dummyAddProduct.basePrice.toString(),
                listOf(),
                dummyAddProduct.location.toString(),
                file as File
            )).thenReturn(expectedValue)

        val actualValue = viewModel.updateProduct(
            dummyAddProduct.id,
            dummyAddProduct.name.toString(),
            dummyAddProduct.description.toString(),
            dummyAddProduct.basePrice.toString(),
            listOf(),
            dummyAddProduct.location.toString(),
            file as File
        ).getOrAwaitValue()

        val requestBody = file as RequestBody
        verify(repoProduct).updateSellerProductById(dummyAddProduct.id, requestBody)
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyAddProduct, (actualValue as Result.Success).data)
    }

    @Test
    fun `when update product should null and return error` (){
        val expectedValue = MutableLiveData<Result<Product>>()
        expectedValue.value = Result.Error("Error")

        val file = "string"
        `when`(
            viewModel.updateProduct(
                dummyAddProduct.id,
                dummyAddProduct.name.toString(),
                dummyAddProduct.description.toString(),
                dummyAddProduct.basePrice.toString(),
                listOf(),
                dummyAddProduct.location.toString(),
                file as File
            )).thenReturn(expectedValue)

        val actualValue = viewModel.updateProduct(
            dummyAddProduct.id,
            dummyAddProduct.name.toString(),
            dummyAddProduct.description.toString(),
            dummyAddProduct.basePrice.toString(),
            listOf(),
            dummyAddProduct.location.toString(),
            file as File
        ).getOrAwaitValue()

        val requestBody = file as RequestBody
        verify(repoProduct).updateSellerProductById(dummyAddProduct.id, requestBody)
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }

    /**
     * GET SELLER PRODUCT BY ID
     */

    @Test
    fun `when get seller product should not null and return success`(){
        val expectedValue = MutableLiveData<Result<Product>>()
        expectedValue.value = Result.Success(dummyAddProduct)

        `when`(viewModel.getSellerProductById(dummyAddProduct.id)).thenReturn(expectedValue)

        val actualValue = viewModel.getSellerProductById(dummyAddProduct.id).getOrAwaitValue()
        verify(repoProduct).getSellerProductById(dummyAddProduct.id)
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyAddProduct, (actualValue as Result.Success).data)
    }

    @Test
    fun `when get seller product should null and return error`(){
        val expectedValue = MutableLiveData<Result<Product>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.getSellerProductById(dummyAddProduct.id)).thenReturn(expectedValue)

        val actualValue = viewModel.getSellerProductById(dummyAddProduct.id).getOrAwaitValue()
        verify(repoProduct).getSellerProductById(dummyAddProduct.id)
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }

    /**
     * Detail product
     */
    @Test
    fun `when detail product should not null and return success`(){
        val expectedValue = MutableLiveData<Result<Product>>()
        expectedValue.value = Result.Success(dummyAddProduct)

        `when`(viewModel.getDetailProduct(dummyAddProduct.id)).thenReturn(expectedValue)

        val actualValue = viewModel.getDetailProduct(dummyAddProduct.id).getOrAwaitValue()
        verify(repoProduct).getBuyerProduct()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyAddProduct, (actualValue as Result.Success).data)
    }

    @Test
    fun `when detail product should null and return error`(){
        val expectedValue = MutableLiveData<Result<Product>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.getDetailProduct(dummyAddProduct.id)).thenReturn(expectedValue)

        val actualValue = viewModel.getDetailProduct(dummyAddProduct.id).getOrAwaitValue()
        verify(repoProduct).getBuyerProduct()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }

    /**
     * ADD BUYER ORDER
     */
    @Test
    fun `when add buyer product should not null and return success`(){
        val expectedValue = MutableLiveData<Result<Order>>()
        expectedValue.value = Result.Success(dummyAddBuyer)

        `when`(viewModel.addBuyerOrder(dummyAddProduct.id, dummyAddProduct.basePrice.toString())).thenReturn(expectedValue)

        val actualValue = viewModel.addBuyerOrder(dummyAddProduct.id, dummyAddProduct.basePrice.toString()).getOrAwaitValue()
        verify(repoOrder).addBuyerOrder(dummyAddProduct.id, dummyAddProduct.basePrice.toString())
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyAddBuyer, (actualValue as Result.Success).data)
    }

    @Test
    fun `when add buyer product should null and return error`(){
        val expectedValue = MutableLiveData<Result<Order>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.addBuyerOrder(dummyAddProduct.id, dummyAddProduct.basePrice.toString())).thenReturn(expectedValue)

        val actualValue = viewModel.addBuyerOrder(dummyAddProduct.id, dummyAddProduct.basePrice.toString()).getOrAwaitValue()
        verify(repoOrder).addBuyerOrder(dummyAddProduct.id, dummyAddProduct.basePrice.toString())
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }
}