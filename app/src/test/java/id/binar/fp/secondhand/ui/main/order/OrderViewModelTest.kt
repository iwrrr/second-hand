package id.binar.fp.secondhand.ui.main.order

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.binar.fp.secondhand.data.repository.OrderRepositoryImpl
import id.binar.fp.secondhand.domain.model.BuyerOrder
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
class OrderViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: OrderRepositoryImpl

    private lateinit var viewModel: OrderViewModel

    private val dummyOrder = DataDummy.generateOrders()

    @Before
    fun setUp() {
        viewModel = OrderViewModel(repo)
    }

    @Test
    fun `when order should not null adn return success`() {
        val expectedValue = MutableLiveData<id.binar.fp.secondhand.util.Result<List<BuyerOrder>>>()
        expectedValue.value = id.binar.fp.secondhand.util.Result.Success(dummyOrder)

        `when`(viewModel.getBuyerOrder()).thenReturn(expectedValue)

        val actualValue = viewModel.getBuyerOrder().getOrAwaitValue()
        verify(repo).getBuyerOrder()
        assertNotNull(actualValue)
        assertTrue(actualValue is id.binar.fp.secondhand.util.Result.Success)
        assertEquals(dummyOrder, (actualValue as Result.Success).data)
    }

    @Test
    fun `when order should null adn return error`() {
        val expectedValue = MutableLiveData<id.binar.fp.secondhand.util.Result<List<BuyerOrder>>>()
        expectedValue.value = id.binar.fp.secondhand.util.Result.Error("Error")

        `when`(viewModel.getBuyerOrder()).thenReturn(expectedValue)

        val actualValue = viewModel.getBuyerOrder().getOrAwaitValue()
        verify(repo).getBuyerOrder()
        assertNotNull(actualValue)
        assertTrue(actualValue is id.binar.fp.secondhand.util.Result.Error)
    }
}