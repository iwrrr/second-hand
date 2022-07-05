package id.binar.fp.secondhand.domain.repository

import androidx.lifecycle.LiveData
import id.binar.fp.secondhand.data.source.network.response.NotificationDto
import id.binar.fp.secondhand.util.Result

interface NotificationRepository {

    fun getNotification(): LiveData<Result<List<NotificationDto>>>

    fun getNotificationById(id: Int): LiveData<Result<NotificationDto>>

    fun updateNotificationById(id: Int): LiveData<Result<NotificationDto>>
}