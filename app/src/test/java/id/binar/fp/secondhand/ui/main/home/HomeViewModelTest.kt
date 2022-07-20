package id.binar.fp.secondhand.ui.main.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.binar.fp.secondhand.data.repository.CategoryRepositoryImpl
import id.binar.fp.secondhand.data.repository.ProductRepositoryImpl
import id.binar.fp.secondhand.domain.model.Banner
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
class HomeViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repoProduct: ProductRepositoryImpl
    @Mock
    private lateinit var repoCategory: CategoryRepositoryImpl

    private lateinit var viewModel: HomeViewModel

    private val dummyBanner = DataDummy.generateBanners()
    private val dummyCategory = DataDummy.generateCategories()
    private val dummyProduct = DataDummy.generateProducts()

    @Before
    fun setUp() {
        viewModel = HomeViewModel(repoProduct, repoCategory)
    }

    @Test
    fun `when banner should not null and return success`(){
        val expectedValue = MutableLiveData<Result<List<Banner>>>()
        expectedValue.value = Result.Success(dummyBanner)

        `when`(viewModel.getBanner()).thenReturn(expectedValue)

        val actualValue = viewModel.getBanner().getOrAwaitValue()
        verify(repoProduct).getBanner()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyBanner, (actualValue as Result.Success).data)
    }

    @Test
    fun `when banner should null and return error`(){
        val expectedValue = MutableLiveData<Result<List<Banner>>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.getBanner()).thenReturn(expectedValue)

        val actualValue = viewModel.getBanner().getOrAwaitValue()
        verify(repoProduct).getBanner()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }

    @Test
    fun `when category should not null and return success`(){
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
    fun `when category should null and return error`(){
        val expectedValue = MutableLiveData<Result<List<Category>>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.getCategory()).thenReturn(expectedValue)

        val actualValue = viewModel.getCategory().getOrAwaitValue()
        verify(repoCategory).getCategory()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }

    @Test
    fun `when product should not null and return success`(){
        val expectedValue = MutableLiveData<Result<List<Product>>>()
        expectedValue.value = Result.Success(dummyProduct)

        `when`(viewModel.getAllProduct()).thenReturn(expectedValue)

        val actualValue = viewModel.getAllProduct().getOrAwaitValue()
        verify(repoProduct).getBuyerProduct()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyProduct, (actualValue as Result.Success).data)
    }

    @Test
    fun `when product should null and return error`(){
        val expectedValue = MutableLiveData<Result<List<Product>>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.getAllProduct()).thenReturn(expectedValue)

        val actualValue = viewModel.getAllProduct().getOrAwaitValue()
        verify(repoProduct).getBuyerProduct()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }
}