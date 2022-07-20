package id.binar.fp.secondhand.ui.main.history

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.binar.fp.secondhand.data.repository.HistoryRepositoryImpl
import id.binar.fp.secondhand.domain.model.History
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
class HistoryViewModelTest{
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: HistoryRepositoryImpl

    private lateinit var viewModel: HistoryViewModel

    private val dummyHistory = DataDummy.generateHistories()

    @Before
    fun setUp() {
        viewModel = HistoryViewModel(repo)
    }

    @Test
    fun `when history should not null and return success`() {
        val expectedValue = MutableLiveData<Result<List<History>>>()
        expectedValue.value = id.binar.fp.secondhand.util.Result.Success(dummyHistory)

        `when` (
            viewModel.getHistory()
        ).thenReturn(expectedValue)

        val actualValue = viewModel.getHistory().getOrAwaitValue()
        verify(repo).getHistory()
        assertNotNull(actualValue)
        assertTrue(actualValue is id.binar.fp.secondhand.util.Result.Success)
        assertEquals(dummyHistory, (actualValue as Result.Success).data)
    }

    @Test
    fun `when history should null and return error`() {
        val expectedValue = MutableLiveData<Result<List<History>>>()
        expectedValue.value = id.binar.fp.secondhand.util.Result.Error("Error")

        `when` (
            viewModel.getHistory()
        ).thenReturn(expectedValue)

        val actualValue = viewModel.getHistory().getOrAwaitValue()
        verify(repo).getHistory()
        assertNotNull(actualValue)
        assertTrue(actualValue is id.binar.fp.secondhand.util.Result.Error)
    }
}