package id.binar.fp.secondhand.data.repository

import id.binar.fp.secondhand.data.source.local.room.dao.HistoryDao
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
class HistoryRepositoryImplTest {
    @Mock
    private lateinit var historyDao: HistoryDao
    private lateinit var apiService: ApiService
    private lateinit var repository: HistoryRepositoryImpl

    private val dummyHistories = DataDummy.generateHistoriesId()

    @Before
    fun setUp() {
        apiService = FakeApiService()
        repository = HistoryRepositoryImpl(apiService, historyDao)
    }

    @Test
    fun `when get history should not null`() = runTest {
        val expectedData = DataDummy.generateHistories()
        val actualData = apiService.getHistory()
        assertNotNull(actualData)
        assertEquals(expectedData.size, actualData.size)
    }

    @Test
    fun `when get history id should not null`() = runTest {
        val expectedData = DataDummy.generateHistoriesId()
        val actualData = apiService.getHistoryById(dummyHistories.id).toDomain()
        assertNotNull(actualData)
        assertEquals(expectedData, actualData)
    }
}