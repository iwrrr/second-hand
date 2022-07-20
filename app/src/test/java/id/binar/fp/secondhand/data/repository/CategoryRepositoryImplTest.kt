package id.binar.fp.secondhand.data.repository

import id.binar.fp.secondhand.data.source.local.room.dao.CategoryDao
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.sources.remote.service.FakeApiService
import id.binar.fp.secondhand.utils.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CategoryRepositoryImplTest {
    @Mock
    private lateinit var categoryDao: CategoryDao
    private lateinit var apiService: ApiService
    private lateinit var repository: CategoryRepositoryImpl

    @Before
    fun setUp() {
        apiService = FakeApiService()
        repository = CategoryRepositoryImpl(apiService, categoryDao)
    }

    @Test
    fun `when get category should not null`() = runTest{
        val expectedData = DataDummy.generateCategories()
        val actualData = apiService.getCategory()
        assertNotNull(actualData)
        assertEquals(expectedData.size, actualData.size)
    }
}