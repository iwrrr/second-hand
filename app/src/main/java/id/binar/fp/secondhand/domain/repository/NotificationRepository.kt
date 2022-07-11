package id.binar.fp.secondhand.domain.repository

import androidx.lifecycle.LiveData
import id.binar.fp.secondhand.domain.model.Notification
import id.binar.fp.secondhand.util.Result

interface NotificationRepository {

    fun getNotification(): LiveData<Result<List<Notification>>>

    fun getNotificationById(id: Int): LiveData<Result<Notification>>

    fun updateNotificationById(id: Int): LiveData<Result<Notification>>
}