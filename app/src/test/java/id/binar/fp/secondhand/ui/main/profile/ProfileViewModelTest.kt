package id.binar.fp.secondhand.ui.main.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.binar.fp.secondhand.data.repository.AuthRepositoryImpl
import id.binar.fp.secondhand.domain.model.User
import id.binar.fp.secondhand.util.Result
import id.binar.fp.secondhand.utils.DataDummy
import id.binar.fp.secondhand.utils.getOrAwaitValue
import okhttp3.RequestBody
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
class ProfileViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: AuthRepositoryImpl
    private lateinit var viewModel: ProfileViewModel
    private val dummyAuth = DataDummy.generateUserId()

    @Before
    fun setUp(){
        viewModel = ProfileViewModel(repo)
    }

    @Test
    fun `when profile update should not null`(){
        val expectedValue = MutableLiveData<Result<User>>()
        expectedValue.value = Result.Success(dummyAuth)

        `when`(viewModel.updateProfile()).thenReturn(expectedValue)

        val actualValue = viewModel.updateProfile().getOrAwaitValue()
        verify(repo).updateProfile(dummyAuth as RequestBody)
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyAuth, (actualValue as Result.Success).data)
    }

    @Test
    fun `when profile update should null`(){
        val expectedValue = MutableLiveData<Result<User>>()
        expectedValue.value = Result.Error("Error")

        `when`(viewModel.updateProfile()).thenReturn(expectedValue)

        val actualValue = viewModel.updateProfile().getOrAwaitValue()
        verify(repo).updateProfile(dummyAuth as RequestBody)
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }
}