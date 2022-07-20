package id.binar.fp.secondhand.ui.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import id.binar.fp.secondhand.data.repository.AuthRepositoryImpl
import id.binar.fp.secondhand.domain.model.User
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
class AuthViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repo: AuthRepositoryImpl

    private lateinit var viewModel: AuthViewModel

    private val dummyAuth = DataDummy.generateUsers()[0]

    @Before
    fun setUp() {
        viewModel = AuthViewModel(repo)
    }

    @Test
    fun `when register should not null and return success`() {
        val expectedValue = MutableLiveData<Result<User>>()
        expectedValue.value = id.binar.fp.secondhand.util.Result.Success(dummyAuth)

        `when` (
            viewModel.register(
                dummyAuth.fullName.toString(),
                dummyAuth.email.toString(),
                dummyAuth.password.toString(),
                dummyAuth.phoneNumber.toString(),
                dummyAuth.city.toString(),
                dummyAuth.address.toString()
            )
        ).thenReturn(expectedValue)

        val actualValue = viewModel.register(
            dummyAuth.fullName.toString(),
            dummyAuth.email.toString(),
            dummyAuth.password.toString(),
            dummyAuth.phoneNumber.toString(),
            dummyAuth.city.toString(),
            dummyAuth.address.toString()
        ).getOrAwaitValue()
        verify(repo).register(dummyAuth.fullName.toString(),
            dummyAuth.email.toString(),
            dummyAuth.password.toString(),
            dummyAuth.phoneNumber.toString(),
            dummyAuth.city.toString(),
            dummyAuth.address.toString())
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyAuth, (actualValue as Result.Success).data)
    }

    @Test
    fun `when login should not null and return success`() {
        val expectedValue = MutableLiveData<Result<User>>()
        expectedValue.value = id.binar.fp.secondhand.util.Result.Success(dummyAuth)

        `when` (
            viewModel.login(
                dummyAuth.fullName.toString(),
                dummyAuth.email.toString()
            )
        ).thenReturn(expectedValue)

        val actualValue = viewModel.login(
            dummyAuth.fullName.toString(),
            dummyAuth.email.toString()
        ).getOrAwaitValue()
        verify(repo).login(dummyAuth.fullName.toString(), dummyAuth.email.toString())
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Success)
        assertEquals(dummyAuth, (actualValue as Result.Success).data)
    }

    @Test
    fun `when register should null and return error`() {
        val expectedValue = MutableLiveData<Result<User>>()
        expectedValue.value = id.binar.fp.secondhand.util.Result.Error("Error")

        `when` (
            viewModel.register(
                dummyAuth.fullName.toString(),
                dummyAuth.email.toString(),
                dummyAuth.password.toString(),
                dummyAuth.phoneNumber.toString(),
                dummyAuth.city.toString(),
                dummyAuth.address.toString()
            )
        ).thenReturn(expectedValue)

        val actualValue = viewModel.register(
            dummyAuth.fullName.toString(),
            dummyAuth.email.toString(),
            dummyAuth.password.toString(),
            dummyAuth.phoneNumber.toString(),
            dummyAuth.city.toString(),
            dummyAuth.address.toString()
        ).getOrAwaitValue()
        verify(repo).register(dummyAuth.fullName.toString(),
            dummyAuth.email.toString(),
            dummyAuth.password.toString(),
            dummyAuth.phoneNumber.toString(),
            dummyAuth.city.toString(),
            dummyAuth.address.toString())
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }

    @Test
    fun `when login should null and return error`() {
        val expectedValue = MutableLiveData<Result<User>>()
        expectedValue.value = id.binar.fp.secondhand.util.Result.Error("Error")

        `when` (
            viewModel.login(
                dummyAuth.fullName.toString(),
                dummyAuth.email.toString()
            )
        ).thenReturn(expectedValue)

        val actualValue = viewModel.login(
            dummyAuth.fullName.toString(),
            dummyAuth.email.toString()
        ).getOrAwaitValue()
        verify(repo).login(dummyAuth.fullName.toString(), dummyAuth.email.toString())
        assertNotNull(actualValue)
        assertTrue(actualValue is Result.Error)
    }
}