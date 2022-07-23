package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.local.room.dao.NotificationDao
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.domain.model.Notification
import id.binar.fp.secondhand.domain.repository.NotificationRepository
import id.binar.fp.secondhand.util.Result
import retrofit2.HttpException
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val notificationDao: NotificationDao
) : NotificationRepository {

    override fun getNotification(): LiveData<Result<List<Notification>>> = liveData {
        emit(Result.Loading())

        val notification = notificationDao.getNotification().map { it.toDomain() }
        emit(Result.Loading(notification))

        try {
            val data = apiService.getNotification().map { it.toEntity() }
            notificationDao.deleteNotifications()
            notificationDao.insertNotifications(data)
        } catch (e: HttpException) {
            emit(Result.Error(e.message(), notification))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found", notification))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error", notification))
        }

        val newNotification = notificationDao.getNotification().map { it.toDomain() }
        emit(Result.Success(newNotification))
    }

    override fun getNotificationById(id: Int): LiveData<Result<Notification>> = liveData {
        TODO("Not yet implemented")
    }

    override fun updateNotificationById(
        id: Int,
        notification: Notification
    ): LiveData<Result<Notification>> = liveData {
        emit(Result.Loading())

        notificationDao.updateNotification(notification.toEntity())
        emit(Result.Loading(notification))

        try {
            apiService.updateNotificationById(id)
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }

        val newNotification = notificationDao.getNotificationById(id)?.toDomain()
        emit(Result.Success(newNotification))
    }
}