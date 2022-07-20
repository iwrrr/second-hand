package id.binar.fp.secondhand.ui.main.notification

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.binar.fp.secondhand.data.repository.NotificationRepositoryImpl
import id.binar.fp.secondhand.domain.model.Notification
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
class NotificationViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: NotificationRepositoryImpl

    private lateinit var viewModel: NotificationViewModel

    private val dummyNotif = DataDummy.generateNotif()

    @Before
    fun setUp() {
        viewModel = NotificationViewModel(repo)
    }

    @Test
    fun `when notif should not null adn return success`(){
        val expectedValue = MutableLiveData<Result<List<Notification>>>()
        expectedValue.value = Result.Success(dummyNotif)

        `when`(viewModel.getNotification()).thenReturn(expectedValue)

        val actualValue = viewModel.getNotification().getOrAwaitValue()
        verify(repo).getNotification()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyNotif, (actualValue as Result.Success).data)
    }

    @Test
    fun `when notif should null adn return error`(){
        val expectedValue = MutableLiveData<Result<List<Notification>>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.getNotification()).thenReturn(expectedValue)

        val actualValue = viewModel.getNotification().getOrAwaitValue()
        verify(repo).getNotification()
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }
}