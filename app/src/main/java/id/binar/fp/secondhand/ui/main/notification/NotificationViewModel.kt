package id.binar.fp.secondhand.ui.main.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.binar.fp.secondhand.domain.model.Notification
import id.binar.fp.secondhand.domain.repository.NotificationRepository
import id.binar.fp.secondhand.util.Result
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    fun getNotification() = notificationRepository.getNotification()

    fun updateNotification(id: Int, notification: Notification): LiveData<Result<Notification>> {
        notification.read = true
        return notificationRepository.updateNotificationById(id, notification)
    }
}