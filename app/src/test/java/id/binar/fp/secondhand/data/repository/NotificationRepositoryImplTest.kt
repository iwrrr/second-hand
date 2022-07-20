package id.binar.fp.secondhand.data.repository

import id.binar.fp.secondhand.data.source.local.room.dao.NotificationDao
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
class NotificationRepositoryImplTest {

    @Mock
    private lateinit var NotifDao: NotificationDao
    private lateinit var apiService: ApiService
    private lateinit var repository: NotificationRepositoryImpl

    private val dummyNotifId = DataDummy.generateNotifId()

    @Before
    fun setUp() {
        apiService = FakeApiService()
        repository = NotificationRepositoryImpl(apiService, NotifDao)
    }

    @Test
    fun `when get notif`() = runTest {
        val expectedData = DataDummy.generateNotif()
        val actualData = apiService.getNotification()
        assertNotNull(actualData)
        assertEquals(expectedData.size, actualData.size)
    }

    @Test
    fun `when get notif id`() = runTest {
        val expectedData = DataDummy.generateNotifId()
        val actualData = apiService.getNotificationById(dummyNotifId.id).toDomain()
        assertNotNull(actualData)
        assertEquals(expectedData, actualData)
    }

    @Test
    fun `when update notif`() = runTest {
        val expectedData = DataDummy.generateNotifId()
        val actualData = apiService.updateNotificationById(dummyNotifId.id).toDomain()
        assertNotNull(actualData)
        assertEquals(expectedData, actualData)
    }
}