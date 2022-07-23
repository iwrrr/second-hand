package id.binar.fp.secondhand.ui.main.product

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.binar.fp.secondhand.data.repository.CategoryRepositoryImpl
import id.binar.fp.secondhand.data.repository.OrderRepositoryImpl
import id.binar.fp.secondhand.data.repository.ProductRepositoryImpl
import id.binar.fp.secondhand.domain.model.Category
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.utils.DataDummy
import id.binar.fp.secondhand.utils.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

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

    @Mock
    private lateinit var viewModel: ProductViewModel

    private val dummyCategory = DataDummy.generateCategories()
    private val dummyAddProduct = DataDummy.generateAddProducts()

    @Before
    fun setUp() {
        viewModel = ProductViewModel(repoProduct, repoCategory, repoOrder)
    }

    /**
     * CATEGORY
     */
    @Test
    fun `when category should not null and return success`() {
        val expectedValue = MutableLiveData<Result<List<Category>>>()
        expectedValue.value = Result.Success(dummyCategory)

        `when`(viewModel.getCategory()).thenReturn(expectedValue)

        val actualValue = viewModel.getCategory().getOrAwaitValue()
        verify(repoCategory).getCategory()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyCategory, (actualValue as Result.Success).data)
    }

    @Test
    fun `when category should null and return error`() {
        val expectedValue = MutableLiveData<Result<List<Category>>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.getCategory()).thenReturn(expectedValue)

        val actualValue = viewModel.getCategory().getOrAwaitValue()
        verify(repoCategory).getCategory()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }

    /**
     * GET SELLER PRODUCT BY ID
     */

    @Test
    fun `when get seller product should not null and return success`() {
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
    fun `when get seller product should null and return error`() {
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
    fun `when detail product should not null and return success`() {
        val expectedValue = MutableLiveData<Result<Product>>()
        expectedValue.value = Result.Success(dummyAddProduct)

        `when`(viewModel.getDetailProduct(dummyAddProduct.id)).thenReturn(expectedValue)

        val actualValue = viewModel.getDetailProduct(dummyAddProduct.id).getOrAwaitValue()
        verify(repoProduct).getBuyerProductById(dummyAddProduct.id)
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyAddProduct, (actualValue as Result.Success).data)
    }

    @Test
    fun `when detail product should null and return error`() {
        val expectedValue = MutableLiveData<Result<Product>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.getDetailProduct(dummyAddProduct.id)).thenReturn(expectedValue)

        val actualValue = viewModel.getDetailProduct(dummyAddProduct.id).getOrAwaitValue()
        verify(repoProduct).getBuyerProductById(dummyAddProduct.id)
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }
}