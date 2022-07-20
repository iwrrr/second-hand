package id.binar.fp.secondhand.ui.main.seller

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.binar.fp.secondhand.data.repository.OrderRepositoryImpl
import id.binar.fp.secondhand.data.repository.ProductRepositoryImpl
import id.binar.fp.secondhand.data.source.network.response.MessageDto
import id.binar.fp.secondhand.domain.model.Product
import id.binar.fp.secondhand.domain.model.SellerOrder
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
class SellerViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repoProduct: ProductRepositoryImpl
    @Mock
    private lateinit var repoOrder: OrderRepositoryImpl

    private lateinit var viewModel: SellerViewModel

    private val dummyProductList = DataDummy.generateProducts()
    private val dummyProduct = DataDummy.generateAddProducts()
    private val dummyDeleteSeller = DataDummy.generateDeleteSeller()
    private val dummyOrder = DataDummy.generateSellerOrders()
    private val dummyOrderId = DataDummy.generateSellerOrdersId()

    @Before
    fun setUp() {
        viewModel = SellerViewModel(repoProduct, repoOrder)
    }

    /**
     * GET SELLER PRODUCT
     */
    @Test
    fun `when get seller should not null and return success`(){
        val expectedValue = MutableLiveData<Result<List<Product>>>()
        expectedValue.value = Result.Success(dummyProductList)

        `when`(viewModel.getSellerProduct()).thenReturn(expectedValue)

        val actualValue = viewModel.getSellerProduct().getOrAwaitValue()
        verify(repoProduct).getSellerProduct()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyProductList, (actualValue as Result.Success).data)
    }

    @Test
    fun `when get seller should null and return error`(){
        val expectedValue = MutableLiveData<Result<List<Product>>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.getSellerProduct()).thenReturn(expectedValue)

        val actualValue = viewModel.getSellerProduct().getOrAwaitValue()
        verify(repoProduct).getSellerProduct()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }

    /**
     * UPDATE SELLER PRODUCT BY ID
     */
    @Test
    fun `when update seller should not null`(){
        val expectedValue = MutableLiveData<Result<Product>>()
        expectedValue.value = Result.Success(dummyProduct)

        `when`(viewModel.updateSellerProductById(dummyProduct.id, dummyProduct.status.toString())).thenReturn(expectedValue)

        val actualValue = viewModel.updateSellerProductById(dummyProduct.id, dummyProduct.status.toString()).getOrAwaitValue()
        verify(repoProduct).updateSellerProductById(dummyProduct.id, dummyProduct.status.toString())
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyProduct, (actualValue as Result.Success).data)
    }

    @Test
    fun `when update seller should null`(){
        val expectedValue = MutableLiveData<Result<Product>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.updateSellerProductById(dummyProduct.id, dummyProduct.status.toString())).thenReturn(expectedValue)

        val actualValue = viewModel.updateSellerProductById(dummyProduct.id, dummyProduct.status.toString()).getOrAwaitValue()
        verify(repoProduct).updateSellerProductById(dummyProduct.id, dummyProduct.status.toString())
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }

    /**
     * DELETE SELLER BY ID
     */
    @Test
    fun `when delete seller should not null`(){
        val expectedValue = MutableLiveData<Result<MessageDto>>()
        expectedValue.value = Result.Success(dummyDeleteSeller)

        `when`(viewModel.deleteSellerProductById(dummyProduct.id)).thenReturn(expectedValue)

        val actualValue = viewModel.deleteSellerProductById(dummyProduct.id).getOrAwaitValue()
        verify(repoProduct).deleteSellerProductById(dummyProduct.id)
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyDeleteSeller, (actualValue as Result.Success).data)
    }

    @Test
    fun `when delete seller should null`(){
        val expectedValue = MutableLiveData<Result<MessageDto>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.deleteSellerProductById(dummyProduct.id)).thenReturn(expectedValue)

        val actualValue = viewModel.deleteSellerProductById(dummyProduct.id).getOrAwaitValue()
        verify(repoProduct).deleteSellerProductById(dummyProduct.id)
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }

    /**
     * GET SELLER ORDER
     */
    @Test
    fun `when seller order shoud not null`(){
        val expectedValue = MutableLiveData<Result<List<SellerOrder>>>()
        expectedValue.value = Result.Success(dummyOrder)

        `when`(viewModel.getSellerOrder()).thenReturn(expectedValue)

        val actualValue = viewModel.getSellerOrder().getOrAwaitValue()
        verify(repoOrder).getSellerOrder()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyOrder, (actualValue as Result.Success).data)
    }

    @Test
    fun `when seller order shoud null`(){
        val expectedValue = MutableLiveData<Result<List<SellerOrder>>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.getSellerOrder()).thenReturn(expectedValue)

        val actualValue = viewModel.getSellerOrder().getOrAwaitValue()
        verify(repoOrder).getSellerOrder()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }

    /**
     * SELLER ORDER BY ID
     */
    @Test
    fun `when seller order id shoud not null`(){
        val expectedValue = MutableLiveData<Result<SellerOrder>>()
        expectedValue.value = Result.Success(dummyOrderId)

        `when`(viewModel.getSellerOrderById(dummyOrderId.id)).thenReturn(expectedValue)

        val actualValue = viewModel.getSellerOrderById(dummyOrderId.id).getOrAwaitValue()
        verify(repoOrder).getSellerOrderById(dummyOrderId.id)
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyOrderId, (actualValue as Result.Success).data)
    }

    @Test
    fun `when seller order id shoud null`(){
        val expectedValue = MutableLiveData<Result<SellerOrder>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.getSellerOrderById(dummyOrderId.id)).thenReturn(expectedValue)

        val actualValue = viewModel.getSellerOrderById(dummyOrderId.id).getOrAwaitValue()
        verify(repoOrder).getSellerOrderById(dummyOrderId.id)
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }

    /**
     * UPDATE SELER ORDER BY ID
     */
    @Test
    fun `when update seller order id shoud not null`(){
        val expectedValue = MutableLiveData<Result<SellerOrder>>()
        expectedValue.value = Result.Success(dummyOrderId)

        `when`(viewModel.updateSellerOrderById(dummyOrderId.id, dummyOrderId.status.toString())).thenReturn(expectedValue)

        val actualValue = viewModel.updateSellerOrderById(dummyOrderId.id, dummyOrderId.status.toString()).getOrAwaitValue()
        verify(repoOrder).updateSellerOrderById(dummyOrderId.id, dummyOrderId.status.toString())
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyOrderId, (actualValue as Result.Success).data)
    }

    @Test
    fun `when update seller order id shoud null`(){
        val expectedValue = MutableLiveData<Result<SellerOrder>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.updateSellerOrderById(dummyOrderId.id, dummyOrderId.status.toString())).thenReturn(expectedValue)

        val actualValue = viewModel.updateSellerOrderById(dummyOrderId.id, dummyOrderId.status.toString()).getOrAwaitValue()
        verify(repoOrder).updateSellerOrderById(dummyOrderId.id, dummyOrderId.status.toString())
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }
}